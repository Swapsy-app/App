package com.example.freeupcopy.data.remote.dto.product

data class BargainOfferRequest(
    val offeredPrice: Float,
    val offeredIn: String, // "cash" or "coin"
    val sellerReceives: String,
    val message: String
)