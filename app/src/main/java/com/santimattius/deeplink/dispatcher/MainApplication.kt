package com.santimattius.deeplink.dispatcher

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.santimattius.deeplink.dispatcher.di.AppModule
import com.santimattius.deeplink.dispatcher.ui.MainViewModel
import com.santimattius.deeplink.dispatcher.ui.theme.DeeplinkNavigationTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(AppModule().module + module {
                viewModel { MainViewModel(get()) }
            })
        }
    }
}

@Composable
internal fun ApplicationTheme(content: @Composable () -> Unit) {
    DeeplinkNavigationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            content = content,
        )
    }
}