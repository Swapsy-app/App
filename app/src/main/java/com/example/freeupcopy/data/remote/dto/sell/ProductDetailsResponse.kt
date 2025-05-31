package com.example.freeupcopy.data.remote.dto.sell

import com.example.freeupcopy.data.remote.dto.product.User

data class ProductDetailsResponse(
    val success: Boolean,
    val productId: String,
    val product: ProductDetail,
    val finalPrice: FinalPrice,
    val isWishlisted: Boolean
)

data class ProductDetail(
    val _id: String,
    val title: String,
    val brand: String?,
    val description: String,
    val images: List<String>,
    val video: String?,
    val category: Category,
    val condition: String,
    val views: Int,
    val manufacturingCountry: String,
    val weight: Double,
    val occasion: String?,
    val color: String?,
    val shape: String?,
    val fabric: String?,
    val quantitySold: Int = 0,
    val sellerId: User, // Assuming this is the ObjectId as a String
    val size: Size?,
    val price: Price,
    val status: String = "available",
)

data class FinalPrice(
    val mrp: Float,
    val cash: Float?,  // Will be taken from either product or the bargain if applicable
    val coin: Float?,  // Same here for coin pricing
    val mix: MixPrice?
)
