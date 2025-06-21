package com.example.freeupcopy.data.remote.dto.product

// Request parameters
data class DeliveryEstimationRequest(
    val productId: String,
    val pincode: String? = null
)

// Response data class
data class DeliveryEstimationResponse(
    val message: String,
    val estimatedDeliveryDate: String,
    val estimatedSellerShipDate: String,
    val sellerPincode: String,
    val buyerPincode: String,
    val deliveryZone: String,
    val estimatedDays: Int
)
