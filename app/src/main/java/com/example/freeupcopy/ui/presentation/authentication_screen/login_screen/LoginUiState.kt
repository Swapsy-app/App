package com.example.freeupcopy.ui.presentation.authentication_screen.login_screen

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)