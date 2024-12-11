package com.example.freeupcopy.ui.presentation.authentication_screen.login_screen

sealed class LoginUiEvent {
    data class EmailChange(val email: String) : LoginUiEvent()
    data class PasswordChange(val password: String) : LoginUiEvent()
    object ForgotPassword : LoginUiEvent()
    object Login : LoginUiEvent()
}