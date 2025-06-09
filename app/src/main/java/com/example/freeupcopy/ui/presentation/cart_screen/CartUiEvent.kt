package com.example.freeupcopy.ui.presentation.cart_screen

sealed class CartUiEvent {
    data class AddToCart(val productId: String) : CartUiEvent()
    data class RemoveProduct(val productId: String) : CartUiEvent()
    data class RemoveSeller(val sellerId: String) : CartUiEvent()
    object RefreshCart : CartUiEvent()
}