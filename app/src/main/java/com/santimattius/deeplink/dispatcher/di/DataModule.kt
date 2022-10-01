package com.santimattius.deeplink.dispatcher.di

import android.app.Application
import androidx.room.Room
import com.santimattius.deeplink.dispatcher.data.DeepLinkRepository
import com.santimattius.deeplink.dispatcher.data.datasource.DeepLinkDatasource
import com.santimattius.deeplink.dispatcher.data.datasource.DeepLinkLocalDatasource
import com.santimattius.deeplink.dispatcher.data.db.AppDatabase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataModule {

    @Single
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "deeplink_db"
    ).build()

    @Single
    fun provideDeepLinkDatasource(database: AppDatabase): DeepLinkLocalDatasource {
        return DeepLinkDatasource(database.deepLinkDao())
    }

    @Factory
    fun provideDeepLinkRepository(localDatasource: DeepLinkLocalDatasource): DeepLinkRepository {
        return DeepLinkRepository(localDatasource = localDatasource)
    }
}