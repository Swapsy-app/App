package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.domain.repository.LocationRepository
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.domain.use_case.GetAddressesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val sellRepository: SellRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LocationUiState())
    val state: StateFlow<LocationUiState> = _state.asStateFlow()

    val addresses = getAddressesUseCase().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        PagingData.empty()
    )


    fun onEvent(event: LocationUiEvent) {
        when (event) {
            is LocationUiEvent.ChangeLoading -> {
                _state.update {
                    it.copy(isLoading = event.isLoading)
                }
            }

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
                    sellRepository.setDefaultAddress(event.addressId).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
//                                _state.update {
//                                    it.copy(isLoading = true, error = "")
//                                }
                            }

                            is Resource.Success -> {
//                                delay(1000)
                                _state.update {
                                    it.copy(isMenuExpandedAddressId = null)
                                }
                            }

                            is Resource.Error -> {
//                                delay(1000)
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "Unknown error",
                                        isMenuExpandedAddressId = null
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is LocationUiEvent.OnDelete -> {
                viewModelScope.launch {

//                    repository.deleteAddress(event.address).collect { result ->
//                        when (result) {
//                            is Resource.Loading -> {
//                                _state.update { it.copy(isLoading = true, error = "") }
//                            }
//
//                            is Resource.Success -> {
//                                _state.update {
//                                    it.copy(isMenuExpandedAddressId = null, isLoading = false)
//                                }
//                            }
//
//                            is Resource.Error -> {
//                                _state.update {
//                                    it.copy(
//                                        error = result.message ?: "Unknown error",
//                                        isLoading = false
//                                    )
//                                }
//                            }
//                        }
//                    }

                    sellRepository.deleteAddress(event.addressId).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
//                                _state.update { it.copy(isLoading = true, error = "") }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(isMenuExpandedAddressId = null)
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "Unknown error",
                                        isMenuExpandedAddressId = null
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
