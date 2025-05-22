package com.example.freeupcopy.data.remote.dto.your_profile

// Following response
data class FollowingResponse(
    val following: List<FollowingItem>,
    val currentPage: Int,
    val totalPages: Int,
    val totalFollowing: Int
)

data class FollowingItem(
    val _id: String,
    val follower: String,
    val following: FollowUser,
    val createdAt: String?,
    val updatedAt: String?
)