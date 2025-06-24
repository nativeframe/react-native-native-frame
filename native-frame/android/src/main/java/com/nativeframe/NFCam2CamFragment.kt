package com.nativeframe

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.google.common.util.concurrent.ListenableFuture
import com.nativeframe.databinding.NfBroadcasterFragmentBinding
import com.nativeframe.databinding.NfCam2camFragmentBinding

class NFCam2CamFragment : Fragment() {
  private lateinit var binding: NfCam2camFragmentBinding
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
//    val v = inflater.inflate(R.layout.nf_cam2cam_fragment, null)
    binding = NfCam2camFragmentBinding.inflate(LayoutInflater.from(context))
//    binding = NfCam2camFragmentBinding.inflate(inflater)

    android.widget.Toast.makeText(context, "onCreateView",android.widget.Toast.LENGTH_SHORT)?.show()

    return binding.root
//    return v
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    android.widget.Toast.makeText(context, "onViewCreated",android.widget.Toast.LENGTH_SHORT)?.show()
    cameraSelector = CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
      .build()
    preview = Preview.Builder().build()
    binding.cameraPreviewMe.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
    preview.setSurfaceProvider(binding.cameraPreviewMe.surfaceProvider)
//    preview.surfaceProvider = v.findViewById<PreviewView>(R.id.camera_preview_me).surfaceProvider

    loadCam()
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    android.widget.Toast.makeText(context, "onViewStateRestored",android.widget.Toast.LENGTH_SHORT)?.show()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    android.widget.Toast.makeText(context, "onAttach",android.widget.Toast.LENGTH_SHORT)?.show()
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onPause() {
    super.onPause()
   // unloadCam()
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
          cameraProviderFuture?.get()?.bindToLifecycle(requireActivity(), it, preview)
        }
      },
      ContextCompat.getMainExecutor(requireContext())
    )
  }

}

