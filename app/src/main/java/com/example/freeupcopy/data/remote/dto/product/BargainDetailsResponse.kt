package com.example.freeupcopy.data.remote.dto.product

data class BargainDetailsResponse(
    val message: String,
    val bargain: BargainDetails
)

data class BargainDetails(
    val _id: String,
    val status: String,
    val offeredPrice: Double,
    val offeredIn: String,
    val message: String?
)