package com.example.freeupcopy.data.remote.dto.product

data class BargainUpdateRequest(
    val offeredPrice: Float,
    val offeredIn: String,
    val sellerReceives: String,
    val message: String
)