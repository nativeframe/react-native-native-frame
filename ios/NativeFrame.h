
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNNativeFrameSpec.h"

@interface NativeFrame : NSObject <NativeNativeFrameSpec>
#else
#import <React/RCTBridgeModule.h>

@interface NativeFrame : NSObject <RCTBridgeModule>
#endif

@end
