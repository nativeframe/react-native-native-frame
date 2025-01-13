package com.sandbox.components

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.CustomWebViewManagerInterface;
import com.facebook.react.viewmanagers.CustomWebViewManagerDelegate;

@ReactModule(name = NFWebViewManager.REACT_CLASS)
class NFWebViewManager(context: ReactApplicationContext) : SimpleViewManager<NFWebView>(), CustomWebViewManagerInterface<NFWebView> {
    private val delegate: CustomWebViewManagerDelegate<NFWebView, NFWebViewManager> =
        CustomWebViewManagerDelegate(this)

    override fun getDelegate(): ViewManagerDelegate<NFWebView> = delegate

    override fun getName(): String = REACT_CLASS

    override fun createViewInstance(context: ThemedReactContext): NFWebView = NFWebView(context)

    @ReactProp(name = "sourceUrl")
    override fun setSourceURL(view: NFWebView, sourceURL: String?) {
        if (sourceURL == null) {
            view.emitOnScriptLoaded(NFWebView.OnScriptLoadedEventResult.error)
            return;
        }
        view.loadUrl(sourceURL, emptyMap())
    }

    companion object {
        const val REACT_CLASS = "CustomWebView"
    }

    override fun getExportedCustomBubblingEventTypeConstants(): Map<String, Any> =
        mapOf(
            "onScriptLoaded" to
                    mapOf(
                        "phasedRegistrationNames" to
                                mapOf(
                                    "bubbled" to "onScriptLoaded",
                                    "captured" to "onScriptLoadedCapture"
                                )))
}