package com.example.freeupcopy.data.remote.dto.cart

data class AddToCartResponse(
    val message: String,
    val cart: CartDocument? = null
)