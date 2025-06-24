package com.nativeframe

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.allViews
import androidx.fragment.app.FragmentManager
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.ReactContext
import com.nativeframe.databinding.NfFragmentContainerBinding

class NFBroadcaster : LinearLayoutCompat {
  protected var binding: NfFragmentContainerBinding? = null
  protected var reactContext: ReactContext? = null
  protected var fragmentManager: FragmentManager? = null

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
    (context as? ReactContext)?.let {
      reactContext = it
    }

    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    binding = NfFragmentContainerBinding.inflate(LayoutInflater.from(context))

    addView(binding?.root)
    Log.i("views: ", allViews.map { it.id }.joinToString())
    Log.i("views 2: ", binding?.root?.allViews?.map { it.id }?.joinToString() ?: "n/a")
    Log.i("views 3: ", binding?.container?.id?.toString() ?: "n/a")
    Log.i("views 4a: ", R.id.container.toString())

    showFragmentWithReactContext()
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()

    Log.i("views 4a: onAttachedToWindow", R.id.container.toString())
  }

  fun setUri(uri: String) {
  }

  fun showFragmentWithReactContext() {
    (reactContext?.currentActivity as? ReactActivity)?.supportFragmentManager?.let {
      showFragment(it)
    }
  }

  fun showFragment(fragmentManager: FragmentManager) {
//    with((reactContext!!.currentActivity!! as ReactActivity).supportFragmentManager.beginTransaction()) {
//      replace(binding!!.container.id, NFBroadcasterFragment())
//      commit()
//    }
    with(fragmentManager.beginTransaction()) {
      replace(binding!!.container.id, NFBroadcasterFragment())
      commit()
    }
  }
}
