package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.facebook.react.ReactActivity
import com.nativeframe.databinding.NfBroadcasterFragmentBinding

class NFBroadcaster : NFLinearLayoutView<NfBroadcasterFragmentBinding, NFBroadcasterController> {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  override fun onSetup(view: NfBroadcasterFragmentBinding) {
    super.onSetup(view)

    controller = NFBroadcasterController(context, view)
    controller?.initCam()

    addView(view.root)
  }

  fun setUri(uri: String) {
  }

  override fun inflateBinding() = NfBroadcasterFragmentBinding.inflate(LayoutInflater.from(context))

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
