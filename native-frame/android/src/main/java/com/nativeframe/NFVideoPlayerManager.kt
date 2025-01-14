package com.nativeframe

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.NFVideoPlayerManagerDelegate
import com.facebook.react.viewmanagers.NFVideoPlayerManagerInterface

@ReactModule(name = NFVideoPlayerManager.REACT_CLASS)
class NFVideoPlayerManager : SimpleViewManager<NFVideoPlayer>(),
  NFVideoPlayerManagerInterface<NFVideoPlayer> {
  private val delegate: NFVideoPlayerManagerDelegate<NFVideoPlayer, NFVideoPlayerManager> =
    NFVideoPlayerManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<NFVideoPlayer> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): NFVideoPlayer =
    NFVideoPlayer(context)

  companion object {
    const val REACT_CLASS = "NFVideoPlayer"
  }

  @ReactProp(name = "hls")
  override fun setHls(view: NFVideoPlayer?, value: String?) {
    value?.let {
      view?.setHls(value)
    }
  }
}
