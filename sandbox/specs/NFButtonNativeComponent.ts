import type {HostComponent, ViewProps} from 'react-native';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
  text?: string;
}

export default codegenNativeComponent<NativeProps>(
  'CustomButton',
) as HostComponent<NativeProps>;
