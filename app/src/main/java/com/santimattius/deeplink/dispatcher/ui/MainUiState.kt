package com.santimattius.deeplink.dispatcher.ui

import com.santimattius.deeplink.dispatcher.data.DeepLink

data class MainUiState(
    val isLoading: Boolean = false,
    val data: List<DeepLink> = emptyList(),
)