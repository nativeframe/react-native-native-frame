package com.nativeframe

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.NFManifestPlayerManagerDelegate
import com.facebook.react.viewmanagers.NFManifestPlayerManagerInterface

@ReactModule(name = NFVideoPlayerManager.REACT_CLASS)
class NFManifestPlayerManager : SimpleViewManager<NFManifestPlayer>(),
  NFManifestPlayerManagerInterface<NFManifestPlayer> {
  private val delegate: NFManifestPlayerManagerDelegate<NFManifestPlayer, NFManifestPlayerManager> =
    NFManifestPlayerManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<NFManifestPlayer> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): NFManifestPlayer =
    NFManifestPlayer(context)

  companion object {
    const val REACT_CLASS = "NFManifestPlayer"
  }

  @ReactProp(name = "manifestUri")
  override fun setManifestUri(view: NFManifestPlayer?, value: String?) {
    value?.let { view?.setManifestUri(it) }
  }
}
