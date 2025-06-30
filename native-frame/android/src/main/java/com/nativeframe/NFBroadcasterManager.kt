package com.nativeframe

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.NFBroadcasterManagerDelegate
import com.facebook.react.viewmanagers.NFBroadcasterManagerInterface

@ReactModule(name = NFVideoPlayerManager.REACT_CLASS)
class NFBroadcasterManager : SimpleViewManager<NFBroadcaster>(),
  NFBroadcasterManagerInterface<NFBroadcaster> {
  private val delegate: NFBroadcasterManagerDelegate<NFBroadcaster, NFBroadcasterManager> =
    NFBroadcasterManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<NFBroadcaster> = delegate

  override fun getName(): String = REACT_CLASS

  private var view: NFBroadcaster? = null
  override fun createViewInstance(context: ThemedReactContext): NFBroadcaster {
    view = NFBroadcaster(context)

    val m = context.getNativeModule(NFBroadcastModule::class.java)
    m?.listener = object : NFBroadcastModule.BroadcastListener {
      override fun onWebRTCSource(url: String) {
        context.runOnUiQueueThread {
          view?.setWebRTCStreamUrl(url)
        }
      }
    }

    return view!!
  }

  companion object {
    const val REACT_CLASS = "NFBroadcaster"
  }

  @ReactProp(name = "uri")
  override fun setUri(view: NFBroadcaster?, value: String?) {
    value?.let { view?.setUri(it) }
  }
}
