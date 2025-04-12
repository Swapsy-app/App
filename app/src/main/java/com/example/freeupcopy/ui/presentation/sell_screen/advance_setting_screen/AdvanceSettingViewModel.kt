package com.example.freeupcopy.ui.presentation.sell_screen.advance_setting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.remote.dto.sell.GstRequest
import com.example.freeupcopy.domain.repository.SellRepository
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
    private val sellRepository: SellRepository
): ViewModel() {
    private val _state = MutableStateFlow(AdvanceSettingUiState())
    val state: StateFlow<AdvanceSettingUiState> = _state

//    init {
//        loadGst()
//    }

    fun onEvent(event: AdvanceSettingUiEvent) {
        when(event) {
            is AdvanceSettingUiEvent.OnGstChanged -> {
                _state.update { it.copy(gst = event.gst) }
            }
            AdvanceSettingUiEvent.OnSave -> {
//                viewModelScope.launch {
//                    if (_state.value.gst.isNotBlank()) {
//                        swapGoPref.saveGSTIN(_state.value.gst)
//                    } else {
//                        swapGoPref.clearGSTIN()
//                    }
//                }
                viewModelScope.launch {
                    sellRepository.updateGst(GstRequest(_state.value.gst)).collect { response ->
                        when(response) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "", gstResponse = null) }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        gst = response.data?.gst ?: "",
                                        isLoading = false,
                                        gstResponse = response.data
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = response.message ?: "An error occurred",
                                        gstResponse = null
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
        val validateGst = Validator.validateGstin(_state.value.gst)
        if (!validateGst.isValid) return validateGst

        return ValidationResult(true)
    }
}