package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class ReactButton : AppCompatButton {
  constructor(context: Context) : super(context) {
    configureComponent()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    configureComponent()
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    configureComponent()
  }

  private fun configureComponent() {
    setText("GOGOGOG")
  }
}
