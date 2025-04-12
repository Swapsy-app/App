package com.example.freeupcopy.data.remote.dto.sell.address

data class Address(
    val __v: Int,
    val _id: String,
    val address: String,
    val city: String,
    val codAvailable: Boolean,
    val createdAt: String,
    val defaultAddress: Boolean,
    val deliveryAvailable: Boolean,
    val houseNumber: String,
    val name: String,
    val phoneNumber: String,
    val landmark: String,
    val pickupAvailable: Boolean,
    val pincode: String,
    val state: String,
    val updatedAt: String,
    val userId: String
)