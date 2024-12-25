package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient

sealed class AddLocationUiEvent {
    data class FlatNoChanged(val flatNo: String) : AddLocationUiEvent()
    data class LandmarkChanged(val landmark: String) : AddLocationUiEvent()
    data class RoadNameChanged(val roadName: String) : AddLocationUiEvent()
    data class PincodeChanged(val pincode: String) : AddLocationUiEvent()
    data class CityChanged(val city: String) : AddLocationUiEvent()
    data class StateChanged(val state: String) : AddLocationUiEvent()
    data class ChangeAddress(val address: String) : AddLocationUiEvent()
    data class PermissionGranted(val permission: Boolean) : AddLocationUiEvent()
    data object ConfirmSheetOpen : AddLocationUiEvent()
    data class PincodeSubmit(val context: Context, val pincode: String) : AddLocationUiEvent()
    data class MyCurrentLocation(val context: Context, val fusedLocationClient: FusedLocationProviderClient) : AddLocationUiEvent()
    object SaveLocation : AddLocationUiEvent()
}