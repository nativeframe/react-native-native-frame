package com.nativeframe

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
    NFBroadcasterPackage()
  )
}
