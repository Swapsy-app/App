package com.example.freeupcopy.ui.presentation.main_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: MainUiEvent) {
        when(event) {
            is MainUiEvent.CurrentPageChange -> {
                _state.update {
                    it.copy(currentPage = event.currentPage)
                }
            }
        }
    }
}