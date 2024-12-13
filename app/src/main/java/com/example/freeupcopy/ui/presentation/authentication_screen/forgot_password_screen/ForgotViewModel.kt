package com.example.freeupcopy.ui.presentation.authentication_screen.forgot_password_screen

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(ForgotUiState())
    val state: StateFlow<ForgotUiState> = _state

    fun onEvent(event: ForgotUiEvent) {
        when(event) {
            is ForgotUiEvent.EmailChange -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is ForgotUiEvent.PasswordChange -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is ForgotUiEvent.ConfirmPasswordChange -> {
                _state.value = _state.value.copy(confirmPassword = event.confirmPassword)
            }
            is ForgotUiEvent.ResetPassword -> {
                _state.value = _state.value.copy(isLoading = false)
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