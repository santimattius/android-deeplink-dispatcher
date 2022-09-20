package com.santimattius.deeplink.dispatcher.di

import android.app.Application
import androidx.room.Room
import com.santimattius.deeplink.dispatcher.data.AppDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DataModule::class, UiModule::class])
class AppModule {

    @Single
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "deeplink_db"
    ).build()

}