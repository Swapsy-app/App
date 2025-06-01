package com.example.freeupcopy.data.remote.dto.your_profile

import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.sell.ProductPrice

// Response wrapper
data class UserProductsResponse(
    val success: Boolean,
    val userId: String,
    val products: List<UserProductCard>,
    val totalProducts: Int,
    val totalPages: Int,
    val currentPage: Int
)

// Product card for user products
data class UserProductCard(
    val _id: String,
    val images: List<String>,
    val brand: String?,
    val title: String,
    val size: UserProductSize?,
    val views: Int,
    val price: ProductPrice,
    val seller: User,
    val status: String,
    val createdAt: String
)

// Add this new data class for size
data class UserProductSize(
    val attributes: List<SizeAttribute>?,
    val freeSize: Boolean?,
    val sizeString: String?
)

// Add this data class for size attributes
data class SizeAttribute(
    val name: String?,
    val value: String?
)