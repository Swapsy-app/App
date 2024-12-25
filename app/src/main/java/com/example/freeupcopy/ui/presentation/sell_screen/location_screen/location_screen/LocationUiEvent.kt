package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

import com.example.freeupcopy.data.local.Address

sealed class LocationUiEvent {
    data class OnExpandMenuClick(val addressId: Int?) : LocationUiEvent()
    data class OnSetDefault(val addressId: Int) : LocationUiEvent()
    data class OnDelete(val address: Address) : LocationUiEvent()
}