package com.preview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.nativeframe.databinding.NfBroadcasterBinding
import com.nativeframe.databinding.NfManifestPlayerBinding
import com.preview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var cameraSelector: CameraSelector
  private lateinit var preview: Preview

  private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.views.addView(TextView(this).apply { text = "Manifest Player" })
    binding.views.addView(NfManifestPlayerBinding.inflate(LayoutInflater.from(this)).root)

    binding.views.addView(TextView(this).apply { text = "Encoder" })
    val b = NfBroadcasterBinding.inflate(LayoutInflater.from(this))
    binding.views.addView(b.root)

    cameraSelector = CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
      .build()
    preview = Preview.Builder().build()
    preview.surfaceProvider = b.cameraPreview.surfaceProvider
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
    if (!checkPermissions())
      return

    if (cameraProviderFuture != null)
      return

    cameraProviderFuture =
      ProcessCameraProvider.getInstance(applicationContext)
    cameraProviderFuture?.addListener(
      {
        cameraSelector.let {
          cameraProviderFuture?.get()?.bindToLifecycle(this, it, preview)
        }
      },
      ContextCompat.getMainExecutor(applicationContext)
    )
  }

  private val requestPermissionLauncher =
    registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
      if (isGranted) {
        loadCam()
      }
    }

  private fun checkPermissions(): Boolean {
    var r = false
    when {
      ContextCompat.checkSelfPermission(
        applicationContext,
        Manifest.permission.CAMERA
      ) == PackageManager.PERMISSION_GRANTED -> {
        r = true
      }
      else -> {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
      }
    }

    return r
  }
}
