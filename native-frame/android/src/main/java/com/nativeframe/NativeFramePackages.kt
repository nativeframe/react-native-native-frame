package com.nativeframe

@Suppress("unused")
object NativeFramePackages {
  val packages = listOf(
    //modules
    NativeFramePackage(),
    NativeLocalStoragePackage(),

    //components
    NFButtonPackage(),
    NFVideoPlayerPackage()
  )
}
