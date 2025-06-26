package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.facebook.react.ReactActivity
import com.nativeframe.databinding.NfBroadcasterBinding

class NFBroadcaster : NFLinearLayoutView<NfBroadcasterBinding, NFBroadcasterController> {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  companion object {
    var DEL_STREAM_URL = ""
  }

  override fun onSetup(view: NfBroadcasterBinding) {
    super.onSetup(view)

    controller = NFBroadcasterController(
      context, view,
      onToggleCam = {
        if (it) showCam() else noCam()
      },
      onToggleMic = {

      })
    controller?.initCam()

    addView(view.root)

//    Handler(Looper.getMainLooper()).postDelayed({
//      val v = WebRTCView(context)
//      Log.i("BILLY JOEL2 Native: using: ", DEL_STREAM_URL ?: "")
//RTCVideoViewManager().setStreamURL(v, DEL_STREAM_URL)
//      view.root.addView(v)
//    }, 10000)
  }

//  private fun getVideoTrackForStreamURL(streamURL: String?): VideoTrack? {
//    var videoTrack: VideoTrack? = null
//
//    if (streamURL != null) {
//      val reactContext = context as ReactContext
//      val module
//        : WebRTCModule? = reactContext.getNativeModule(WebRTCModule::class.java)
//      val stream: MediaStream = module.getStreamForReactTag(streamURL)
//
//      if (stream != null) {
//        val videoTracks: List<VideoTrack> = stream.videoTracks
//
//        if (!videoTracks.isEmpty()) {
//          videoTrack = videoTracks[0]
//        }
//      }
//
//      if (videoTrack == null) {
//        Log.w(
//          com.oney.WebRTCModule.WebRTCView.TAG,
//          "No video stream for react tag: $streamURL"
//        )
//      }
//    }
//
//    return videoTrack
//  }

  fun setUri(uri: String) {
  }

  override fun inflateBinding() = NfBroadcasterBinding.inflate(LayoutInflater.from(context))

  override fun onShown() {
    super.onShown()

    showCam()
  }

  override fun onHide() {
    super.onHide()

    noCam()
  }

  private fun showCam() {
    reactContext?.runOnUiQueueThread {
      controller?.initCam()
      controller?.loadCam(
        reactContext!!.currentActivity!!,
        (reactContext!!.currentActivity!! as ReactActivity)
      )
    }
  }

  private fun noCam() {
    reactContext?.runOnUiQueueThread {
      controller?.unloadCam()
    }
  }
}
