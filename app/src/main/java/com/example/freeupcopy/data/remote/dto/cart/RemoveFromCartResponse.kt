package com.example.freeupcopy.data.remote.dto.cart

data class RemoveFromCartResponse(
    val message: String,
    val cart: CartDocument? = null
)