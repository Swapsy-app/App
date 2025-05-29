package com.example.freeupcopy.ui.presentation.community_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.remote.dto.your_profile.FollowerItem
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingItem
import com.example.freeupcopy.domain.use_case.GetFollowersUseCase
import com.example.freeupcopy.domain.use_case.GetFollowingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
    private val swapGoPref: SwapGoPref
): ViewModel() {

    private val _state = MutableStateFlow(CommunityUiState())
    val state = _state.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)

    // Create flows that react to currentUserId changes
    @OptIn(ExperimentalCoroutinesApi::class)
    val followers = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                getFollowersUseCase(userId)
            } else {
                flowOf(PagingData.empty())
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val following = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                getFollowingUseCase(userId)
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
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            swapGoPref.getUser().collect { user ->
                _currentUserId.value = user?._id
            }
        }
    }
}
data class CommunityUiState(
    val isLoading: Boolean = false,
    val error: String = ""
)
