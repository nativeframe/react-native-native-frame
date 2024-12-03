// import { NativeModules, EventEmitter } from 'react-native';
// import { Platform, DeviceEventEmitter, NativeEventEmitter } from 'react-native';

// export interface ManifestPlayerRequestsListener {
//   onRequestVideoPause(): void;
//   onRequestVideoPlay(): void;
//   onRequestTimeupdate(): void;
//   onRequestDisposePlayer(): void;
//   onRequestPreferredQualityChange(preferredQuality: string): void;
//   onRequestReloadPlayer(): void;
//   onRequestInitPlayer(): void;
//   onRequestVideoMuteToggle(): void;
// }

// export class ManifestPlayerEvents {
//   readonly requestsListener: ManifestPlayerRequestsListener;

//   constructor(requestsListener: ManifestPlayerRequestsListener) {
//     this.requestsListener = requestsListener;
//   }

//   emitter!: EventEmitter;

//   subscribeEvents() {
//     this.emitter =
//       Platform.OS === 'android' ? DeviceEventEmitter : new NativeEventEmitter(NativeModules.ManifestPlayerEvents);

//     this.emitter.addListener('player.pause', () => {
//       this.requestsListener.onRequestVideoPause();
//     });

//     this.emitter.addListener('player.play', () => {
//       this.requestsListener.onRequestVideoPlay();
//     });

//     this.emitter.addListener('player.timeupdate', () => {
//       this.requestsListener.onRequestTimeupdate();
//     });

//     this.emitter.addListener('player.dispose', () => {
//       this.requestsListener.onRequestDisposePlayer();
//     });

//     this.emitter.addListener('player.preferredQuality', (qualityLevel: string) => {
//       this.requestsListener.onRequestPreferredQualityChange(qualityLevel);
//     });

//     this.emitter.addListener('player.refresh', () => {
//       this.requestsListener.onRequestReloadPlayer();
//     });

//     this.emitter.addListener('player.init', () => {
//       this.requestsListener.onRequestInitPlayer();
//     });

//     this.emitter.addListener('player.toggleMute', () => {
//       this.requestsListener.onRequestVideoMuteToggle();
//     });
//   }

//   clear() {
//     this.emitter.removeAllListeners();
//   }

//   onManifest(manifest: string) {
//     NativeModules.ManifestPlayerEvents?.onManifest({ manifest: manifest });
//   }

//   onStreamOffline(manifest: string) {
//     NativeModules.ManifestPlayerEvents?.onStreamOffline({ manifest: manifest });
//   }

//   onManifestUnauthorized(manifest: string) {
//     NativeModules.ManifestPlayerEvents?.onManifestUnauthorized({ manifest: manifest });
//   }

//   onError(error: string) {
//     NativeModules.ManifestPlayerEvents?.onError({ error: error });
//   }

//   onManifestSourceChange(source: string, peerId?: string) {
//     NativeModules.ManifestPlayerEvents?.onManifestSourceChange({ source: source, peerId: peerId });
//   }

//   onDriverChange(driver: string) {
//     NativeModules.ManifestPlayerEvents?.onDriverChange({ driver: driver });
//   }

//   onVideoPlay() {
//     NativeModules.ManifestPlayerEvents?.onVideoPlay();
//   }

//   onVideoPaused() {
//     NativeModules.ManifestPlayerEvents?.onVideoPaused();
//   }

//   onMute(muted: boolean) {
//     NativeModules.ManifestPlayerEvents?.onMute({ muted: muted });
//   }

//   onDisposed() {
//     NativeModules.ManifestPlayerEvents?.onDisposed();
//   }

//   onAvailableQualities(availableQualities: string[]) {
//     NativeModules.ManifestPlayerEvents?.onAvailableQualities({ availableQualities: availableQualities });
//   }

//   onAccessDenied(message: string) {
//     NativeModules.ManifestPlayerEvents?.onAccessDenied({ message: message });
//   }

//   onPeerAtCapacity() {
//     NativeModules.ManifestPlayerEvents?.onPeerAtCapacity();
//   }
// }
