package com.example.freeupcopy.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.SignUpUiEvent
import com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.SignUpUiState
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState> = _state

    fun onEvent(event: SignUpUiEvent) {
        when(event) {
            is SignUpUiEvent.NameChange -> {
                _state.value = state.value.copy(name = event.name)
            }
            is SignUpUiEvent.MobileChange -> {
                _state.value = state.value.copy(mobile = event.mobile)
            }
            is SignUpUiEvent.EmailChange -> {
                _state.value = state.value.copy(email = event.email)
            }
            is SignUpUiEvent.PasswordChange -> {
                _state.value = state.value.copy(password = event.password)
            }
            is SignUpUiEvent.ConfirmPasswordChange -> {
                _state.value = state.value.copy(confirmPassword = event.confirmPassword)
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
