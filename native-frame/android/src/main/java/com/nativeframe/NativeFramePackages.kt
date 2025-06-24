package com.nativeframe

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

@Suppress("unused")
object NativeFramePackages {
  val packages = listOf(
    //modules
    NFUtilPackage(),
    NFLocalStoragePackage(),
    EventsPackage(),

    //components
    NFButtonPackage(),
    NFVideoPlayerPackage(),
    NFManifestPlayerPackage(),
    NFBroadcasterPackage(),
    NFCam2CamPackage()
  )

  const val PERMISSION_REQUEST_CODE = 120
  fun checkAppPermissions(activity: Activity): Boolean {
    var r = false
    when {
      ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.CAMERA
      ) == PackageManager.PERMISSION_GRANTED -> {
        r = true
      }

      else -> {
        requestPermissions(
          activity,
          arrayOf(Manifest.permission.CAMERA),
          PERMISSION_REQUEST_CODE
        )
      }
    }
    return r
  }
}
