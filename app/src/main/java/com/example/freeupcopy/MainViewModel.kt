package com.example.freeupcopy

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val swapGoPref: SwapGoPref,
) : ViewModel() {

    private val _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken = _refreshToken.asStateFlow()

    private val _isUserDataLoaded = MutableStateFlow(false)
    val isUserDataLoaded = _isUserDataLoaded.asStateFlow()

    // Add a user state flow that other ViewModels can observe
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        // Check if we already have user data in DataStore
        viewModelScope.launch {
            swapGoPref.getUser().collect { userData ->
                _user.value = userData
            }
        }
    }

    fun setRefreshToken(token: String?) {
        _refreshToken.value = token
        if (!token.isNullOrEmpty()) {
            fetchUserData()
        } else {
            _isUserDataLoaded.value = true
        }
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            profileRepository.getUserBasicInfo().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.user?.let { user ->
                            // Store in DataStore
                            swapGoPref.saveUser(user)

                            // Update the user state flow
                            _user.value = user
                        }
                        _isUserDataLoaded.value = true
                    }
                    is Resource.Error -> {
                        // Handle error
                        _isUserDataLoaded.value = true
                    }
                    is Resource.Loading -> {
                        // Loading state
                    }
                }
            }
        }
    }
}
