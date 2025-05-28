package com.nativeframe

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class NFBroadcasterPackage : TurboReactPackage() {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(NFBroadcasterManager())
  }

  override fun getModule(
    s: String,
    reactApplicationContext: ReactApplicationContext
  ): NativeModule? {
    when (s) {
      NFBroadcasterManager.REACT_CLASS -> NFBroadcasterManager()
    }
    return null
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
    mapOf(
      NFBroadcasterManager.REACT_CLASS to ReactModuleInfo(
        _name = NFBroadcasterManager.REACT_CLASS,
        _className = NFBroadcasterManager.REACT_CLASS,
        _canOverrideExistingModule = false,
        _needsEagerInit = false,
        isCxxModule = false,
        isTurboModule = true,
      )
    )
  }
}
