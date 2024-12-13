package com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen

sealed class ConnectUiEvent {
    data class TransitionChange(val transitionAnim: Boolean) : ConnectUiEvent()
}