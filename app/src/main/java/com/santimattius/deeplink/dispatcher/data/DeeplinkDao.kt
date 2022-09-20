package com.santimattius.deeplink.dispatcher.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.santimattius.deeplink.dispatcher.data.entities.DeepLinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeeplinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deepLink: DeepLinkEntity)

    @Query("SELECT * FROM deeplink")
    fun getAll(): Flow<DeepLinkEntity>

}