package com.example.freeupcopy.ui.presentation.authentication_screen.forgot_password_screen

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.remote.dto.ForgotPasswordRequest
import com.example.freeupcopy.domain.repository.AuthRepository
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefs: SwapGoPref
): ViewModel() {
    private val _state = MutableStateFlow(ForgotUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.getAccessToken().collectLatest { token ->
                Log.e("ForgotViewModel", "Token: $token")

                authRepository.checkLoginStatus().collect { result ->
                    when(result) {
                        is Resource.Loading -> {
                            Log.e("ForgotViewModel", "Loading")
                        }
                        is Resource.Success -> {
                            Log.e("ForgotViewModel", result.data.toString())
                        }
                        is Resource.Error -> {
                            Log.e("ForgotViewModel", result.message ?: "An unexpected error occurred")
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ForgotUiEvent) {
        when(event) {
            is ForgotUiEvent.EmailChange -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }
            is ForgotUiEvent.PasswordChange -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }
            is ForgotUiEvent.ConfirmPasswordChange -> {
                _state.update {
                    it.copy(confirmPassword = event.confirmPassword)
                }
            }
            is ForgotUiEvent.ResetPassword -> {
                viewModelScope.launch {
                    val forgotPasswordRequest = ForgotPasswordRequest(
                        email = _state.value.email
                    )

                    authRepository.forgotPassword(forgotPasswordRequest).collect { result ->
                        when(result) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(isLoading = true, shouldNavigateToLogin = false)
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(isLoading = false, shouldNavigateToLogin = true)
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.message ?: "An unexpected error occurred",
                                        shouldNavigateToLogin = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun validateAll(): ValidationResult {
        val emailResult = Validator.validateEmail(state.value.email)
        if (!emailResult.isValid) return emailResult

        val passwordResult = Validator.validatePassword(state.value.password)
        if (!passwordResult.isValid) return passwordResult

        val confirmPasswordResult = if (state.value.password != state.value.confirmPassword) {
            ValidationResult(false, "Passwords do not match.")
        } else {
            ValidationResult(true)
        }
        if (!confirmPasswordResult.isValid) return confirmPasswordResult

        return ValidationResult(true) // All validations passed
    }
}