package com.santimattius.deeplink.dispatcher.data

import android.net.Uri
import java.util.*

fun generate(max: Int) = (0..max).map {
    DeepLink(title = "DeepLink $it", uri = Uri.parse("test://link/$it"))
}

data class DeepLink(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val uri: Uri,
) {

}