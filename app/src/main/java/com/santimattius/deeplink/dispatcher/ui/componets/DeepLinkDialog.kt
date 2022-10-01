package com.santimattius.deeplink.dispatcher.ui.componets

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.santimattius.deeplink.dispatcher.R

@Stable
data class DeepLinkDialogState(var title: String, var uri: String) {

    fun isValid(): Boolean {
        if (title.isEmpty() || uri.isEmpty()) return false
        return runCatching {
            Uri.parse(uri).isDeepLink()
        }.getOrDefault(false)
    }

    private fun Uri.isDeepLink(): Boolean {
        return !scheme.isNullOrBlank() && !host.isNullOrBlank() && !path.isNullOrBlank()
    }
}

@Composable
fun rememberDeepLinkDialogState(
    title: String = remember { "" },
    uri: String = remember { "" },
): DeepLinkDialogState = remember(title, uri) {
    DeepLinkDialogState(title, uri)
}

@Composable
fun DeepLinkDialog(
    state: DeepLinkDialogState = rememberDeepLinkDialogState(),
    onShowDialog: (Boolean) -> Unit,
    onCreate: (DeepLinkDialogState) -> Unit,
) {

    var currentState by remember {
        mutableStateOf(state)
    }

    var error by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Create new deeplink",
                        style = MaterialTheme.typography.h6
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = colorResource(android.R.color.darker_gray),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { onShowDialog(false) }
                    )
                }

                TextInput(
                    value = currentState.title,
                    placeHolder = "Example",
                    onValueChange = {
                        currentState = currentState.copy(title = it)
                    },
                    error = error,
                )

                TextInput(
                    value = currentState.uri,
                    placeHolder = "scheme://host/path",
                    error = error,
                    onValueChange = {
                        currentState = currentState.copy(uri = it)
                    }
                )

                Button(
                    onClick = {
                        if (currentState.isValid()) {
                            onCreate(currentState)
                            onShowDialog(false)
                        } else {
                            error = "Has error"
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                ) {
                    Text(text = "Create")
                }
            }
        }
    }
}

@Composable
private fun TextInput(
    value: String,
    placeHolder: String = "",
    onValueChange: (String) -> Unit,
    error: String,
) {
    val errorColor = if (error.isEmpty()) R.color.purple_200 else R.color.purple_500

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = errorColor)
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = placeHolder) },
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = onValueChange)
}

@Preview(showSystemUi = true)
@Composable
fun DialogPreview() {
    DeepLinkDialog(onShowDialog = {}, onCreate = {})
}