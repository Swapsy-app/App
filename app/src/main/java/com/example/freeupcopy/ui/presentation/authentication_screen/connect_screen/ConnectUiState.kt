package com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen

data class ConnectUiState(
    val transitionAnim: Boolean = false,
    val isLoading: Boolean = false,
    val error : String = "",
)