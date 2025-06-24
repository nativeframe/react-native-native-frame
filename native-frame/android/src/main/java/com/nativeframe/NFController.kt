package com.nativeframe

import android.app.Activity
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.Preview.SurfaceProvider
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.facebook.react.bridge.ReactContext
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.google.common.util.concurrent.ListenableFuture

abstract class NFController<T>(
  private val context: Context,
  private val binding: T
) {
  private var cameraSelector: CameraSelector? = null
  private var preview: Preview? = null

  private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
  protected var emitter: RCTDeviceEventEmitter? = null

  init {
    (context as? ReactContext)?.let {
      emitter = it.getJSModule(RCTDeviceEventEmitter::class.java)
    }
  }

  protected abstract fun getSurfaceProvider(): SurfaceProvider?

  fun initCam() {
    cameraSelector = CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
      .build()
    preview = Preview.Builder().build()
    preview?.setSurfaceProvider(getSurfaceProvider())
  }

  fun unloadCam() {
    preview?.setSurfaceProvider(null)
    cameraProviderFuture?.get()?.unbindAll()
    cameraProviderFuture = null
    preview = null
  }

  fun loadCam(activity: Activity, lifecycleOwner: LifecycleOwner) {
    if (!NativeFramePackages.checkAppPermissions(activity))
      return

    if (cameraProviderFuture != null)
      return

    cameraProviderFuture = ProcessCameraProvider.getInstance(activity)
    cameraProviderFuture?.addListener(
      {
        cameraSelector?.let {
          preview?.let { p ->
            cameraProviderFuture?.get()?.bindToLifecycle(lifecycleOwner, it, p)
          }
        }
      },
      ContextCompat.getMainExecutor(context)
    )
  }
}

