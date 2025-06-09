package com.example.freeupcopy.data.remote.dto.cart

data class RemoveFromCartRequest(
    val sellerId: String? = null,
    val productId: String? = null
)