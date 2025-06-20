package com.example.freeupcopy.data.remote.dto.cart

data class CartResponse(
    val cart: List<SellerCart>
)

data class SellerCart(
    val seller: Seller,
    val products: List<CartProduct>,
    val totalPrice: TotalPrice
)

data class Seller(
    val _id: String,
    val username: String,
    val avatar: String
)

data class CartProduct(
    val _id: String,
    val title: String,
    val image: String,
    val price: ProductPrice,
    val selectedPaymentMode: String, // Add this field
    val productPrice: Double? = null // Add this field for the actual price
)


data class ProductPrice(
    val cash: Double? = null,
    val coin: Double? = null,
    val mixCash: Double? = null,
    val mixCoin: Double? = null
)

data class TotalPrice(
    val totalCash: Double,
    val totalCoin: Double,
    val totalMix: MixPrice
)

data class MixPrice(
    val cash: Double,
    val coin: Double
)
