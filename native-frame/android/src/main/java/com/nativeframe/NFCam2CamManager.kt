package com.nativeframe

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.NFCam2CamManagerDelegate
import com.facebook.react.viewmanagers.NFCam2CamManagerInterface

@ReactModule(name = NFCam2CamManager.REACT_CLASS)
class NFCam2CamManager : SimpleViewManager<NFCam2Cam>(),
  NFCam2CamManagerInterface<NFCam2Cam> {
  private val delegate: NFCam2CamManagerDelegate<NFCam2Cam, NFCam2CamManager> =
    NFCam2CamManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<NFCam2Cam> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): NFCam2Cam =
    NFCam2Cam(context)

  companion object {
    const val REACT_CLASS = "NFCam2Cam"
  }

  @ReactProp(name = "uri")
  override fun setUri(view: NFCam2Cam?, value: String?) {
    value?.let { view?.setUri(it) }
  }
}
