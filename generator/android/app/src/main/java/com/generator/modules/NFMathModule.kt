package com.generator.modules

import com.facebook.fbreact.specs.NativeMathSpec
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NFMathModule.NAME)
class NFMathModule(reactContext: ReactApplicationContext) :
  NativeMathSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = "NFNativeMath"
  }
}
