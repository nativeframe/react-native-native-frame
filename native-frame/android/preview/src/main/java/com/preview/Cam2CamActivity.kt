package com.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nativeframe.NFCam2CamFragment
import com.preview.databinding.ActivityCam2camBinding

class Cam2CamActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCam2camBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCam2camBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(supportFragmentManager.beginTransaction()) {
      replace(R.id.container, NFCam2CamFragment())
      commit()
    }
  }
}
