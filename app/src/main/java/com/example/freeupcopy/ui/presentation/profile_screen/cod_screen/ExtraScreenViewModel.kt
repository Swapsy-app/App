package com.example.freeupcopy.ui.presentation.profile_screen.cod_screen

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.ui.presentation.profile_screen.ExtraScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ExtraScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(ExtraScreenUiState())
    val state = _state.asStateFlow()

    fun toggleCod(){
        _state.update {
            it.copy(cashOnDelivery = ! _state.value.cashOnDelivery)
        }
    }

    fun toggleHoliday(){
        _state.update {
            it.copy(holidayMode = ! _state.value.holidayMode)
        }
    }
}