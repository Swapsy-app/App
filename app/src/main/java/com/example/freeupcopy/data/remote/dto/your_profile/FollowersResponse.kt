package com.example.freeupcopy.data.remote.dto.your_profile


// Followers response
data class FollowersResponse(
    val followers: List<FollowerItem>,
    val currentPage: Int,
    val totalPages: Int,
    val totalFollowers: Int
)

// User data for followers/following lists
data class FollowUser(
    val _id: String,
    val name: String,
    val aboutMe: String?,
    val username: String,
    val avatar: String,
    val isVerified: Boolean
)

data class FollowerItem(
    val _id: String,
    val follower: FollowUser,
    val following: String,
    val createdAt: String?,
    val updatedAt: String?
)