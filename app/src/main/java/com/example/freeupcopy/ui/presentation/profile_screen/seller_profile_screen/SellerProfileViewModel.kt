package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.domain.repository.ProfileRepository
import com.example.freeupcopy.domain.use_case.GetUserProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SellerProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val getUserProductsUseCase: GetUserProductsUseCase,
    private val swapGoPref: SwapGoPref,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _state = MutableStateFlow(SellerProfileUiState())
    val state = _state.asStateFlow()

    // Retrieve the userId from the saved state
    private val userId: String? = savedStateHandle["userId"]

    // Use userId directly instead of targetUserId
    private val _userId = MutableStateFlow<String?>(userId)

    // Create paging flows for available products
    val availableProducts = _userId
        .flatMapLatest { id ->
            if (id != null) {
                Log.e("SellerProfileViewModel", "Fetching products for userId: $id")
                getUserProductsUseCase(id, "available")
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    // Create paging flows for sold products
    val soldProducts = _userId
        .flatMapLatest { id ->
            if (id != null) {
                getUserProductsUseCase(id, "sold")
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    init {
        if (userId != null) {
            _userId.value = userId
            getCurrentUser()
            getProfileById()
        } else {
            getProfile()
        }
    }

    fun onEvent(event: SellerProfileUiEvent) {
        when (event) {
            is SellerProfileUiEvent.FollowClicked -> {
                handleFollowClick()
            }
            is SellerProfileUiEvent.ProfileActionClicked -> {
                _state.update {
                    it.copy(isProfileActionSheetOpen = !it.isProfileActionSheetOpen)
                }
            }
            is SellerProfileUiEvent.ClearError -> {
                _state.update { it.copy(error = "") }
            }
            is SellerProfileUiEvent.OnProductTabSelected -> {
                _state.update {
                    it.copy(selectedProductTab = event.tabIndex)
                }
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            swapGoPref.getUser().collect { user ->
                _state.update {
                    it.copy(
                        currentUser = user,
                        isMyProfile = user?._id == userId,
                        isLoading = false
                    )
                }
                // If no userId provided, use current user's ID
                if (userId == null) {
                    _userId.value = user?._id
                }
            }
        }
    }

    private fun handleFollowClick() {
        // Only allow follow/unfollow if we have a valid userId and it's not the user's own profile
        if (userId == null || _state.value.isMyProfile) return

        val currentFollowState = _state.value.isFollowing
        val newFollowState = !currentFollowState

        // Optimistically update the UI
        _state.update {
            it.copy(
                isFollowing = newFollowState,
                isFollowLoading = true
            )
        }

        viewModelScope.launch {
            try {
                val result = if (newFollowState) {
                    // Follow the user
                    profileRepository.followUser(userId)
                } else {
                    // Unfollow the user
                    profileRepository.unfollowUser(userId)
                }

                result.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isFollowLoading = true)
                            }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isFollowing = newFollowState,
                                    isFollowLoading = false,
                                    // Update follower count
                                    followers = updateFollowerCount(it.followers, newFollowState)
                                )
                            }
                        }
                        is Resource.Error -> {
                            // Revert the optimistic update on error
                            _state.update {
                                it.copy(
                                    isFollowing = currentFollowState,
                                    isFollowLoading = false,
                                    error = resource.message ?: "Failed to update follow status"
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                // Revert the optimistic update on exception
                _state.update {
                    it.copy(
                        isFollowing = currentFollowState,
                        isFollowLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    private fun updateFollowerCount(currentCount: String, isFollowing: Boolean): String {
        return try {
            val count = currentCount.toInt()
            val newCount = if (isFollowing) count + 1 else maxOf(0, count - 1)
            newCount.toString()
        } catch (e: NumberFormatException) {
            currentCount // Return original if parsing fails
        }
    }

    fun getUserProfile() {
        if (userId != null) {
            getCurrentUser()
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
                        _userId.value = result.data?.id
                        _state.update {
                            it.copy(
                                sellerId = result.data?.id ?: "",
                                sellerName = result.data?.name ?: "",
                                sellerUsername = result.data?.username ?: "",
                                sellerBio = result.data?.aboutMe ?: "",
                                sellerProfileImageUrl = result.data?.avatar ?: "",
                                lastActive = result.data?.lastActive ?: "",
                                occupation = result.data?.occupation ?: "",
                                joinedTime = result.data?.createdAt ?: "",
                                gender = result.data?.gender ?: "",
                                followers = result.data?.followers ?: "0",
                                following = result.data?.following ?: "0",
                                isLoading = false,
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
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
                        _state.update {
                            it.copy(
                                sellerId = result.data?.id ?: "",
                                sellerName = result.data?.name ?: "",
                                sellerUsername = result.data?.username ?: "",
                                sellerBio = result.data?.aboutMe ?: "",
                                sellerProfileImageUrl = result.data?.avatar ?: "",
                                lastActive = result.data?.lastActive ?: "",
                                occupation = result.data?.occupation ?: "",
                                joinedTime = result.data?.createdAt ?: "",
                                gender = result.data?.gender ?: "",
                                followers = result.data?.followers ?: "0",
                                following = result.data?.following ?: "0",
                                isFollowing = result.data?.isFollowing ?: false,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
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
}
