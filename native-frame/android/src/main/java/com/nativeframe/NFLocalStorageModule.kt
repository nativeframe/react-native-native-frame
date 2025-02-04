package com.nativeframe

import android.content.Context
import com.facebook.fbreact.specs.NativeLocalStorageSpec
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = NFUtilModule.NAME)
class NFLocalStorageModule(reactContext: ReactApplicationContext) :
  NativeLocalStorageSpec(reactContext) {

  companion object {
    const val NAME = "NativeLocalStorage"
  }

  override fun getName(): String {
    return NAME
  }

  override fun setItem(key: String, value: String) {
    val sharedPref = reactApplicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(key, value)
    editor.apply()
  }

  override fun getItem(key: String): String {
    val sharedPref = reactApplicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    val username = sharedPref.getString(key, null)
    return username.toString()
  }

  override fun removeItem(key: String) {
    val sharedPref = reactApplicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.remove(key)
    editor.apply()
  }

  override fun clear() {
    val sharedPref = reactApplicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.clear()
    editor.apply()
  }
}
