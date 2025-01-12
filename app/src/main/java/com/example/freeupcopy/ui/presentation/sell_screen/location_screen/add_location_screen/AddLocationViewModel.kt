package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.domain.repository.LocationRepository
import com.example.freeupcopy.utils.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddLocationUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddLocationUiEvent) {
        when (event) {
            is AddLocationUiEvent.FlatNoChanged -> {
                _state.update { it.copy(flatNo = event.flatNo) }
            }

            is AddLocationUiEvent.LandmarkChanged -> {
                _state.update { it.copy(landmark = event.landmark) }
            }

            is AddLocationUiEvent.RoadNameChanged -> {
                _state.update { it.copy(roadName = event.roadName) }
            }

            is AddLocationUiEvent.PincodeChanged -> {
                _state.update { it.copy(pincode = event.pincode) }
            }

            is AddLocationUiEvent.CityChanged -> {
                _state.update { it.copy(city = event.city) }
            }

            is AddLocationUiEvent.StateChanged -> {
                _state.update { it.copy(state = event.state) }
            }

            is AddLocationUiEvent.ChangeAddress -> {
                _state.update { it.copy(address = event.address) }
            }

            is AddLocationUiEvent.PermissionGranted -> {
                _state.update { it.copy(isPermissionGranted = event.permission) }
            }

            is AddLocationUiEvent.ConfirmSheetOpen -> {
                _state.update { it.copy(isConfirmSheetOpen = !it.isConfirmSheetOpen) }
            }

            is AddLocationUiEvent.SaveLocation -> {
                viewModelScope.launch {
                    repository.insertAddress(
                        Address(
                            address = _state.value.address,
                        )
                    ).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "",
                                        isConfirmSheetOpen = false
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.message ?: "Failed to save location."
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is AddLocationUiEvent.PincodeSubmit -> {
                viewModelScope.launch {
                    repository.fetchStateAndCityFromPincode(event.context, event.pincode).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        state = result.data?.state ?: "",
                                        city = result.data?.city ?: "",
                                        isLoading = false,
                                        error = ""
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.message ?: "Invalid pincode or unable to fetch details."
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is AddLocationUiEvent.MyCurrentLocation -> {
                viewModelScope.launch {
                    repository.fetchLocationAndAddress(
                        event.context,
                        event.fusedLocationClient
                    ).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        roadName = result.data?.roadName ?: "",
                                        pincode = result.data?.pincode ?: "",
                                        state = result.data?.state ?: "",
                                        city = result.data?.city ?: "",
                                        landmark = result.data?.landmark ?: "",
                                        isLoading = false,
                                        error = ""
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.message ?: "Unable to fetch current location details."
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    fun validateAll(): ValidationResult {
        if (_state.value.flatNo.isEmpty()) return ValidationResult(false, "Flat number is required")
        if (_state.value.landmark.isEmpty()) return ValidationResult(false, "Landmark is required")
        if (_state.value.roadName.isEmpty()) return ValidationResult(false, "Road name is required")
        if (_state.value.pincode.isEmpty()) return ValidationResult(false, "Pincode is required")
        if (_state.value.city.isEmpty()) return ValidationResult(false, "City is required")
        if (_state.value.state.isEmpty()) return ValidationResult(false, "State is required")

        return ValidationResult(true)
    }
}