package com.example.freeupcopy.ui.presentation.cart_screen

import com.example.freeupcopy.data.remote.dto.cart.SellerCart

data class CartState(
    val isLoading: Boolean = false,
    val cartItems: List<SellerCart> = emptyList(),
    val totalProducts: Int = 0,
    val totalCombos: Int = 0,
    val error: String = "",
    val message: String = ""
)