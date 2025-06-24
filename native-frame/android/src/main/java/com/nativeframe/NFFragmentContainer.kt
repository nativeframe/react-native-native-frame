package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.ReactContext
import com.nativeframe.databinding.NfFragmentContainerBinding

abstract class NFFragmentContainer : LinearLayoutCompat {
  protected var binding: NfFragmentContainerBinding? = null
  protected var reactContext: ReactContext? = null

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
    (context as ReactContext?)?.let {
      reactContext = it
    }

    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    binding = NfFragmentContainerBinding.inflate(LayoutInflater.from(context))

    with((reactContext!!.currentActivity!! as ReactActivity).supportFragmentManager.beginTransaction()) {
      replace(R.id.container, getFragment())
      commit()
    }

    addView(binding?.root)
  }

  abstract fun getFragment(): Fragment
}
