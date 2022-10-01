package com.santimattius.deeplink.dispatcher.data.datasource

import com.santimattius.deeplink.dispatcher.data.entities.DeepLinkEntity

interface DeepLinkLocalDatasource {

    suspend fun getAll(): List<DeepLinkEntity>

    suspend fun insert(deepLink: DeepLinkEntity): Result<Unit>

    suspend fun update(deepLink: DeepLinkEntity): Result<Unit>

    suspend fun delete(deepLink: DeepLinkEntity): Result<Unit>
}