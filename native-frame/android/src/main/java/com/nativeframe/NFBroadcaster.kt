package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.children
import com.facebook.react.ReactActivity
import com.nativeframe.databinding.NfBroadcasterBinding
import com.oney.WebRTCModule.WebRTCView

class NFBroadcaster : NFLinearLayoutView<NfBroadcasterBinding, NFBroadcasterController> {
  private val videoViewManager = com.oney.WebRTCModule.RTCVideoViewManager()

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  private fun showWebRTCView(show: Boolean) {
    for (v in binding?.preview?.children?.filter { it is WebRTCView } ?: emptySequence()) {
      v.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
  }

  private fun enableControls(enable: Boolean) {
    binding?.toggleCam?.isEnabled = enable
    binding?.toggleMic?.isEnabled = enable
  }

  override fun onSetup(view: NfBroadcasterBinding) {
    super.onSetup(view)

    enableControls(false)
    controller = NFBroadcasterController(
      context, view,
      onBroadcast = { enabled ->
        showWebRTCView(enabled)
      },
      onToggleCam = { enabled ->
        showWebRTCView(enabled)
      },
      onToggleMic = {

      })
    controller?.initCam()

    addView(view.root)
  }

  fun setWebRTCStreamUrl(url: String) {
    enableControls(true)

    controller?.unloadCam()
    controller = null
    for (v in binding?.preview?.children?.filter { it is WebRTCView } ?: emptySequence()) {
      binding?.preview?.removeView(v)
    }

    val v = WebRTCView(context)
    Log.i(NFBroadcasterManager.REACT_CLASS, "new webrtc source url: $url")
    videoViewManager.setStreamURL(v, url)
    v.setObjectFit("cover")
    v.setMirror(true)

    binding?.cameraPreview?.visibility = View.GONE
    binding?.preview?.addView(v)
  }

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
    if (controller == null)
      return

    reactContext?.runOnUiQueueThread {
      controller?.initCam()
      controller?.loadCam(
        reactContext!!.currentActivity!!,
        (reactContext!!.currentActivity!! as ReactActivity)
      )
    }
  }

  private fun noCam() {
    if (controller == null)
      return

    reactContext?.runOnUiQueueThread {
      controller?.unloadCam()
    }
  }
}
