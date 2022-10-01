package com.santimattius.deeplink.dispatcher.data

import androidx.core.net.toUri
import com.santimattius.deeplink.dispatcher.data.datasource.DeepLinkLocalDatasource
import com.santimattius.deeplink.dispatcher.data.entities.DeepLinkEntity

class DeepLinkRepository(
    private val localDatasource: DeepLinkLocalDatasource,
) {

    suspend fun all() = localDatasource.getAll().map { it.asDomainModel() }

    suspend fun create(deepLink: DeepLink) = localDatasource.insert(deepLink.asEntity())

    suspend fun delete(deepLink: DeepLink) = localDatasource.delete(deepLink.asEntity())

    private fun DeepLinkEntity.asDomainModel(): DeepLink {
        return DeepLink(
            id = uid,
            title = title,
            uri = uri.toUri()
        )
    }

    private fun DeepLink.asEntity(): DeepLinkEntity {
        return DeepLinkEntity(
            uid = id,
            title = title,
            uri = uri.toString()
        )
    }
}
