package com.example.freeupcopy.ui.presentation.setting_screen

data class SettingsUiState(
    val onSuccessfulLogOut: Boolean = false,

    val isLoading: Boolean = false,
    val error: String = "",
)