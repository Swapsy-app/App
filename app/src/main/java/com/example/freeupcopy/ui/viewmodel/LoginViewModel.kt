package com.example.freeupcopy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.ui.presentation.authentication_screen.login_screen.LoginUiEvent
import com.example.freeupcopy.ui.presentation.authentication_screen.login_screen.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChange -> {
                _state.value = state.value.copy(email = event.email)
            }
            is LoginUiEvent.PasswordChange -> {
                _state.value = state.value.copy(password = event.password)
            }
            is LoginUiEvent.ForgotPassword -> {
                // Handle forgot password logic (e.g., navigate or show dialog)
            }
            is LoginUiEvent.Login -> {
                _state.value = state.value.copy(isLoading = true)
                // Implement login logic (e.g., call API, update state based on success or failure)
            }
        }
    }
}