package com.example.freeupcopy.ui.presentation.authentication_screen.login_screen

import com.example.freeupcopy.data.remote.dto.auth.LoginResponse

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",

//    val shouldNavigateToHome: Boolean = false
    val signInVerifyResponse: LoginResponse? = null
)