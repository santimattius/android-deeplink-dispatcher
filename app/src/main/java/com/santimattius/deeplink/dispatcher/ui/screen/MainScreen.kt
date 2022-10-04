package com.santimattius.deeplink.dispatcher.ui.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.deeplink.dispatcher.data.DeepLink
import com.santimattius.deeplink.dispatcher.ui.MainUiState
import com.santimattius.deeplink.dispatcher.ui.MainViewModel
import com.santimattius.deeplink.dispatcher.ui.componets.*
import org.koin.androidx.compose.getViewModel


@ExperimentalMaterialApi
@ExperimentalLifecycleComposeApi
@Composable
fun MainRoute(
    viewModel: MainViewModel = getViewModel(),
) {
    val handler = LocalUriHandler.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreen(
        state = state,
        onClick = {
            handler.safeOpenUri(context, it.uri.toString())
        },
        onCreate = viewModel::create,
        onDelete = viewModel::delete
    )
}

fun UriHandler.safeOpenUri(context: Context, uri: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    if (intent.resolveActivity(context.packageManager) != null) {
        openUri(uri)
    } else {
        Toast.makeText(context, "Deeplink $uri no supported", Toast.LENGTH_SHORT).show()
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    state: MainUiState,
    onClick: (DeepLink) -> Unit,
    onCreate: (DeepLink) -> Unit,
    onDelete: (DeepLink) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val dialogState = rememberDeepLinkDialogState()

    if (showDialog) {
        DeepLinkDialog(
            state = dialogState,
            onShowDialog = {
                showDialog = it
            },
            onCreate = {
                onCreate(DeepLink(title = it.title, uri = it.uri.toUri()))
            }
        )
    }
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
            when {
                state.isLoading -> {
                    Center {
                        CircularProgressIndicator()
                    }
                }
                state.data.isEmpty() -> {
                    Center {
                        Text(text = "Add your deeplinks")
                    }
                }
                else -> {
                    val deepLinks = state.data.toMutableStateList()
                    HomeContent(
                        deepLinks = deepLinks,
                        modifier = Modifier.padding(padding),
                        onClick = onClick,
                        onDelete = onDelete,
                    )
                }
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
private fun HomeContent(
    deepLinks: List<DeepLink>,
    modifier: Modifier = Modifier,
    onClick: (DeepLink) -> Unit,
    onDelete: (DeepLink) -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {

            items(deepLinks, key = { it.id }) { item ->
                DeepLinkItemView(
                    deepLink = item,
                    onClick = onClick,
                    onDelete = onDelete
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
    onDelete: (DeepLink) -> Unit = {},
) {
    val dismissState = rememberDismissState()
    val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
    if (isDismissed) {
        onDelete(deepLink)
    }
    CustomAnimatedVisibility(visible = isDismissed) {
        SwipeToDismissComponent(dismissState = dismissState) {
            ListItem(
                modifier = modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .clickable(onClick = { onClick.invoke(deepLink) }),
                text = { Text(text = deepLink.title) },
                secondaryText = { Text(text = deepLink.uri.toString()) }
            )
        }
    }
}