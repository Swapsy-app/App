package com.example.freeupcopy.data.remote.dto.sell

import com.example.freeupcopy.data.remote.dto.product.User

data class UserBasicInfoResponse(
    val message: String,
    val user: User
)