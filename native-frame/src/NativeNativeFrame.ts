import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import { VideoClient, type types } from '@video/video-client-core';
export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NativeFrame');

export class NativeFrame {
  async initVideoClient(token: string) {
    const videoClientOptions: types.VideoClientOptions = {
      backendEndpoints: ['https://platform.nativeframe.com'],
      token: token,
      userId: 'aj',
    };
    return new VideoClient(videoClientOptions);
  }

  static stream() {
    new NativeFrame().initVideoClient('');
  }
}
