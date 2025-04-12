package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.sell.address.AddAddressRequest
import com.example.freeupcopy.domain.repository.LocationRepository
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val sellRepository: SellRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddLocationUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddLocationUiEvent) {
        when (event) {
            is AddLocationUiEvent.ChangeName -> {
                _state.update { it.copy(name = event.name) }
            }

            is AddLocationUiEvent.ChangePhoneNumber -> {
                _state.update { it.copy(phoneNumber = event.phoneNumber) }
            }

            is AddLocationUiEvent.FlatNoChanged -> {
                _state.update { it.copy(flatNo = event.flatNo) }
            }

            is AddLocationUiEvent.LandmarkChanged -> {
                _state.update { it.copy(landmark = event.landmark) }
            }

            is AddLocationUiEvent.ChangeAddress -> {
                _state.update { it.copy(address = event.roadName) }
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

            is AddLocationUiEvent.ChangeCompleteAddress -> {
                _state.update { it.copy(completeAddress = event.address) }
            }

            is AddLocationUiEvent.PermissionGranted -> {
                _state.update { it.copy(isPermissionGranted = event.permission) }
            }

            is AddLocationUiEvent.ConfirmSheetOpen -> {
                _state.update { it.copy(isConfirmSheetOpen = !it.isConfirmSheetOpen) }
            }

            is AddLocationUiEvent.SaveLocation -> {
                viewModelScope.launch {
                    sellRepository.addAddress(
                        AddAddressRequest(
                            name = _state.value.name,
                            houseNumber = _state.value.flatNo,
                            landmark = _state.value.landmark,
                            address = _state.value.address,
                            pincode = _state.value.pincode,
                            city = _state.value.city,
                            state = _state.value.state,
                            phoneNumber = _state.value.phoneNumber
                        )
                    ).collect { response ->
                        when (response) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(isLoading = true, error = "", isConfirmSheetOpen = false)
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "",
                                        isConfirmSheetOpen = false,
                                        onSuccessFullAdd = true
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = response.message ?: "Failed to save location.",
                                        isLoading = false,
                                        isConfirmSheetOpen = false
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is AddLocationUiEvent.PincodeSubmit -> {
                viewModelScope.launch {
                    locationRepository.fetchStateAndCityFromPincode(event.context, event.pincode).collect { result ->
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
                    locationRepository.fetchLocationAndAddress(
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
                                        address = result.data?.roadName ?: "",
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
        val nameResult = Validator.validateName(state.value.name)
        if (!nameResult.isValid) return nameResult

        val mobileResult = Validator.validateMobile(state.value.phoneNumber)
        if (!mobileResult.isValid) return mobileResult

        if (_state.value.flatNo.isEmpty()) return ValidationResult(false, "Flat number is required")
//        if (_state.value.landmark.isEmpty()) return ValidationResult(false, "Landmark is required")
        if (_state.value.address.isEmpty()) return ValidationResult(false, "Road name is required")
        if (_state.value.pincode.isEmpty()) return ValidationResult(false, "Pincode is required")
        if (_state.value.city.isEmpty()) return ValidationResult(false, "City is required")
        if (_state.value.state.isEmpty()) return ValidationResult(false, "State is required")

        return ValidationResult(true)
    }
}