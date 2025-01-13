import { Component, type ReactNode } from 'react';
import {
  ManifestPlayerEvents,
  type ManifestPlayerRequestsListener,
} from '../native-events/ManifestPlayerEvents';
import { VideoClient, types } from '@video/video-client-core';
// import { rnLogger } from '@videorn/video-client-reactnative';
import {
  type CallAPI,
  type ManifestJson,
  isGenericFormat,
} from '@video/video-client-core/lib/api';
import { CorePlayer } from '@video/video-client-core/lib/internal/player/core';

type NativeDrivers = 'webrtc' | 'native-hls';
type NativeFormats = 'webrtc' | 'mp4-hls';
type NativePlayerSpecList = { id: NativeDrivers }[];

interface ManifestPlayerProps {
  manifestUrl: string;
  token: string;
  videoClientOptions?: types.VideoClientOptions;
  players?: NativeDrivers[];
  autoplay?: boolean;
  muted?: boolean;
  preferredScoreLevel?: types.TranscodeScoreLevel | types.SourceScoreLevel;
  debounceInitTime?: number;
  children?: (props: { player: NativeFramePlayer }) => ReactNode | null;
}

interface ManifestPlayerState {
  format: NativeFormats | undefined;
  source: string | null;
  availableQualities: string[];
  paused: boolean;
  muted: boolean;
  driver: string | null;
}

const ALL_PLAYERS: { id: NativeDrivers }[] = [
  { id: 'webrtc' },
  { id: 'native-hls' },
];

const formatMappings: Record<string, NativeFormats> = {
  'webrtc': 'webrtc',
  'native-hls': 'mp4-hls',
};

export class NativeFramePlayer
  extends Component<ManifestPlayerProps, ManifestPlayerState>
  implements ManifestPlayerRequestsListener
{
  readonly TAG = 'NativeFramePlayer';
  readonly events = new ManifestPlayerEvents(this);

  videoClient: VideoClient;

  players: NativePlayerSpecList;

  player: types.PlayerAPI<types.VideoElement> | null = null;

  mediaStream: MediaStream | null = null;

  availableQualities: string[] = [];

  initPlayerTimeout: ReturnType<typeof setTimeout> | null = null;

  constructor(props: ManifestPlayerProps) {
    super(props);
    const { token, videoClientOptions, players, autoplay, muted } = props;
    this.videoClient = new VideoClient({
      token: token,
      ...videoClientOptions,
    } as types.VideoClientOptions);

    const sanitizedPlayers = players?.reduce<NativePlayerSpecList>(
      (accumulator, playerId) => {
        return [...accumulator, { id: playerId }];
      },
      []
    );

    this.players = sanitizedPlayers ?? ALL_PLAYERS;

    this.state = {
      format: undefined,
      driver: null,
      source: null,
      availableQualities: [],
      paused: !(
        autoplay ||
        videoClientOptions?.autoPlay ||
        videoClientOptions?.playerOptions?.autoPlay
      ),
      muted: (muted || videoClientOptions?.playerOptions?.muted) ?? false,
    };

    this.videoClient.on('error', (error) => {
      if (error.code !== 'manifest-error') {
        // rnLogger.error('Video client error.', error);
        this.events.onError(JSON.stringify(error));
      }
    });

    this.initManifestPlayer();

    this.events.subscribeEvents();
  }

  componentDidUpdate(prevProps: Readonly<ManifestPlayerProps>): void {
    if (this.props.manifestUrl !== prevProps.manifestUrl) {
      if (this.initPlayerTimeout) clearTimeout(this.initPlayerTimeout);
      this.initPlayerTimeout = setTimeout(() => {
        this.onRequestReloadPlayer();
      }, this.props.debounceInitTime ?? 500);
    }
  }

  initManifestPlayer = () => {
    if (!this.props.manifestUrl) return;

    const options: Partial<types.PlayerOptions> = {
      players: this.players,
      preferredScoreLevel: this.props.preferredScoreLevel,
      autoPlay: !this.state.paused,
      muted: this.state.muted,
    };

    try {
      const player = this.videoClient.requestPlayer(
        this.props.manifestUrl,
        options
      );
      this.player = player;

      player.on('error', (error) => {
        // rnLogger.error('Player emits error.', error);

        const isManifestError =
          error.message.includes('MANIFEST_FORBIDDEN') ||
          error.message.includes('MANIFEST_UNAUTHORIZED');
        if (isManifestError) {
          this.events.onManifestUnauthorized(JSON.stringify(error));
        } else {
          this.events.onError(JSON.stringify(error));
        }
      });

      player.on('driver', (driver) => {
        this.mediaStream = null;
        this.setState({ format: formatMappings[driver], driver: driver });
        this.events.onDriverChange(driver);
      });

      (player as any).on(
        'manifest',
        (manifest: types.ManifestPlayerEvents['manifest']) => {
          if (manifest.code === 200) {
            this.events.onManifest(JSON.stringify(manifest));
          } else if (manifest.code === 404) {
            this.events.onStreamOffline(JSON.stringify(manifest));
          }
        }
      );

      (player as any).provider?.on('source', (manifest: ManifestJson) => {
        if (!this.state.format) return;
        const format = manifest.formats?.[this.state.format];

        if (isGenericFormat(format) && format.manifest) {
          const currentQualityLayer = (
            (this.player as any)?.currentPlayer as CorePlayer
          )?.currentQuality?.layer;

          let source: string;
          if (
            currentQualityLayer != null &&
            currentQualityLayer.id !== '' &&
            (this.player as any)?.preferredLevel
          ) {
            source = currentQualityLayer.id.toString();
          } else {
            source = format.manifest;
          }

          if (source === this.state.source) return;
          this.setState({ source });
          this.events.onManifestSourceChange(source as string);
        }
      });

      (player as any).on('joinedCall', ({ call }: { call: CallAPI }) => {
        if (!call) return;

        call.on('streamAdded', (event) => {
          event.stream.on('source', (stream) => {
            const tracks = stream.getTracks();
            if (this.mediaStream) {
              tracks.forEach((track) => {
                this.mediaStream?.removeTrack(track as any);
              });
            }

            // Crete new media stream to see quality changes
            this.mediaStream = new MediaStream();

            tracks.forEach((track) => {
              if (player.localAudioMuted && track.kind === 'audio')
                track.enabled = false;
              if (player.localVideoPaused) track.enabled = false;
              this.mediaStream?.addTrack(track as any);
            });

            const mediaStreamURL = (this.mediaStream as any).toURL();
            this.setState({ source: mediaStreamURL });
            this.events.onManifestSourceChange(mediaStreamURL as string);
          });
        });
      });

      player.on('availableQualities', (availableQualities) => {
        const qualities = availableQualities.map(({ level }) => level);
        this.availableQualities = qualities;
        this.setState({ availableQualities: qualities });
        this.events.onAvailableQualities(qualities);
      });

      player.on('disposed', () => {
        this.events.onDisposed();
      });

      player.on('playerAccessDenied', ({ message }) => {
        this.events.onAccessDenied(message);
      });

      player.on('peerAtCapacity', () => {
        this.events.onPeerAtCapacity();
      });

      (player as any).provider?.on('videoPaused', (paused: boolean) => {
        if (this.state.format === 'webrtc' && this.mediaStream) {
          const tracks = this.mediaStream.getTracks();
          tracks.forEach((track) => {
            track.enabled = !paused;
          });
        }

        if (paused) {
          this.events.onVideoPaused();
        } else {
          this.events.onVideoPlay();
        }

        this.setState({ paused });
      });

      (player as any).provider?.on('audioMuted', (muted: boolean) => {
        if (this.state.format === 'webrtc' && this.mediaStream) {
          const tracks = this.mediaStream.getTracks();
          tracks.forEach((track) => {
            if (track.kind === 'audio') {
              track.enabled = !muted;
            }
          });
        }
        this.events.onMute(muted);
      });
    } catch (error) {
      // rnLogger.error('Error requesting player.', error);
      this.events.onError('Error requesting player.' + error + '');
    }
  };

  onRequestTimeupdate = () => {
    if (!this.player) return;
    this.player.emit('timeupdate');
  };

  onRequestVideoPause = () => {
    if (!this.player) return;
    this.player.localVideoPaused = true;

    if (this.state.format === 'webrtc' && this.mediaStream) {
      const tracks = this.mediaStream.getTracks();
      tracks.forEach((track) => {
        track.enabled = false;
      });
    }
    this.setState({ paused: true });
    this.events.onVideoPaused();
  };

  onRequestVideoPlay = () => {
    if (!this.player) return;
    this.player.localVideoPaused = false;

    if (this.state.format === 'webrtc' && this.mediaStream) {
      const tracks = this.mediaStream.getTracks();
      tracks.forEach((track) => {
        track.enabled = true;
      });
    }
    this.setState({ paused: false });
    this.events.onVideoPlay();
  };

  onRequestVideoMuteToggle = () => {
    if (!this.player) return;
    const newMuteValue = !this.player.localAudioMuted;
    this.player.localAudioMuted = newMuteValue;

    if (this.state.format === 'webrtc' && this.mediaStream) {
      const tracks = this.mediaStream.getTracks();
      tracks.forEach((track) => {
        if (track.kind === 'audio') {
          track.enabled = !newMuteValue;
        }
      });
    }
    this.setState({ muted: newMuteValue });
    this.events.onMute(newMuteValue);
  };

  onRequestDisposePlayer = () => {
    if (!this.player) return;
    this.player.dispose();
    this.player = null;
    this.mediaStream = null;
  };

  onRequestPreferredQualityChange = (level: string) => {
    if (!this.player) return;
    (this.player as any).preferredLevel = level;
  };

  onRequestReloadPlayer = () => {
    this.resetState(() => {
      if (this.mediaStream) {
        this.mediaStream.getTracks().forEach((track) => {
          this.mediaStream?.removeTrack(track);
        });
      }
      this.player?.dispose();
      this.player = null;
      this.initManifestPlayer();
    });
  };

  onRequestInitPlayer = () => {
    if (this.player) return;
    this.initManifestPlayer();
  };

  resetState = (fn?: () => void) => {
    this.setState(
      {
        format: undefined,
        driver: null,
        source: '',
        availableQualities: [],
      },
      fn
    );
  };

  render() {
    if (this.props.children) {
      return this.props.children({ player: this });
    } else {
      return null;
    }
  }
}
