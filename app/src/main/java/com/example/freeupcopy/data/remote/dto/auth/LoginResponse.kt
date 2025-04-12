package com.example.freeupcopy.data.remote.dto.auth

data class LoginResponse (
    val message: String,
    val refreshToken: String,
    val accessToken: String
)