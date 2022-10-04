package com.santimattius.deeplink.airbnb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.deeplinkdispatch.DeepLink
import com.santimattius.deeplink.airbnb.ui.theme.DeeplinkDispatcherTheme

@AppDeepLink(value = ["/sample"])
@DeepLink("app://dispatcher/deeplink/{id}", "app://dispatcher/{id}")
class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        var idString: String? = "Android"
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            val parameters = intent.extras
            idString = parameters!!.getString("id")
            // Do something with idString
        }
        setContent {
            DeeplinkDispatcherTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.title_activity_sample)) }) }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center
                        ) {
                            Greeting(idString ?: "Android")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeeplinkDispatcherTheme {
        Greeting("Android")
    }
}