package com.santimattius.deeplink.dispatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.santimattius.deeplink.dispatcher.data.DeepLink
import com.santimattius.deeplink.dispatcher.data.generate
import com.santimattius.deeplink.dispatcher.ui.componets.DeepLinkDialog
import com.santimattius.deeplink.dispatcher.ui.componets.rememberDeepLinkDialogState
import com.santimattius.deeplink.dispatcher.ui.theme.DeeplinkNavigationTheme

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                MainScreen()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainScreen() {
    var showDialog by remember { mutableStateOf(false) }
    val dialogState = rememberDeepLinkDialogState()

    if (showDialog)
        DeepLinkDialog(
            state = dialogState,
            onShowDialog = {
                showDialog = it
            },
            onCreate = {

            }
        )
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "DeepLink Navigation") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        content = { padding ->
            HomeContent(modifier = Modifier.padding(padding))
        }
    )
}

@ExperimentalMaterialApi
@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            val deepLinks = generate(20)
            items(deepLinks, key = { it.id }) { item ->
                DeepLinkItemView(
                    deepLink = item
                )
                Divider()
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun DeepLinkItemView(
    deepLink: DeepLink,
    modifier: Modifier = Modifier,
    onClick: (DeepLink) -> Unit = {},
) {
    ListItem(
        modifier = modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable(onClick = { onClick.invoke(deepLink) }),
        text = { Text(text = deepLink.title) },
        secondaryText = { Text(text = deepLink.uri.toString()) }
    )
}

@ExperimentalMaterialApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    DeeplinkNavigationTheme {
        MainScreen()
    }
}