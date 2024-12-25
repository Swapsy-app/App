package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

import com.example.freeupcopy.data.local.Address

data class LocationUiState(
    val addresses: List<Address> = emptyList(),
    val isMenuExpandedAddressId: Int? = null,
    val defaultAddressId: Int? = null,

    val isLoading: Boolean = false,
    val error: String = ""
)