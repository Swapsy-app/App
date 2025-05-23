package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen

import com.example.freeupcopy.data.remote.dto.auth.SignUpOtpVerifyResponse

data class OtpUiState(
    val otpValues: List<String> = List(6) { "" },
    val isVerifyEnabled: Boolean = false,
    val isResendEnabled: Boolean = false,
    val cooldownTime: Int = 60,
    val isLoading: Boolean = false,
    val error: String = "",

    val isSuccessfulOtpResend: Boolean = false,

//    val shouldNavigateToLogin: Boolean = false
    val signUpOtpVerifyResponse: SignUpOtpVerifyResponse? = null
)