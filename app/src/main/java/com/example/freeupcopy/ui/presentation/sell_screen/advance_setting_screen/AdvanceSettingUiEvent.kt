package com.example.freeupcopy.ui.presentation.sell_screen.advance_setting_screen

sealed class AdvanceSettingUiEvent {
    data class OnGstChanged(val gst: String) : AdvanceSettingUiEvent()
    object OnSave : AdvanceSettingUiEvent()
}