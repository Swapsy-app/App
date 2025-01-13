package com.example.freeupcopy.ui.order_confirmation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderStateViewModel : ViewModel() {
    private val _state = MutableStateFlow(OrderConfirmationUiState())
    val state = _state.asStateFlow()
}