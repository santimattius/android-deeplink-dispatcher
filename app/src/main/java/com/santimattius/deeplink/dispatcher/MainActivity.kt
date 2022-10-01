package com.santimattius.deeplink.dispatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.santimattius.deeplink.dispatcher.data.generate
import com.santimattius.deeplink.dispatcher.ui.MainUiState
import com.santimattius.deeplink.dispatcher.ui.screen.MainRoute
import com.santimattius.deeplink.dispatcher.ui.screen.MainScreen
import com.santimattius.deeplink.dispatcher.ui.theme.DeeplinkNavigationTheme

@ExperimentalLifecycleComposeApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                MainRoute()
            }
        }
    }
}


@ExperimentalMaterialApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    DeeplinkNavigationTheme {
        MainScreen(
            state = MainUiState(data = generate(20)),
            onClick = {

            },
            onCreate = {

            },
            onDelete = {

            }
        )
    }
}