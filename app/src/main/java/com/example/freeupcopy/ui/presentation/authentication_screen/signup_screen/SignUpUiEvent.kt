package com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen

sealed class SignUpUiEvent {
    data class NameChange(val name: String): SignUpUiEvent()
    data class MobileChange(val mobile: String): SignUpUiEvent()
    data class EmailChange(val email: String): SignUpUiEvent()
    data class PasswordChange(val password: String): SignUpUiEvent()
    data class ConfirmPasswordChange(val confirmPassword: String): SignUpUiEvent()
//    data object ValidateAll: SignUpUiEvent()
    object SignUp: SignUpUiEvent()
//    object NavigateToLogin: SignUpUiEvent()
//    object NavigateToHome: SignUpUiEvent()
}
