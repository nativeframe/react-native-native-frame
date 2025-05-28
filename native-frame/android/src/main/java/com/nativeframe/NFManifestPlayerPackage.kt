package com.nativeframe

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class NFManifestPlayerPackage : TurboReactPackage() {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(NFManifestPlayerManager())
  }

  override fun getModule(
    s: String,
    reactApplicationContext: ReactApplicationContext
  ): NativeModule? {
    when (s) {
      NFManifestPlayerManager.REACT_CLASS -> NFManifestPlayerManager()
    }
    return null
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
    mapOf(
      NFManifestPlayerManager.REACT_CLASS to ReactModuleInfo(
        _name = NFManifestPlayerManager.REACT_CLASS,
        _className = NFManifestPlayerManager.REACT_CLASS,
        _canOverrideExistingModule = false,
        _needsEagerInit = false,
        isCxxModule = false,
        isTurboModule = true,
      )
    )
  }
}
