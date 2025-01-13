package com.example.freeupcopy.ui.order_confirmation

data class OrderConfirmationUiState(
    val orderPlacedDate : String = "09 Dec 2024",
    val receiver : String = "Shreyash",
    val location : String = "Near New K.R Mobile Center, Maharajganj Road Hariharjganj, Jharkhand 822131",
    val finalPrice : String = "250 Rs",
    val deliveryCharge : String = "15 Rs",
    val finalEarning: String = "235 Rs"
)