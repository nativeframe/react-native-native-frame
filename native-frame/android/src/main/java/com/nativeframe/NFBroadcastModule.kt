package com.nativeframe

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NFBroadcastModule.NAME)
class NFBroadcastModule(reactContext: ReactApplicationContext) :
  NativeBroadcastSpec(reactContext) {

  companion object {
    const val NAME = "NativeBroadcast"
  }

  override fun getName(): String {
    return NAME
  }

  override fun webRTC(url: String?) {
Log.i("BILLY JOEL2 Native:", url ?: "")
    NFBroadcaster.DEL_STREAM_URL = url ?: ""


//    val rtcView = WebRTCView(reactInstanceManager.currentReactContext, null)
//    val video = RTCVideoViewManager()
//    video.setStreamURL(rtcView, streamId)
//
//    val defParams = LinearLayout.LayoutParams(
//      ViewGroup.LayoutParams.MATCH_PARENT,
//      ViewGroup.LayoutParams.WRAP_CONTENT
//    ).apply {
//      gravity = Gravity.CENTER_HORIZONTAL
//
//      if (enableMargin)
//        bottomMargin = 10
//    }
//
//    rtcView.layoutParams = layoutParams ?: defParams
//
//    if (enableBorder) {
//      rtcView.background = ContextCompat.getDrawable(this, R.drawable.border)
//    }
  }
}
