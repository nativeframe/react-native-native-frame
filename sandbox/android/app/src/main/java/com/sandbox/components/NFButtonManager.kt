package com.sandbox.components

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.CustomButtonManagerDelegate
import com.facebook.react.viewmanagers.CustomButtonManagerInterface

@ReactModule(name = NFButtonManager.REACT_CLASS)
class NFButtonManager(context: ReactApplicationContext) : SimpleViewManager<NFButton>(),
    CustomButtonManagerInterface<NFButton> {
    private val delegate: CustomButtonManagerDelegate<NFButton, NFButtonManager> =
        CustomButtonManagerDelegate(this)

    override fun getDelegate(): ViewManagerDelegate<NFButton> = delegate

    override fun getName(): String = REACT_CLASS

    override fun createViewInstance(context: ThemedReactContext): NFButton = NFButton(context)

    @ReactProp(name = "sourceUrl")
    override fun setText(view: NFButton?, value: String?) {
        view?.text = value
    }

    companion object {
        const val REACT_CLASS = "CustomButton"
    }
}