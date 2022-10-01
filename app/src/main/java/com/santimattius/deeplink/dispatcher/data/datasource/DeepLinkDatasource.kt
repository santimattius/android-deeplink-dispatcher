package com.santimattius.deeplink.dispatcher.data.datasource

import com.santimattius.deeplink.dispatcher.data.db.DeeplinkDao
import com.santimattius.deeplink.dispatcher.data.entities.DeepLinkEntity

class DeepLinkDatasource(
    private val dao: DeeplinkDao,
) : DeepLinkLocalDatasource {

    override suspend fun getAll() = dao.getAll()

    override suspend fun insert(deepLink: DeepLinkEntity): Result<Unit> = runCatching {
        dao.insert(deepLink)
    }

    override suspend fun update(deepLink: DeepLinkEntity): Result<Unit> = runCatching {
        dao.update(deepLink)
    }

    override suspend fun delete(deepLink: DeepLinkEntity) = runCatching {
        dao.delete(deepLink)
    }
}