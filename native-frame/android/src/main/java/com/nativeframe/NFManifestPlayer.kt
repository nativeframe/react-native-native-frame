package com.nativeframe

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.text.trimmedLength
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.nativeframe.databinding.NfManifestPlayerBinding

class NFManifestPlayer : LinearLayoutCompat {
  private var binding: NfManifestPlayerBinding? = null
  private var emitter: RCTDeviceEventEmitter? = null

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
      listOf("2960kbps", "1260kbps", "172kbps")
    )

    (context as ReactContext?)?.let {
      emitter = it.getJSModule(RCTDeviceEventEmitter::class.java)
    }
  }

  fun setManifestUri(uri: String) {
    binding?.let {
      it.player.player?.release()

      if (uri.trimmedLength() == 0)
        return

      it.player.player = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        prepare()
      }
    }
  }
}
