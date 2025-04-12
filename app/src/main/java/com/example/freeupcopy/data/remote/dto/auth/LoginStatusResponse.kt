package com.example.freeupcopy.data.remote.dto.auth

import com.example.freeupcopy.data.remote.dto.UserData

data class LoginStatusResponse(
    val message: String,
    val user: UserData
)
