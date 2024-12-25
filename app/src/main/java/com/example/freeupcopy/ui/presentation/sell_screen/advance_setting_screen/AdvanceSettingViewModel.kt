package com.example.freeupcopy.ui.presentation.sell_screen.advance_setting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.data.pref.SellPref
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvanceSettingViewModel @Inject constructor(
    private val sellPref: SellPref
): ViewModel() {
    private val _state = MutableStateFlow(AdvanceSettingUiState())
    val state: StateFlow<AdvanceSettingUiState> = _state

    init {
        loadGst()
    }

    fun onEvent(event: AdvanceSettingUiEvent) {
        when(event) {
            is AdvanceSettingUiEvent.OnGstChanged -> {
                _state.update { it.copy(gst = event.gst) }
            }
            AdvanceSettingUiEvent.OnSave -> {
                viewModelScope.launch {
                    if (_state.value.gst.isNotBlank()) {
                        sellPref.saveGSTIN(_state.value.gst)
                    } else {
                        sellPref.clearGSTIN()
                    }
                }
            }
        }
    }

    private fun loadGst() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }
            try {
                val savedGst = sellPref.getGSTIN().first()
                _state.update {
                    it.copy(
                        gst = savedGst ?: "",
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

    fun validateAll(): ValidationResult {
        val validateGst = Validator.validateGstin(_state.value.gst)
        if (!validateGst.isValid) return validateGst

        return ValidationResult(true)
    }
}