package com.nativeframe

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class NFVideoPlayerPackage : TurboReactPackage() {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(NFVideoPlayerManager())
  }

  override fun getModule(
    s: String,
    reactApplicationContext: ReactApplicationContext
  ): NativeModule? {
    when (s) {
      NFVideoPlayerManager.REACT_CLASS -> NFVideoPlayerManager()
    }
    return null
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
    mapOf(
      NFVideoPlayerManager.REACT_CLASS to ReactModuleInfo(
        _name = NFVideoPlayerManager.REACT_CLASS,
        _className = NFVideoPlayerManager.REACT_CLASS,
        _canOverrideExistingModule = false,
        _needsEagerInit = false,
        isCxxModule = false,
        isTurboModule = true,
      )
    )
  }
}
