package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

sealed class LocationUiEvent {
    data class ChangeLoading(val isLoading: Boolean) : LocationUiEvent()
    data class OnExpandMenuClick(val addressId: String?) : LocationUiEvent()
    data class OnSetDefault(val addressId: String) : LocationUiEvent()
    data class OnDelete(val addressId: String) : LocationUiEvent()
}