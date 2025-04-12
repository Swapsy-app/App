package com.example.freeupcopy.data.remote.dto.your_profile

data class ProfileResponse(
    val name: String,
    val username: String,
    val gender: String,
    val occupation: String,
    val aboutMe: String,
    val email: String,
    val mobile: String,
    val gst: String,
    val createdAt: String,
    val avatar: String,
    val isOnline: String,
    val lastActive: String,
    val followers: String,
    val following: String,
)