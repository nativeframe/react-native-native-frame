package com.nativeframe

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.CustomButtonManagerDelegate
import com.facebook.react.viewmanagers.CustomButtonManagerInterface

@ReactModule(name = ReactButtonManager.REACT_CLASS)
class ReactButtonManager(context: ReactApplicationContext) : SimpleViewManager<ReactButton>(),
  CustomButtonManagerInterface<ReactButton> {
  private val delegate: CustomButtonManagerDelegate<ReactButton, ReactButtonManager> =
    CustomButtonManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<ReactButton> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): ReactButton = ReactButton(context)

  @ReactProp(name = "sourceUrl")
  override fun setSourceURL(view: ReactButton, sourceURL: String?) {
  view.setText(view.text.toString() + view.text.toString())
  }

  companion object {
    const val REACT_CLASS = "CustomWebView"
  }
}
