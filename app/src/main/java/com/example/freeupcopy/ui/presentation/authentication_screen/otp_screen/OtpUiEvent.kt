package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen

sealed class OtpUiEvent {
    data class OtpChange(val index: Int, val value: String) : OtpUiEvent()
    data class VerifyChange(val isVerifyEnabled: Boolean) : OtpUiEvent()
    data class ResendChange(val isResendEnabled: Boolean) : OtpUiEvent()
    object CooldownCount: OtpUiEvent()
    data class ResendOtp(val email: String) : OtpUiEvent()
    data class VerifyOtp(val email: String) : OtpUiEvent()
}