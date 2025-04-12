package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen

data class AddLocationUiState(
    val flatNo: String = "",
    val name: String = "",
    val landmark: String = "",
    val address: String = "",
    val pincode: String = "",
    val city: String = "",
    val state: String = "",
    val phoneNumber: String = "",

    val isPermissionGranted: Boolean = true,
    val isConfirmSheetOpen: Boolean = false,

    val completeAddress: String = "",

    val onSuccessFullAdd: Boolean = false,

    val isLoading: Boolean = false,
    val error: String = ""
)