package com.example.freeupcopy.data.remote.dto.cart

data class UpdateSellerCartRequest(
    val sellerId: String,
    val productId: String,
    val selectedPriceMode: String
)