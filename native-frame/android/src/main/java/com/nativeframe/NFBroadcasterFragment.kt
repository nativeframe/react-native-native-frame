package com.nativeframe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.google.common.util.concurrent.ListenableFuture
import com.nativeframe.databinding.NfBroadcasterFragmentBinding

class NFBroadcasterFragment : Fragment() {
  private var binding: NfBroadcasterFragmentBinding? = null

  private lateinit var cameraSelector: CameraSelector
  private lateinit var preview: Preview

  private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
  private var emitter: RCTDeviceEventEmitter? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    (context as? ReactContext)?.let {
      emitter = it.getJSModule(RCTDeviceEventEmitter::class.java)
    }
    binding = NfBroadcasterFragmentBinding.inflate(LayoutInflater.from(context))

    cameraSelector = CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
      .build()
    preview = Preview.Builder().build()
    preview.setSurfaceProvider(binding?.cameraPreview?.surfaceProvider)

    binding?.goLive?.setOnClickListener {
      emitter?.emit("broadcaster.onBroadcast", Arguments.createMap().apply {
        putString("uri", "hey world")
      })
    }

    return binding!!.root
  }

  override fun onResume() {
    super.onResume()
    loadCam()
  }

  override fun onPause() {
    super.onPause()
    unloadCam()
  }

  private fun unloadCam() {
    cameraProviderFuture?.get()?.unbindAll()
    cameraProviderFuture = null
  }

  private fun loadCam() {
    if (!NativeFramePackages.checkAppPermissions(requireActivity()))
      return

    if (cameraProviderFuture != null)
      return

    cameraProviderFuture =
      ProcessCameraProvider.getInstance(requireActivity())
    cameraProviderFuture?.addListener(
      {
        cameraSelector.let {
          cameraProviderFuture?.get()?.bindToLifecycle(this, it, preview)
        }
      },
      ContextCompat.getMainExecutor(requireContext().applicationContext)
    )
  }

}

