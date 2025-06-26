package com.nativeframe

import android.content.Context
import android.view.View
import android.widget.Toast
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.nativeframe.databinding.NfBroadcasterBinding

class NFBroadcasterController(
  context: Context,
  private val binding: NfBroadcasterBinding,
  onToggleCam: (enabled: Boolean) -> Unit,
  onToggleMic: (enabled: Boolean) -> Unit
) : NFCamController(context) {

  init {
    binding.goLive.setOnClickListener {
      emitter?.emit("broadcaster.onBroadcast", Arguments.createMap().apply {
        putString("uri", "hey world")
      })
    }
    binding.toggleCam.setOnClickListener {
      val e = toggle(binding.toggleCam, Arguments.createMap())

      onToggleCam(e)
      emitter?.emit("broadcaster.camera.enable", Arguments.createMap().apply {
        Toast.makeText(context, if (e) "Camera on" else "Camera off", Toast.LENGTH_SHORT).show()
      })
    }
    binding.toggleMic.setOnClickListener {
      val e = toggle(binding.toggleMic, Arguments.createMap())
      onToggleMic(e)

      emitter?.emit("broadcaster.mic.enable", Arguments.createMap().apply {
        Toast.makeText(context, if (e) "Mic on" else "Mic off", Toast.LENGTH_SHORT).show()
      })
    }
  }

  override fun getSurfaceProvider() = binding.cameraPreview.surfaceProvider

  private fun toggle(view: View, map: WritableMap): Boolean {
    val enabled = view.tag != "disable"

    if (enabled) {
      view.tag = "disable"
      map.putBoolean("enable", false)
    } else {
      view.tag = null
      map.putBoolean("enable", true)
    }

    return !enabled
  }
}

