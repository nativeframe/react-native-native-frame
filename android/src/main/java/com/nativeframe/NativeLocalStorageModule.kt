package com.nativeframe

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NativeFrameModule.NAME)
class NativeLocalStorageModule(reactContext: ReactApplicationContext) :
  NativeLocalStorageSpec(reactContext) {

  companion object {
    const val NAME = "NativeLocalStorage"
  }

  override fun getName(): String {
    return NAME
  }

  override fun setItem(value: String, key: String) {
    val sharedPref = reactApplicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(key, value)
    editor.apply()
  }

  override fun getItem(key: String): String? {
    val sharedPref = reactApplicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val username = sharedPref.getString(key, null)
    return username.toString()
  }

  override fun removeItem(key: String) {
    val sharedPref = reactApplicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.remove(key)
    editor.apply()
  }

  override fun clear() {
    val sharedPref = reactApplicationContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.clear()
    editor.apply()
  }
}
