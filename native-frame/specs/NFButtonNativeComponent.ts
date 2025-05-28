import type { HostComponent, ViewProps } from 'react-native';
import type { BubblingEventHandler } from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
  text?: string;
  onClicked?: BubblingEventHandler<{}> | null;
}

export default codegenNativeComponent<NativeProps>(
  'NFButton'
) as HostComponent<NativeProps>;
