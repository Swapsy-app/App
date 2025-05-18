package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.domain.repository.AuthRepository
import com.example.freeupcopy.domain.repository.SellerProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(
    private val profileRepository: SellerProfileRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _state = MutableStateFlow(SellerProfileUiState())
    val state = _state.asStateFlow()

    // Retrieve the productId from the saved state
    private val userId: String? = savedStateHandle["userId"]

    init {
        if (userId != null) {
            getSellerBasicInfo()
            getProfileById()
        } else {
            getProfile()
        }
    }

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

    fun getUserProfile() {
        if (userId != null) {
            getSellerBasicInfo()
            getProfileById()
        } else {
            getProfile()
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = ""
                            )
                        }
                    }
                    is Resource.Success -> {
//                        delay(1000)
                        _state.update {
                            it.copy(
                                sellerName = result.data?.name ?: "",
                                sellerUsername = result.data?.username ?: "",
                                sellerBio = result.data?.aboutMe ?: "",
                                sellerProfileImageUrl = result.data?.avatar ?: "",
//                                shippingTime = result.data.shippingTime,
                                lastActive = result.data?.lastActive ?: "",
                                occupation = result.data?.occupation ?: "",
                                joinedTime = result.data?.createdAt ?: "",
                                gender = result.data?.gender ?: "",
                                followers = result.data?.followers ?: "0",
                                following = result.data?.following ?: "0",
//                                sold = result.data.sold,
//                                rating = result.data.rating,
//                                numberOfReviews = result.data.numberOfReviews,
//                                cancelled = result.data.cancelled,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
//                        delay(1000)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getProfileById() {
        viewModelScope.launch {
            profileRepository.getProfileById(userId!!).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = ""
                            )
                        }
                    }
                    is Resource.Success -> {
//                        delay(1000)
                        _state.update {
                            it.copy(
                                sellerName = result.data?.name ?: "",
                                sellerUsername = result.data?.username ?: "",
                                sellerBio = result.data?.aboutMe ?: "",
                                sellerProfileImageUrl = result.data?.avatar ?: "",
//                                shippingTime = result.data.shippingTime,
                                lastActive = result.data?.lastActive ?: "",
                                occupation = result.data?.occupation ?: "",
                                joinedTime = result.data?.createdAt ?: "",
                                gender = result.data?.gender ?: "",
                                followers = result.data?.followers ?: "0",
                                following = result.data?.following ?: "0",
//                                sold = result.data.sold,
//                                rating = result.data.rating,
//                                numberOfReviews = result.data.numberOfReviews,
//                                cancelled = result.data.cancelled,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
//                        delay(1000)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getSellerBasicInfo() {
        viewModelScope.launch {
            profileRepository.getUserBasicInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isMyProfile = result.data!!.user._id == userId,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false,
                                isMyProfile = false
                            )
                        }
                    }
                }
            }
        }
    }
}