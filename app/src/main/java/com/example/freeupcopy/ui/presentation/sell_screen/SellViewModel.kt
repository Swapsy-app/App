package com.example.freeupcopy.ui.presentation.sell_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.data.local.AddressDao
import com.example.freeupcopy.data.pref.SellPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val sellPref: SellPref,
    private val addressDao: AddressDao
) : ViewModel() {

    private val _state = MutableStateFlow(SellUiState())
    val state: StateFlow<SellUiState> = _state

    init {
        loadDefaultAddress()
    }

    fun onEvent(event: SellUiEvent) {
        when (event) {
            is SellUiEvent.TitleChange -> {
                _state.update { it.copy(title = event.title) }
            }

            is SellUiEvent.DescriptionChange -> {
                _state.update { it.copy(description = event.description) }
            }

            is SellUiEvent.WeightChange -> {
                _state.update { it.copy(weight = event.weight) }
            }

            is SellUiEvent.ConditionChange -> {
                _state.update { it.copy(condition = event.condition) }
            }

            is SellUiEvent.ManufacturingCountryChange -> {
                _state.update { it.copy(manufacturingCountry = event.country) }
            }

            is SellUiEvent.PriceChange -> {
                _state.update { it.copy(price = event.price) }
            }

            is SellUiEvent.AddressChange -> {
                _state.update { it.copy(address = event.address) }
            }
        }
    }

    fun loadDefaultAddress() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }
            try {
                val defaultAddressId = sellPref.getDefaultAddress().first()
                val address = addressDao.getAddressById(defaultAddressId ?: 0)
                _state.update {
                    it.copy(
                        address = address,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }
}