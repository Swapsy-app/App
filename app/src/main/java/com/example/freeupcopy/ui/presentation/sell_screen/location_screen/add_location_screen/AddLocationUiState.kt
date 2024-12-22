package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen

data class AddLocationUiState(
    val flatNo: String = "",
    val landmark: String = "",
    val roadName: String = "",
    val pincode: String = "",
    val city: String = "",
    val state: String = "",
    val isPermissionGranted: Boolean = true,
    val isConfirmSheetOpen: Boolean = false,

    val address: String = "",

    val isLoading: Boolean = false,
    val error: String = ""
)