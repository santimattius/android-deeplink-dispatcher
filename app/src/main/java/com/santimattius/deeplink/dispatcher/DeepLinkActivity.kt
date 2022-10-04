package com.santimattius.deeplink.dispatcher

import android.app.Activity
import android.os.Bundle
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.santimattius.deeplink.airbnb.AirbnbDeeplinkModule
import com.santimattius.deeplink.airbnb.AirbnbDeeplinkModuleRegistry

@DeepLinkHandler(AirbnbDeeplinkModule::class, AppDeepLinkModule::class)
class DeepLinkActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate =
            DeepLinkDelegate(AirbnbDeeplinkModuleRegistry(), AppDeepLinkModuleRegistry())
        deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}