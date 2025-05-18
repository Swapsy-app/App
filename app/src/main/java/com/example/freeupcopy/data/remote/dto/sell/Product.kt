package com.example.freeupcopy.data.remote.dto.sell

data class Product(
    val category: Category,
    val images: List<String>,
    val video: String?,
    val title: String,
    val description: String,
    val pickupAddress: String, // Assuming this is the ObjectId as a String
    val condition: String,
    val manufacturingCountry: String,
    val weight: Double,
    val brand: String?,
    val occasion: String?,
    val color: String?,
    val shape: String?,
    val fabric: String?,
    val quantity: Int = 1,
    val shippingMethod: String,
    val gstNumber: String?,
    val sellerId: String, // Assuming this is the ObjectId as a String
    val size: Size?,
    val price: Price,
    val status: String = "available",
    val views: Int = 0
)