package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

data class SellerProfileUiState(
    val sellerName: String = "Sk Sahil Islam",
    val sellerUsername: String = "sahil_islam",
    val sellerBio: String = "We sell genuine products at great price. " +
            "Hope you will love our products and enjoy the experience.",
    val sellerProfileImageUrl: String = "https://images.unsplash.com/photo-1634170380000-4b3b3b3b3b3b",
    val shippingTime: String = "4 days",
    val lastActive: String = "2 hour ago",
    val occupation: String = "Graphic Designer",
    val joinedTime: String = "1 year ago",
    val followers: String = "696",
    val following: String = "12",
    val sold: String = "12",
    val rating: String = "4.5",
    val numberOfReviews: String = "8",
    val cancelled: String = "2",
    val isMyProfile: Boolean = true,
    val isFollowing: Boolean = false,

    val isProfileActionSheetOpen: Boolean = false,
)
