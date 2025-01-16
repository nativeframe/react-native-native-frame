import type { HostComponent, ViewProps } from 'react-native';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
  hls?: string;
  webm?: string;
}

export default codegenNativeComponent<NativeProps>(
  'NFVideoPlayer'
) as HostComponent<NativeProps>;
