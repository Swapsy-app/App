package com.example.freeupcopy.data.remote.dto.cart

data class CartActionResponse(
    val message: String,
    val cart: CartDocument? = null  // Nullable but properly typed
)