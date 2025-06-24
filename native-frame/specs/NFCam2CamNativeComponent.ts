import type { HostComponent, ViewProps } from 'react-native';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
  uri?: string;
}

export default codegenNativeComponent<NativeProps>(
  'NFCam2Cam'
) as HostComponent<NativeProps>;
