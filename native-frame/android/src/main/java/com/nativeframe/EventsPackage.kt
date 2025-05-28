package com.nativeframe

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class EventsPackage : ReactPackage {
  override fun createNativeModules(c: ReactApplicationContext): MutableList<NativeModule> {
    return mutableListOf(ManifestPlayerEventsModule(c))
  }

  override fun createViewManagers(c: ReactApplicationContext): MutableList<ViewManager<View, ReactShadowNode<*>>> {
    return mutableListOf()
  }
}
