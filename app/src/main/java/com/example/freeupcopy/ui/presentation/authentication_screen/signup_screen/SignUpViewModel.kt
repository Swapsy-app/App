package com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.auth.SignUpRequest
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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.NameChange -> {
                _state.update { it.copy(name = event.name) }
            }

            is SignUpUiEvent.MobileChange -> {
                _state.update { it.copy(mobile = event.mobile) }
            }

            is SignUpUiEvent.EmailChange -> {
                _state.update { it.copy(email = event.email) }
            }

            is SignUpUiEvent.PasswordChange -> {
                _state.update { it.copy(password = event.password) }
            }

            is SignUpUiEvent.ConfirmPasswordChange -> {
                _state.update { it.copy(confirmPassword = event.confirmPassword) }
            }

            is SignUpUiEvent.SignUp -> {
                viewModelScope.launch {
                    val signUpRequest = SignUpRequest(
                        name = _state.value.name,
                        email = _state.value.email,
                        mobile = _state.value.mobile,
                        password = _state.value.password,
                    )

                    authRepository.signUp(signUpRequest).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        isLoading = true,
                                        error = "",
                                        successMessage = ""
                                    )
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        successMessage = result.data?.message ?: "",
                                        error = ""
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "An unexpected error occurred",
                                        isLoading = false,
                                        successMessage = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }

//            is SignUpUiEvent.ValidateAll -> {
//                val validationResult = validateAll()
//                if (!validationResult.isValid) {
//                    _state.value = state.value.copy(error = validationResult.errorMessage.orEmpty())
//                } else {
//                    _state.value = state.value.copy(error = "", isLoading = true) // Proceed with signup logic
//                }
//            }
//            is SignUpUiEvent.SignUp -> {
//                state.value = state.value.copy(isLoading = true)
//            }
//            is SignUpUiEvent.NavigateToLogin -> {
//                state.value = state.value.copy(isLoading = false)
//            }
//            is SignUpUiEvent.NavigateToHome -> {
//                state.value = state.value.copy(isLoading = false)
//            }
        }
    }

    fun validateAll(): ValidationResult {
        val nameResult = Validator.validateName(state.value.name)
        if (!nameResult.isValid) return nameResult

        val emailResult = Validator.validateEmail(state.value.email)
        if (!emailResult.isValid) return emailResult

        val mobileResult = Validator.validateMobile(state.value.mobile)
        if (!mobileResult.isValid) return mobileResult

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
