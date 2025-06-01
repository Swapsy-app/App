package com.example.freeupcopy.ui.presentation.profile_screen

sealed class ProfileUiEvent {
    data class IsLoading(val isLoading: Boolean) : ProfileUiEvent()
    data object ClearError : ProfileUiEvent()
}