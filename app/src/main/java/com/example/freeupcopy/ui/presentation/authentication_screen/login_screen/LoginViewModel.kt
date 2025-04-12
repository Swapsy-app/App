package com.example.freeupcopy.ui.presentation.authentication_screen.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.auth.LoginRequest
import com.example.freeupcopy.domain.repository.AuthRepository
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChange -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is LoginUiEvent.PasswordChange -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            is LoginUiEvent.ForgotPassword -> {
                // Handle forgot password logic (e.g., navigate or show dialog)
            }

            is LoginUiEvent.Login -> {
                viewModelScope.launch {
                    val loginRequest = LoginRequest(
                        email = state.value.email,
                        password = state.value.password
                    )

                    authRepository.login(loginRequest).collect { response ->
                        when (response) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        isLoading = true,
                                        error = "",
                                        signInVerifyResponse = null
                                    )
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "",
                                        signInVerifyResponse = response.data
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = response.message ?: "An unexpected error occurred",
                                        signInVerifyResponse = null
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

//        val passwordResult = Validator.validatePassword(state.value.password)
//        if (!passwordResult.isValid) return passwordResult

        return ValidationResult(true) // All validations passed
    }
}

