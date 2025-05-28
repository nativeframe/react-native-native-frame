package com.nativeframe

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule

class ManifestPlayerEventsModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  companion object {
    const val NAME = "ManifestPlayerEvents"
  }

  override fun getName() = NAME
}
