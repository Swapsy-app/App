package com.example.freeupcopy.ui.presentation.cart_screen

sealed class CartUiEvent {
    data class RemoveSeller(val sellerId: String) : CartUiEvent()
    object RefreshCart : CartUiEvent()

    object ClearError : CartUiEvent() // New event for clearing errors
    object ClearMessage : CartUiEvent() // New event for clearing success messages
}