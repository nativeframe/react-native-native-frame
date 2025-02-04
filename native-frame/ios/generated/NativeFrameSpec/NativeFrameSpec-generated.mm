/**
 * This code was generated by [react-native-codegen](https://www.npmjs.com/package/react-native-codegen).
 *
 * Do not edit this file as changes may cause incorrect behavior and will be lost
 * once the code is regenerated.
 *
 * @generated by codegen project: GenerateModuleObjCpp
 *
 * We create an umbrella header (and corresponding implementation) here since
 * Cxx compilation in BUCK has a limitation: source-code producing genrule()s
 * must have a single output. More files => more genrule()s => slower builds.
 */

#import "NativeFrameSpec.h"


@implementation NativeLocalStorageSpecBase


- (void)setEventEmitterCallback:(EventEmitterCallbackWrapper *)eventEmitterCallbackWrapper
{
  _eventEmitterCallback = std::move(eventEmitterCallbackWrapper->_eventEmitterCallback);
}
@end


namespace facebook::react {
  
    static facebook::jsi::Value __hostFunction_NativeLocalStorageSpecJSI_setItem(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
      return static_cast<ObjCTurboModule&>(turboModule).invokeObjCMethod(rt, VoidKind, "setItem", @selector(setItem:value:), args, count);
    }

    static facebook::jsi::Value __hostFunction_NativeLocalStorageSpecJSI_getItem(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
      return static_cast<ObjCTurboModule&>(turboModule).invokeObjCMethod(rt, StringKind, "getItem", @selector(getItem:), args, count);
    }

    static facebook::jsi::Value __hostFunction_NativeLocalStorageSpecJSI_removeItem(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
      return static_cast<ObjCTurboModule&>(turboModule).invokeObjCMethod(rt, VoidKind, "removeItem", @selector(removeItem:), args, count);
    }

    static facebook::jsi::Value __hostFunction_NativeLocalStorageSpecJSI_clear(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
      return static_cast<ObjCTurboModule&>(turboModule).invokeObjCMethod(rt, VoidKind, "clear", @selector(clear), args, count);
    }

  NativeLocalStorageSpecJSI::NativeLocalStorageSpecJSI(const ObjCTurboModule::InitParams &params)
    : ObjCTurboModule(params) {
      
        methodMap_["setItem"] = MethodMetadata {2, __hostFunction_NativeLocalStorageSpecJSI_setItem};
        
        
        methodMap_["getItem"] = MethodMetadata {1, __hostFunction_NativeLocalStorageSpecJSI_getItem};
        
        
        methodMap_["removeItem"] = MethodMetadata {1, __hostFunction_NativeLocalStorageSpecJSI_removeItem};
        
        
        methodMap_["clear"] = MethodMetadata {0, __hostFunction_NativeLocalStorageSpecJSI_clear};
        
  }
} // namespace facebook::react

@implementation NativeUtilSpecBase


- (void)setEventEmitterCallback:(EventEmitterCallbackWrapper *)eventEmitterCallbackWrapper
{
  _eventEmitterCallback = std::move(eventEmitterCallbackWrapper->_eventEmitterCallback);
}
@end


namespace facebook::react {
  
    static facebook::jsi::Value __hostFunction_NativeUtilSpecJSI_platformDetailsString(facebook::jsi::Runtime& rt, TurboModule &turboModule, const facebook::jsi::Value* args, size_t count) {
      return static_cast<ObjCTurboModule&>(turboModule).invokeObjCMethod(rt, StringKind, "platformDetailsString", @selector(platformDetailsString), args, count);
    }

  NativeUtilSpecJSI::NativeUtilSpecJSI(const ObjCTurboModule::InitParams &params)
    : ObjCTurboModule(params) {
      
        methodMap_["platformDetailsString"] = MethodMetadata {0, __hostFunction_NativeUtilSpecJSI_platformDetailsString};
        
  }
} // namespace facebook::react
