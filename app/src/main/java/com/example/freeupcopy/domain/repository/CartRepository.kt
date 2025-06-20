package com.example.freeupcopy.domain.repository

import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.cart.CartActionResponse
import com.example.freeupcopy.data.remote.dto.cart.CartResponse
import com.example.freeupcopy.data.remote.dto.cart.CartSummaryResponse
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(productId: String, selectedPriceMode: String): Flow<Resource<CartActionResponse>>

    suspend fun getCart(): Flow<Resource<CartResponse>>

    suspend fun getCartSummary(): Flow<Resource<CartSummaryResponse>>

    suspend fun removeFromCart(
        sellerId: String? = null,
        productId: String? = null
    ): Flow<Resource<CartActionResponse>>

    suspend fun updateProductPaymentMode(
        sellerId: String,
        productId: String,
        selectedPriceMode: String
    ): Flow<Resource<CartActionResponse>>
}