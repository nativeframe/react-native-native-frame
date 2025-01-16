package com.nativeframe

import android.os.Build
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NFUtilModule.NAME)
class NFUtilModule(reactContext: ReactApplicationContext) :
  NativeUtilSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  override fun platformDetailsString(): String {
    return "android ${Build.VERSION.SDK_INT} ${Build.MANUFACTURER}"
  }

  companion object {
    const val NAME = "NativeUtil"
  }
}
