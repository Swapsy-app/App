package com.example.freeupcopy.data.remote.dto.sell

import com.example.freeupcopy.data.remote.dto.product.User

data class ProductCardsResponse(
    val success: Boolean,
    val page: Int,
    val totalPages: Int,
    val totalProducts: Int,
    val products: List<ProductCard>
)

data class ProductCard(
    val _id: String,
    val images: List<String>,
    val brand: String?,
    val title: String,
    val size: Any?,
    val price: ProductPrice,
    val seller: User
)

data class ProductPrice(
    val mrp: Float?,
    val cashPrice: Float?,
    val coinPrice: Float?,
    val mixPrice: MixPrice?,
    val sellerReceivesCash: Float,
    val sellerReceivesCoin: Float,
    val sellerReceivesmixCash: Float,
    val sellerReceivesmixCoin: Float
)

data class MixPrice(
    val enteredCash: Float,
    val enteredCoin: Float
)

data class SellerInfo(
    val username: String,
    val avatar: String?
)
