package com.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nativeframe.NFCam2CamController
import com.nativeframe.databinding.NfCam2camFragmentBinding
import com.preview.databinding.ActivityCam2camBinding

class Cam2CamActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCam2camBinding
  private lateinit var controller: NFCam2CamController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCam2camBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val v = NfCam2camFragmentBinding.inflate(layoutInflater)

    binding.root.addView(v.root)
    controller = NFCam2CamController(this, v)
    controller.initCam()
  }

  override fun onResume() {
    super.onResume()

    controller.loadCam(this, this)
  }

  override fun onPause() {
    super.onPause()

    controller.unloadCam()
  }
}
