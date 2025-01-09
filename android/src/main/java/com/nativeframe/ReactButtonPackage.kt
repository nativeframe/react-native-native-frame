package com.nativeframe

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class ReactButtonPackage : TurboReactPackage() {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(ReactButtonManager(reactContext))
  }

  override fun getModule(s: String, reactApplicationContext: ReactApplicationContext): NativeModule? {
    when (s) {
      ReactButtonManager.REACT_CLASS -> ReactButtonManager(reactApplicationContext)
    }
    return null
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider {
    mapOf(
      ReactButtonManager.REACT_CLASS to ReactModuleInfo(
      _name = ReactButtonManager.REACT_CLASS,
      _className = ReactButtonManager.REACT_CLASS,
      _canOverrideExistingModule = false,
      _needsEagerInit = false,
      isCxxModule = false,
      isTurboModule = true,
    )
    )
  }
}
