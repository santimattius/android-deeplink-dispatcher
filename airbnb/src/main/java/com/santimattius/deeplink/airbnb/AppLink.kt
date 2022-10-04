package com.santimattius.deeplink.airbnb

import com.airbnb.deeplinkdispatch.DeepLinkSpec

@DeepLinkSpec(prefix = ["app://navigation"])
@Retention(AnnotationRetention.RUNTIME)
annotation class AppDeepLink(vararg val value: String)