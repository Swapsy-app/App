package com.example.freeupcopy.ui.presentation.authentication_screen.forgot_password_screen


sealed class ForgotUiEvent {
    data class EmailChange(val email: String): ForgotUiEvent()
    data class PasswordChange(val password: String): ForgotUiEvent()
    data class ConfirmPasswordChange(val confirmPassword: String): ForgotUiEvent()
    object ResetPassword: ForgotUiEvent()
//    object NavigateToLogin: ForgotUiEvent()
//    object NavigateToHome: ForgotUiEvent()
}