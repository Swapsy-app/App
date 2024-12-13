package com.example.freeupcopy.ui.presentation.authentication_screen.forgot_password_screen

data class ForgotUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isEmailValid: Boolean = true,
    val doPasswordsMatch: Boolean = true,
    val isLoading: Boolean = false,
    val error: String = "",
)