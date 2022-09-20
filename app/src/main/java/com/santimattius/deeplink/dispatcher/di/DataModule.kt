package com.santimattius.deeplink.dispatcher.di

import android.app.Application
import androidx.room.Room
import com.santimattius.deeplink.dispatcher.data.AppDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
class DataModule {

    @Single
    @Named("hello")
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "deeplink_db"
    ).build()
}