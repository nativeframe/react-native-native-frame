package com.nativeframe

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NFBroadcastModule.NAME)
class NFBroadcastModule(private val reactContext: ReactApplicationContext) :
  NativeBroadcastSpec(reactContext) {

  companion object {
    const val NAME = "NativeBroadcast"
  }

  interface BroadcastListener {
    fun onWebRTCSource(url: String)
  }

  var listener: BroadcastListener? = null
    set(value) {
      field = value
    }

  override fun getName(): String {
    return NAME
  }

  override fun webRTC(url: String?) {
    if (url != null)
      listener?.onWebRTCSource(url)
  }
}
