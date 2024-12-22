package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LocationUiState())
    val state: StateFlow<LocationUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getAddresses(),
                repository.getDefaultAddress()
            ) { addressesResult, defaultId ->
                when (addressesResult) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = "",
                                defaultAddressId = defaultId
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                addresses = addressesResult.data ?: emptyList(),
                                isLoading = false,
                                defaultAddressId = defaultId
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = addressesResult.message ?: "Unknown error",
                                isLoading = false,
                                defaultAddressId = defaultId
                            )
                        }
                    }
                }
            }.collect()
        }
    }

    fun onEvent(event: LocationUiEvent) {
        when (event) {
            is LocationUiEvent.OnExpandMenuClick -> {
                _state.update {
                    it.copy(
                        isMenuExpandedAddressId = if (it.isMenuExpandedAddressId == event.addressId)
                            null
                        else event.addressId
                    )
                }
            }

            is LocationUiEvent.OnSetDefault -> {
                viewModelScope.launch {
                    repository.setDefaultAddress(event.addressId).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(isLoading = true, error = "")
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(isMenuExpandedAddressId = null, isLoading = false)
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "Unknown error",
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is LocationUiEvent.OnDelete -> {
                viewModelScope.launch {

                    repository.deleteAddress(event.address).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(isMenuExpandedAddressId = null, isLoading = false)
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "Unknown error",
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
