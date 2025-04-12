package com.example.freeupcopy.data.remote.dto.auth

data class SignUpRequest(
    val name: String,
    val mobile: String,
    val email: String,
    val password: String,
)
