package com.nativeframe

import com.facebook.fbreact.specs.NativeMultiplySpec
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NativeFrameModule.NAME)
class NativeFrameModule(reactContext: ReactApplicationContext) :
  NativeMultiplySpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = "NativeFrame"
  }
}
