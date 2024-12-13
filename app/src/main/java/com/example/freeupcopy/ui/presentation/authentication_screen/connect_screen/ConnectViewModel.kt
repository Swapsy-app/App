package com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(ConnectUiState())
    val state: StateFlow<ConnectUiState> = _state

    val smallCircleRadius = Animatable(0f)
    val bigCircleRadius = Animatable(0f)

    fun onEvent(event: ConnectUiEvent) {
        when (event) {
            is ConnectUiEvent.TransitionChange -> {
                _state.value = _state.value.copy(
                    transitionAnim = event.transitionAnim
                )
            }
        }
    }
}