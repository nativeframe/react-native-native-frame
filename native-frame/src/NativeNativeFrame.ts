import { VideoClient, type types } from '@video/video-client-core';

export class NativeFrame {
  async initVideoClient(userID: string, token: string) {
    const videoClientOptions: types.VideoClientOptions = {
      backendEndpoints: ['https://platform.nativeframe.com'],
      token: token,
      userId: userID,
    };
    return new VideoClient(videoClientOptions);
  }

  static stream(userID: string, token: string) {
    new NativeFrame().initVideoClient(userID, token);
  }
}
