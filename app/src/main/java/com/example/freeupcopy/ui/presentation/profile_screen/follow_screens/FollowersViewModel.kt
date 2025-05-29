package com.example.freeupcopy.ui.presentation.profile_screen.follow_screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.freeupcopy.data.remote.dto.your_profile.FollowerItem
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingItem
import com.example.freeupcopy.domain.use_case.GetFollowersUseCase
import com.example.freeupcopy.domain.use_case.GetFollowingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase
): ViewModel() {

    private val _state = MutableStateFlow(FollowersUiState())
    val state = _state.asStateFlow()

    val userId: String? = savedStateHandle["userId"]
    private val type: String? = savedStateHandle["type"]

    val followers = if (userId != null && type == "followers") {
        getFollowersUseCase(userId).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
    } else {
        flowOf(PagingData.empty<FollowerItem>()).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
    }

    val following = if (userId != null && type == "following") {
        getFollowingUseCase(userId).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
    } else {
        flowOf(PagingData.empty<FollowingItem>()).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
    }


    init {
        Log.e("FollowersViewModel", "User ID: $userId, Type: $type")
    }

    fun onEvent(event: FollowersUiEvent) {
        when (event) {
            is FollowersUiEvent.ClearError -> {
                _state.value = _state.value.copy(error = "")
            }
        }
    }

}

sealed class FollowersUiEvent {
    data object ClearError : FollowersUiEvent()
}