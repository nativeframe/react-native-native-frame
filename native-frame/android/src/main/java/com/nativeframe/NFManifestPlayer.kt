package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.OptIn
import androidx.core.view.children
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.nativeframe.databinding.NfManifestPlayerBinding
import com.oney.WebRTCModule.RTCVideoViewManager
import com.oney.WebRTCModule.WebRTCView

class NFManifestPlayer : ReactLinearLayoutCompat {
  private var binding: NfManifestPlayerBinding? = null
  private var emitter: RCTDeviceEventEmitter? = null
  private val videoViewManager = RTCVideoViewManager()

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
    (context as ReactContext?)?.let {
      emitter = it.getJSModule(RCTDeviceEventEmitter::class.java)
    }

    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    binding = NfManifestPlayerBinding.inflate(LayoutInflater.from(context))
    addView(binding?.root)

    binding?.play?.setOnClickListener {
      emitter?.emit("manifestPlayer.uri.onChanged", Arguments.createMap().apply {
        putString("uri", binding?.uri?.text.toString())
      })
    }
    binding?.quality?.adapter = ArrayAdapter(
      context,
      android.R.layout.simple_spinner_item,
      listOf("2960kbps", "1260kbps", "172kbps") //TODO load qualities
    )

    binding?.drivers?.setOnCheckedChangeListener { _, i ->
      when (i) {
        0 or 1 -> {
          setParams(WritableNativeMap().apply {
            putString("hls", last.hls)
            putString("webrtc", last.webrtc)
          })
        }

        else -> {}
      }
    }
  }

  enum class Player { webrtc, hls }

  private fun show(b: NfManifestPlayerBinding, p: Player) {
    when (p) {
      Player.webrtc -> {
        b.playerHls.visibility = View.GONE
        b.playerHls.player?.pause()
        removeWebRtcView()
      }

      Player.hls -> {
        removeWebRtcView()
        b.playerHls.visibility = View.VISIBLE
      }
    }
  }

  private fun removeWebRtcView() {
    for (v in binding?.players?.children?.filter { it is WebRTCView } ?: emptySequence()) {
      binding?.players?.removeView(v)
    }
  }

  class Params(var hls: String?, var webrtc: String?)

  private val last: Params = Params(null, null)

  @OptIn(UnstableApi::class)
  fun setParams(params: ReadableMap) {
    val b = binding ?: return

    val hls = params.getString("hls")
    val webrtc = params.getString("webrtc")

    Log.i(NFManifestPlayerManager.REACT_CLASS, "hls: $hls. webrtc: $webrtc")

    val isWebRTC = !webrtc.isNullOrBlank() && b.radioWebRtc.isChecked
    val isHls = !hls.isNullOrBlank() && b.radioHls.isChecked

    if (isWebRTC) {
      show(b, Player.webrtc)

      val webRTCView = WebRTCView(context)
      webRTCView.setObjectFit("cover")

      if (last.webrtc != webrtc) {
        videoViewManager.setStreamURL(webRTCView, webrtc)

        binding?.players?.addView(webRTCView)
        requestLayout()
      }

      last.webrtc = webrtc
    }

    if (isHls) {
      show(b, Player.hls)

      if (last.hls != hls) {
        b.playerHls.hideController()
        b.playerHls.player = ExoPlayer.Builder(context).build().apply {
          setMediaItem(MediaItem.fromUri(hls!!))
          prepare()
          play()
        }
      }

      last.hls = hls
    }
  }
}
