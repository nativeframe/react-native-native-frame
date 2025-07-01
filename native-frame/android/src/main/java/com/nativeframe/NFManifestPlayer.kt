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
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.nativeframe.databinding.NfManifestPlayerBinding
import com.oney.WebRTCModule.RTCVideoViewManager
import com.oney.WebRTCModule.WebRTCView

class NFManifestPlayer : ReactLinearLayoutCompat {
  private var binding: NfManifestPlayerBinding? = null
  private var emitter: RCTDeviceEventEmitter? = null
  private var webRTCView: WebRTCView? = null
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
  }

  enum class Player { webrtc, hls }

  private fun show(b: NfManifestPlayerBinding, p: Player) {
    when (p) {
      Player.webrtc -> {
        b.playerHls.visibility = View.GONE
        b.playerHls.player?.pause()
      }

      Player.hls -> {
        for (v in binding?.players?.children?.filter { it is WebRTCView } ?: emptySequence()) {
          v.visibility = View.GONE
        }
        b.playerHls.visibility = View.VISIBLE
      }
    }
  }

  @OptIn(UnstableApi::class)
  fun setParams(params: ReadableMap) {
    val b = binding ?: return

    val hls = params.getString("hls")
    val webrtc = params.getString("webrtc")

    Log.i(NFManifestPlayerManager.REACT_CLASS, "hls: $hls. webrtc: $webrtc")

    if (!webrtc.isNullOrBlank()) {
      show(b, Player.webrtc)

      if (webRTCView == null) {
        webRTCView = WebRTCView(context)
        webRTCView?.setObjectFit("cover")
      }

      webRTCView?.let {
        videoViewManager.setStreamURL(it, webrtc)

        if (binding?.players?.children?.contains(it) != true) {
          binding?.players?.addView(it)
        }
        requestLayout()
      }

      return
    }

    if (!hls.isNullOrBlank()) {
      show(b, Player.hls)

      b.playerHls.hideController()
      b.playerHls.player = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(hls))
        prepare()
        play()
      }
    }
  }
}
