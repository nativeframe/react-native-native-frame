package com.nativeframe

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.nativeframe.databinding.NfVideoPlayerBinding

class NFVideoPlayer : LinearLayoutCompat {
  private var binding: NfVideoPlayerBinding? = null

  constructor(context: Context) : super(context) {
    setup()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    setup()
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    setup()
  }

  private fun setup() {
    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    binding = NfVideoPlayerBinding.inflate(LayoutInflater.from(context))
    addView(binding?.root)
  }

  fun setHls(hls: String) {
    binding?.let {
      it.player.player?.release()

      it.player.player = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(hls))
        prepare()
      }
    }
  }

  fun setWebm(webm: String) {
    binding?.let {
      it.player.player?.release()

      it.player.player = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(webm))
        prepare()
      }
    }
  }

  fun release() {
    binding?.player?.let {
      it.player?.release()
      it.player = null
    }
  }
}
