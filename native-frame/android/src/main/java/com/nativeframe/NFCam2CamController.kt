package com.nativeframe

import android.content.Context
import com.nativeframe.databinding.NfCam2camFragmentBinding

class NFCam2CamController(
  context: Context,
  private val binding: NfCam2camFragmentBinding
) : NFController<NfCam2camFragmentBinding>(context, binding) {

  override fun getSurfaceProvider() = binding.cameraPreviewMe.surfaceProvider
}
