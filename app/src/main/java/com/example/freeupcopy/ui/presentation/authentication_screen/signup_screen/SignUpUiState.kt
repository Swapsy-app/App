package com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen

import com.example.freeupcopy.domain.enums.SignUpStatus

data class SignUpUiState(
    val name: String = "",
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String = "",

    val shouldNavigateToOtp: Boolean = false,
//    val status: SignUpStatus? = null
)
