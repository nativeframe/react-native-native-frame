package com.nativeframe

import android.content.Context
import com.nativeframe.databinding.NfCam2camBinding

class NFCam2CamController(
  context: Context,
  private val binding: NfCam2camBinding
) : NFCamController(context) {

  override fun getSurfaceProvider() = binding.cameraPreviewMe.surfaceProvider
}
