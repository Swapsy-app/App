package com.example.freeupcopy.ui.presentation.sell_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(SellUiState())
    val state: StateFlow<SellUiState> = _state

    fun onEvent(event: SellUiEvent) {
        when (event) {
            is SellUiEvent.WeightChange -> {
                _state.value = state.value.copy(weight = event.weight)
            }
            is SellUiEvent.ConditionChange -> {
                _state.value = state.value.copy(condition = event.condition)
            }
            is SellUiEvent.ManufacturingCountryChange -> {
                _state.value = state.value.copy(manufacturingCountry = event.country)
            }
            is SellUiEvent.PriceChange -> {
                _state.value = state.value.copy(price = event.price)
            }
        }
    }
}