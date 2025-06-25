package com.nativeframe

import android.content.Context
import com.nativeframe.databinding.NfCam2camFragmentBinding

class NFCam2CamController(
  context: Context,
  private val binding: NfCam2camFragmentBinding
) : NFCamController(context) {

  override fun getSurfaceProvider() = binding.cameraPreviewMe.surfaceProvider
}
