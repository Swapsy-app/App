package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(OtpUiState())
    val state: StateFlow<OtpUiState> = _state

    fun onEvent(event: OtpUiEvent) {
        when (event) {
            is OtpUiEvent.OtpChange -> {
                val otpValues = state.value.otpValues.toMutableList()
                otpValues[event.index] = event.value
                _state.value = state.value.copy(otpValues = otpValues)
            }
            is OtpUiEvent.VerifyChange -> {
                _state.value = state.value.copy(isVerifyEnabled = event.isVerifyEnabled)
            }
            is OtpUiEvent.ResendChange -> {
                _state.value = state.value.copy(isResendEnabled = event.isResendEnabled)
            }
            is OtpUiEvent.CooldownCount -> {
                _state.value = state.value.copy(cooldownTime = state.value.cooldownTime - 1)
            }
            is OtpUiEvent.ResendOtp -> {
                // Handle resend OTP logic (e.g., call API, update state based on success or failure)
                _state.value = state.value.copy(isResendEnabled = false, cooldownTime = 60)
            }
            is OtpUiEvent.VerifyOtp -> {
                _state.value = state.value.copy(isLoading = true)
                // Implement OTP verification logic (e.g., call API, update state based on success or failure)
            }
        }
    }
}