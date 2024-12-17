package com.example.freeupcopy.ui.presentation.sell_screen.weight_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


//currently this class is not used

@HiltViewModel
class WeightViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(WeightUiState())
    val state: StateFlow<WeightUiState> = _state

    fun onEvent(event: WeightUiEvent) {
        when (event) {
            is WeightUiEvent.WeightTypeSelected -> {
                _state.value = _state.value.copy(selectedWeightType = event.weightType)
            }
        }
    }
}