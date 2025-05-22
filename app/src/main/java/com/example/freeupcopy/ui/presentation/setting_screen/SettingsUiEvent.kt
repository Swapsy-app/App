package com.example.freeupcopy.ui.presentation.setting_screen

sealed class SettingsUiEvent {
    data object LogOut : SettingsUiEvent()
}