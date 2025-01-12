package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(SellerProfileUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: SellerProfileUiEvent) {
        when (event) {
            is SellerProfileUiEvent.FollowClicked -> {
                _state.update {
                    it.copy(isFollowing = !it.isFollowing)
                }
            }

            is SellerProfileUiEvent.ProfileActionClicked -> {
                _state.update {
                    it.copy(isProfileActionSheetOpen = !it.isProfileActionSheetOpen)
                }
            }
        }
    }
}