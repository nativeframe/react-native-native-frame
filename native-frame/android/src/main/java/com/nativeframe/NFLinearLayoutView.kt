package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.facebook.react.bridge.ReactContext

abstract class NFLinearLayoutView<T, TController> : LinearLayoutCompat {
  protected var binding: T? = null
  protected var reactContext: ReactContext? = null
  protected var controller: TController? = null
  protected var shown = false

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

  protected abstract fun inflateBinding(): T

  private fun setup(context: Context) {
    (context as? ReactContext)?.let {
      reactContext = it
    }

    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )

    with(inflateBinding()) {
      binding = this
      onSetup(this)
    }
  }

  //#region react-native bug. see https://github.com/facebook/react-native/issues/17968#issuecomment-2065449875

  override fun requestLayout() {
    super.requestLayout()
    post(measureAndLayout)
  }

  private val measureAndLayout = Runnable {
    measure(
      MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
      MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
    )
    layout(left, top, right, bottom)
  }
  //#endregion

  override fun onVisibilityChanged(changedView: View, visibility: Int) {
    super.onVisibilityChanged(changedView, visibility)

    if (changedView != this)
      return

    if (visibility == View.VISIBLE) {
      if (shown)
        return


      shown = true

      onShown()
    } else {
      shown = false

      onHide()
    }
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()

    onHide()
  }

  protected open fun onSetup(view: T) {
  }

  protected open fun onShown() {
  }

  protected open fun onHide() {
  }
}
