package com.example.freeupcopy.data.remote.dto.sell.address

data class AddAddressRequest(
    val houseNumber: String,
    val name: String,
    val address: String,
    val landmark: String,
    val pincode: String,
    val state: String,
    val city: String,
    val phoneNumber: String
)
