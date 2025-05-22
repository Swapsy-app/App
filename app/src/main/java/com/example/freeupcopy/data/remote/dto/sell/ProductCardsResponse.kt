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
    val seller: User,
    val isWishlisted: Boolean,
)

fun getWishlist(wishlistProduct: WishlistedProduct): ProductCard {
    return ProductCard(
        _id = wishlistProduct.productId,
        images = listOf(wishlistProduct.image),
        brand = wishlistProduct.brand,
        title = wishlistProduct.title,
        size = wishlistProduct.size,
        price = ProductPrice(
            mrp = null,
            cashPrice = null,
            coinPrice = null,
            mixPrice = null,
            sellerReceivesCash = 0f,
            sellerReceivesCoin = 0f,
            sellerReceivesmixCash = 0f,
            sellerReceivesmixCoin = 0f
        ),
        seller = wishlistProduct.seller ?: User(
            _id = "",
            username = "unknown",
            avatar = null
        ),
        isWishlisted = true
    )
}


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
