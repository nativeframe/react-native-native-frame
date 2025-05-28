package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event

class NFButton : AppCompatButton {
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
    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    setOnClickListener {
      (context as ReactContext?)?.let {
        val surfaceId = UIManagerHelper.getSurfaceId(it)
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(it, id)
        dispatcher?.dispatchEvent(OnClickedEvent(surfaceId, id, Arguments.createMap()))
      }
    }
  }

  inner class OnClickedEvent(
    surfaceId: Int,
    viewId: Int,
    private val payload: WritableMap
  ) : Event<OnClickedEvent>(surfaceId, viewId) {
    override fun getEventName() = "onClicked"

    override fun getEventData() = payload
  }
}
