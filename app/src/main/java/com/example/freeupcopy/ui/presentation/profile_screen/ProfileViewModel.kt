package com.example.freeupcopy.ui.presentation.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.data.pref.SwapGoPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val swapGoPref: SwapGoPref
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()


    init {
        getCurrentUser()
    }


    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.IsLoading -> {
                _state.update {
                    it.copy(isLoading = event.isLoading)
                }
            }

            ProfileUiEvent.ClearError -> {
                _state.update {
                    it.copy(error = "")
                }
            }
        }
    }


    // Add this method (similar to CommunityViewModel)
    private fun getCurrentUser() {
        viewModelScope.launch {
            swapGoPref.getUser().collect { user ->
                _state.update {
                    it.copy(
                        user = user,
                        isLoading = false
                    )
                }
            }
        }
    }
}