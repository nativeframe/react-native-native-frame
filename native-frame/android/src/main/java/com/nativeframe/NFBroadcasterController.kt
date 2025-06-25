package com.nativeframe

import android.content.Context
import com.facebook.react.bridge.Arguments
import com.nativeframe.databinding.NfBroadcasterBinding

class NFBroadcasterController(
  context: Context,
  private val binding: NfBroadcasterBinding
) : NFCamController(context) {

  init {
    binding.goLive.setOnClickListener {
      emitter?.emit("broadcaster.onBroadcast", Arguments.createMap().apply {
        putString("uri", "hey world")
      })
    }
  }

  override fun getSurfaceProvider() = binding.cameraPreview.surfaceProvider
}

