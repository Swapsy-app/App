package com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen

data class SignUpUiState(
    val name: String = "",
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)
