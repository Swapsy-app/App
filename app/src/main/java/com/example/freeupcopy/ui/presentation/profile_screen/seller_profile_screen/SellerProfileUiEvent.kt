package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

sealed class SellerProfileUiEvent {
    data object FollowClicked : SellerProfileUiEvent()
    data object ProfileActionClicked : SellerProfileUiEvent()
}