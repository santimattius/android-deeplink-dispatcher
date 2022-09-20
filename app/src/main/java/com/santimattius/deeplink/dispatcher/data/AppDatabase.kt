package com.santimattius.deeplink.dispatcher.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santimattius.deeplink.dispatcher.data.entities.DeepLinkEntity

@Database(entities = [DeepLinkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun deepLinkDao(): DeeplinkDao
}
