package com.example.freeupcopy.data.remote.dto.auth

data class SignUpOtpVerifyResponse (
    val message: String,
    val accessToken: String,
    val refreshToken: String
)