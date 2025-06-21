package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

sealed class SellerProfileUiEvent {
    data object FollowClicked : SellerProfileUiEvent()
    data object ProfileActionClicked : SellerProfileUiEvent()
    data object ClearError : SellerProfileUiEvent()
    // Add product tab events
    data class OnProductTabSelected(val tabIndex: Int) : SellerProfileUiEvent()
}