package com.preview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nativeframe.NFBroadcaster
import com.nativeframe.NFBroadcasterFragment
import com.nativeframe.databinding.NfManifestPlayerBinding
import com.preview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.views.addView(TextView(this).apply { text = "Manifest Player" })
    binding.views.addView(NfManifestPlayerBinding.inflate(LayoutInflater.from(this)).root)

    binding.views.addView(TextView(this).apply { text = "Encoder" })
    val b = NFBroadcaster(this)
    binding.views.addView(b)
    Handler(Looper.getMainLooper()).postDelayed({
      b.showFragment(supportFragmentManager)
    }, 1000)
//    with(supportFragmentManager.beginTransaction()) {
//      replace(R.id.broadcaster, NFBroadcasterFragment())
//      commit()
//    }

    binding.cam2cam.setOnClickListener {
      startActivity(Intent(this@MainActivity, Cam2CamActivity::class.java))
    }
  }

  //region permissions with launcher
// private val requestPermissionLauncher =
//   registerForActivityResult(
//     ActivityResultContracts.RequestPermission()
//   ) { isGranted: Boolean ->
//     if (isGranted) {
//       loadCam()
//     }
//   }
//
//  private fun checkPermissions(): Boolean {
//    var r = false
//    when {
//      ContextCompat.checkSelfPermission(
//        applicationContext,
//        Manifest.permission.CAMERA
//      ) == PackageManager.PERMISSION_GRANTED -> {
//        r = true
//      }
//
//      else -> {
//        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//      }
//    }
//
//    return r
//  }
  //endregion
}
