package com.nativeframe

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class NFButtonPackage : TurboReactPackage() {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(NFButtonManager())
  }

  override fun getModule(
    s: String,
    reactApplicationContext: ReactApplicationContext
  ): NativeModule? {
    when (s) {
      NFButtonManager.REACT_CLASS -> NFButtonManager()
    }
    return null
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
    mapOf(
      NFButtonManager.REACT_CLASS to ReactModuleInfo(
        _name = NFButtonManager.REACT_CLASS,
        _className = NFButtonManager.REACT_CLASS,
        _canOverrideExistingModule = false,
        _needsEagerInit = false,
        isCxxModule = false,
        isTurboModule = true,
      )
    )
  }
}
