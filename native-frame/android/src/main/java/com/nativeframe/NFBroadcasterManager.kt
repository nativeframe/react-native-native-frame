package com.nativeframe

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
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

  override fun createViewInstance(context: ThemedReactContext): NFBroadcaster =
    NFBroadcaster(context)

  companion object {
    const val REACT_CLASS = "NFBroadcaster"
  }

  @ReactProp(name = "uri")
  override fun setUri(view: NFBroadcaster?, value: String?) {
    value?.let { view?.setUri(it) }
    Handler(Looper.getMainLooper()).postDelayed(
      {
        view?.camStart(view.context, MyLifecycleOwner())
      }, 1000
    )
  }

  class MyLifecycleOwner() : LifecycleOwner {
    private val mLifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    init {
      mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun stop() {
      mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun start() {
      mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun getLifecycle(): Lifecycle = mLifecycleRegistry
  }
}
