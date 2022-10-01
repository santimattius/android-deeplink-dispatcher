package com.santimattius.deeplink.dispatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.deeplink.dispatcher.data.DeepLink
import com.santimattius.deeplink.dispatcher.data.DeepLinkRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: DeepLinkRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState(isLoading = true))
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private var job: Job? = null

    init {
        viewModelScope.launch {
            val deepLinks = repository.all()
            _state.update { it.copy(data = deepLinks) }
            _state.update { it.copy(isLoading = false, data = deepLinks) }
        }
    }

    fun create(deepLink: DeepLink) {
        _state.update { it.copy(isLoading = true) }
        job?.cancel()
        job = viewModelScope.launch {
            repository.create(deepLink)
            val deepLinks = repository.all()
            _state.update { it.copy(isLoading = false, data = deepLinks) }
        }
    }

    fun delete(deepLink: DeepLink) {
        job?.cancel()
        job = viewModelScope.launch {
            repository.delete(deepLink)
            val deepLinks = repository.all()
            _state.update { it.copy(isLoading = false, data = deepLinks) }
        }
    }
}