package com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.OtpRequest
import com.example.freeupcopy.data.remote.dto.OtpResendRequest
import com.example.freeupcopy.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(OtpUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: OtpUiEvent) {
        when (event) {
            is OtpUiEvent.OtpChange -> {
                val otpValues = state.value.otpValues.toMutableList()
                otpValues[event.index] = event.value
               // _state.value = state.value.copy(otpValues = otpValues)
                _state.update {
                    it.copy(otpValues = otpValues)
                }
            }
            is OtpUiEvent.VerifyChange -> {
                _state.update {
                    it.copy(isVerifyEnabled = event.isVerifyEnabled)
                }
            }
            is OtpUiEvent.ResendChange -> {
                _state.update {
                    it.copy(isResendEnabled = event.isResendEnabled)
                }
            }
            is OtpUiEvent.CooldownCount -> {
               // _state.value = state.value.copy(cooldownTime = state.value.cooldownTime - 1)
                _state.update {
                    it.copy(cooldownTime = state.value.cooldownTime - 1)
                }
            }
            is OtpUiEvent.ResendOtp -> {
                // Handle resend OTP logic (e.g., call API, update state based on success or failure)
                //_state.value = state.value.copy(isResendEnabled = false, cooldownTime = 60)
                viewModelScope.launch {
                    _state.update {
                        it.copy(isResendEnabled = false, cooldownTime = 60)
                    }
                    val otpResendRequest = OtpResendRequest(
                        email = event.email
                    )
                    authRepository.resendOtp(otpResendRequest).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        isLoading = true,
                                        error = "",
                                        isSuccessfulOtpResend = false
                                    )
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "",
                                        isSuccessfulOtpResend = true
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.message ?: "An unexpected error occurred",
                                        isSuccessfulOtpResend = false,
                                        isResendEnabled = true,
                                        cooldownTime = 0
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is OtpUiEvent.VerifyOtp -> {
                viewModelScope.launch {
                    val otpRequest = OtpRequest(
                        otp = _state.value.otpValues.joinToString(""),
                        email = event.email
                    )
                    authRepository.verifyOtp(otpRequest).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        isLoading = true,
                                        error = "",
                                        signUpOtpVerifyResponse = null
                                    )
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        signUpOtpVerifyResponse = result.data,
                                        error = ""
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        signUpOtpVerifyResponse = null,
                                        error = result.message ?: "An unexpected error occurred"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}