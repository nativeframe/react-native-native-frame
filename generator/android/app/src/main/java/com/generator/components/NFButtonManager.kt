package com.generator.components

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.NFCustomButtonManagerDelegate
import com.facebook.react.viewmanagers.NFCustomButtonManagerInterface

@ReactModule(name = NFButtonManager.REACT_CLASS)
class NFButtonManager(context: ReactApplicationContext) : SimpleViewManager<NFButton>(),
  NFCustomButtonManagerInterface<NFButton> {
  private val delegate: NFCustomButtonManagerDelegate<NFButton, NFButtonManager> =
    NFCustomButtonManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<NFButton> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): NFButton = NFButton(context)

  companion object {
    const val REACT_CLASS = "NFCustomButton"
  }

  @ReactProp(name = "text")
  override fun setText(view: NFButton?, value: String?) {
    view?.text = value
  }
}
