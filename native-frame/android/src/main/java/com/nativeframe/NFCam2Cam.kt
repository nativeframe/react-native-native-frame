package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.facebook.react.ReactActivity
import com.nativeframe.databinding.NfCam2camFragmentBinding

class NFCam2Cam : NFLinearLayoutView<NfCam2camFragmentBinding, NFCam2CamController> {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  override fun onSetup(view: NfCam2camFragmentBinding) {
    super.onSetup(view)

    controller = NFCam2CamController(context, view)
    controller?.initCam()

    addView(view.root)
  }

  fun setUri(uri: String) {
  }

  override fun inflateBinding() = NfCam2camFragmentBinding.inflate(LayoutInflater.from(context))

  override fun onShown() {
    super.onShown()

    reactContext?.runOnUiQueueThread {
      controller?.loadCam(
        reactContext!!.currentActivity!!,
        (reactContext!!.currentActivity!! as ReactActivity)
      )
    }
  }

  override fun onHide() {
    super.onHide()

    reactContext?.runOnUiQueueThread {
      controller?.unloadCam()
    }
  }
}
