package com.santimattius.deeplink.dispatcher.data.db

import androidx.room.*
import com.santimattius.deeplink.dispatcher.data.entities.DeepLinkEntity

@Dao
interface DeeplinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deepLink: DeepLinkEntity)

    @Query("SELECT * FROM deeplink")
    suspend fun getAll(): List<DeepLinkEntity>

    @Update
    suspend fun update(deepLink: DeepLinkEntity)

    @Delete(entity = DeepLinkEntity::class)
    suspend fun delete(deepLink: DeepLinkEntity)

}