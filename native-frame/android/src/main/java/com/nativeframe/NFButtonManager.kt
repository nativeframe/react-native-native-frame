package com.nativeframe

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.NFButtonManagerDelegate
import com.facebook.react.viewmanagers.NFButtonManagerInterface

@ReactModule(name = NFButtonManager.REACT_CLASS)
class NFButtonManager(context: ReactApplicationContext) : SimpleViewManager<NFButton>(),
  NFButtonManagerInterface<NFButton> {
  private val delegate: NFButtonManagerDelegate<NFButton, NFButtonManager> =
    NFButtonManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<NFButton> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): NFButton = NFButton(context)

  companion object {
    const val REACT_CLASS = "NFButton"
  }

  @ReactProp(name = "text")
  override fun setText(view: NFButton?, value: String?) {
    view?.text = value
  }
}
