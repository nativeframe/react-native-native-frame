package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.common.util.concurrent.ListenableFuture
import com.nativeframe.databinding.NfBroadcasterBinding
import java.util.concurrent.Executors

class NFBroadcaster : LinearLayoutCompat {
  private var binding: NfBroadcasterBinding? = null

  private lateinit var cameraSelector: CameraSelector
  private lateinit var preview: Preview

  private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null


  constructor(context: Context) : super(context) {
    setup(context)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    setup(context)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    setup(context)
  }

  private fun setup(context: Context) {
    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    binding = NfBroadcasterBinding.inflate(LayoutInflater.from(context))
    addView(binding?.root)

    cameraSelector = CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
      .build()
    preview = Preview.Builder().build()
    preview.surfaceProvider = binding?.cameraPreview?.surfaceProvider
  }

  fun setUri(uri: String) {
  }

   fun camStart(context: Context, lifecycle: LifecycleOwner) {
    //bindPreview()
    if (cameraProviderFuture != null)
      return

     cameraProviderFuture =
       ProcessCameraProvider.getInstance(context.applicationContext)
     cameraProviderFuture?.addListener({
       Log.e("00039303", "got came")
       bindPreview(cameraProviderFuture!!.get(), lifecycle)
     }, ContextCompat.getMainExecutor(context.applicationContext))
  }

  private  fun camStop() {
//    cameraProviderFuture?.get()?.unbindAll()
//    cameraProviderFuture = null
  }

  private fun bindPreview(cameraProvider: ProcessCameraProvider, lifecycle: LifecycleOwner) {
    cameraSelector.let {
      cameraProvider.bindToLifecycle(lifecycle, it, preview)
    }
  }
}
