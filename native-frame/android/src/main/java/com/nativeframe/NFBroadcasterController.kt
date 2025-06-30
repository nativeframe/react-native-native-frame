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
  onBroadcast: (enabled: Boolean) -> Unit,
  onToggleCam: (enabled: Boolean) -> Unit,
  onToggleMic: (enabled: Boolean) -> Unit
) : NFCamController(context) {

  init {
    binding.goLive.tag = TAG_DISABLE
    binding.goLive.setOnClickListener {
      val e = toggle(binding.goLive, Arguments.createMap())

      onBroadcast(e)
      emitter?.emit(
        if (e) "broadcaster.onBroadcast.start" else "broadcaster.onBroadcast.pause",
        Arguments.createMap().apply {
          //TODO make useful
          putString("uri", "hey world")
        })
      //TODO hide behind a property
      Toast.makeText(
        context,
        if (e) "Broadcasting" else "Broadcast stopped",
        Toast.LENGTH_SHORT
      ).show()
    }
    binding.toggleCam.setOnClickListener {
      val e = toggle(binding.toggleCam, Arguments.createMap())

      onToggleCam(e)
      emitter?.emit("broadcaster.camera.enable", Arguments.createMap().apply {
        putBoolean("enable", e)
      })
      //TODO hide behind a property
      Toast.makeText(context, if (e) "Camera on" else "Camera off", Toast.LENGTH_SHORT).show()
    }
    binding.toggleMic.setOnClickListener {
      val e = toggle(binding.toggleMic, Arguments.createMap())
      onToggleMic(e)

      emitter?.emit("broadcaster.mic.enable", Arguments.createMap().apply {
        putBoolean("enable", e)
      })
      //TODO hide behind a property
      Toast.makeText(context, if (e) "Mic on" else "Mic off", Toast.LENGTH_SHORT).show()
    }
  }

  override fun getSurfaceProvider() = binding.cameraPreview.surfaceProvider

  companion object {
    private const val TAG_DISABLE = "to-disable"
  }

  private fun toggle(view: View, map: WritableMap): Boolean {
    val enabled = view.tag != TAG_DISABLE

    if (enabled) {
      view.tag = TAG_DISABLE
      map.putBoolean("enable", false)
    } else {
      view.tag = null
      map.putBoolean("enable", true)
    }

    return !enabled
  }
}

