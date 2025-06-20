package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

import com.example.freeupcopy.data.remote.dto.product.User

data class SellerProfileUiState(
    val sellerId: String = "",

    val errorId: Long = 0L, // Add this field

    val currentUser: User? = null,

    val sellerName: String = "",
    val sellerUsername: String = "",
    val sellerBio: String = "",
    val sellerProfileImageUrl: String = "",
    val shippingTime: String = "4 days",
    val lastActive: String = "",
    val occupation: String = "",
    val joinedTime: String = "",
    val gender: String = "",
    val followers: String = "0",
    val following: String = "0",
    val sold: String = "12",
    val rating: String = "4.5",
    val numberOfReviews: String = "8",
    val cancelled: String = "2",
    val isMyProfile: Boolean = true,
    val isFollowing: Boolean = false,

    val isProfileActionSheetOpen: Boolean = false,

    val isLoading: Boolean = false,
    val error: String = "",

    val isFollowLoading: Boolean = false,
)
