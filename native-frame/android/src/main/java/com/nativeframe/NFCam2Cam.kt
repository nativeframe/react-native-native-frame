package com.nativeframe

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.ReactContext
import com.nativeframe.databinding.NfCam2camContainerBinding
import com.nativeframe.databinding.NfFragmentContainerBinding

class NFCam2Cam : LinearLayoutCompat {
  protected var binding: NfCam2camContainerBinding? = null
  protected var reactContext: ReactContext? = null
  private var shown = false

  constructor(context: Context) : super(context) {
    setup(context)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    setup(context)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    setup(context)
  }

  private fun setup(context: Context) {
    shown = false
    (context as ReactContext?)?.let {
      reactContext = it
    }

    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    binding = NfCam2camContainerBinding.inflate(LayoutInflater.from(context))

    addView(binding?.root)
  }

  override fun onVisibilityChanged(changedView: View, visibility: Int) {
    super.onVisibilityChanged(changedView, visibility)

    if(!shown && visibility == View.VISIBLE && changedView == this){
      shown = true
      reactContext?.currentActivity?.runOnUiThread {
       // showFragmentWithReactContext()
      }
      reactContext?.runOnUiQueueThread {

      }
    }
  }

  fun showFragmentWithReactContext() {
    (reactContext?.currentActivity as? ReactActivity)?.supportFragmentManager?.let {
      showFragment(it)
      binding?.container?.requestLayout()
    }

    requestLayout()

    Handler(Looper.getMainLooper()).postDelayed({
      requestLayout()
    }, 1000)
    Handler(Looper.getMainLooper()).postDelayed({
      requestLayout()
    }, 2000)
    Handler(Looper.getMainLooper()).postDelayed({
      requestLayout()
    }, 3000)
    Handler(Looper.getMainLooper()).postDelayed({
      requestLayout()
    }, 4000)
  }

  fun showFragment(fragmentManager: FragmentManager) {
//    with((reactContext!!.currentActivity!! as ReactActivity).supportFragmentManager.beginTransaction()) {
//      replace(binding!!.container.id, NFBroadcasterFragment())
//      commit()
//    }
    with(fragmentManager.beginTransaction()) {
      setReorderingAllowed(true)
      replace(binding!!.container.id, NFCam2CamFragment())
      addOnLayoutChangeListener { view, i, i2, i3, i4, i5, i6, i7, i8 ->
        requestLayout()
      }
      addOnAttachStateChangeListener(object: OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(p0: View) {
          requestLayout()
        }

        override fun onViewDetachedFromWindow(p0: View) {

        }

      })
      commit()
    }


  }

  fun setUri(uri: String) {
  }
}
