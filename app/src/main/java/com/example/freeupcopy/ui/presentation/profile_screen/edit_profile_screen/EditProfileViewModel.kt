package com.example.freeupcopy.ui.presentation.profile_screen.edit_profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.your_profile.UpdateProfileResponse
import com.example.freeupcopy.domain.repository.SellerProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: SellerProfileRepository
): ViewModel() {
    private val _state = MutableStateFlow(EditProfileUiState())
    val state = _state.asStateFlow()

    init {
        getAvatars()
    }

    fun onEvent(event: EditProfileUiEvent) {
        when (event) {
            is EditProfileUiEvent.ProfilePhotoChanged -> {
                _state.update {
                    it.copy(profilePhotoUrl = event.profilePhotoUrl)
                }
            }
            is EditProfileUiEvent.UserFullNameChanged -> {
                _state.update {
                    it.copy(userFullName = event.userFullName)
                }
            }
            is EditProfileUiEvent.UsernameChanged -> {
                val isValidFormat = event.username.all { char ->
                    char.isLetterOrDigit() || char == '_'
                }

                val usernameError = when {
                    !isValidFormat -> "Username can only contain letters, numbers, and underscores"
                    event.username.isEmpty() -> "Username must be at least 1 characters long" // Optional: Add minimum length check
                    event.username.length > 30 -> "Username must be less than 30 characters" // Optional: Add maximum length check
                    else -> null // No error
                }

                _state.update {
                    it.copy(
                        username = event.username,
                        usernameError = usernameError ?: ""
                    )
                }
            }
            is EditProfileUiEvent.UserBioChanged -> {
                val userBioError = if (event.userBio.length > 150)
                    "About me must be less than 150 characters"
                else
                    null

                _state.update {
                    it.copy(
                        userBio = event.userBio,
                        userBioError = userBioError ?: ""
                    )
                }
            }
            is EditProfileUiEvent.UserGenderChanged -> {
                _state.update {
                    it.copy(userGender = event.userGender)
                }
            }
            is EditProfileUiEvent.UserOccupationChanged -> {
                _state.update {
                    it.copy(userOccupation = event.userOccupation)
                }
            }
            is EditProfileUiEvent.UserProfilePhotoChange -> {
                _state.update {
                    it.copy(profilePhotoUrl = event.userProfilePhotoUrl)
                }
            }

            is EditProfileUiEvent.ConfirmProfileChangesClicked -> {
                viewModelScope.launch {

                    val avatarFilename = state.value.profilePhotoUrl.substringAfterLast("/")

                    profileRepository.updateProfile(
                        UpdateProfileResponse(
                            avatar = avatarFilename,
                            name = state.value.userFullName,
                            username = state.value.username,
                            aboutMe = state.value.userBio,
                            gender = state.value.userGender.ifBlank { null },
                            occupation = state.value.userOccupation.ifBlank { null }
                        )
                    ).collect { response ->
                        when (response) {
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(isLoading = true)
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        navigateBack = true
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = response.message ?: "An unexpected error occurred",
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAvatars() {
        viewModelScope.launch {
            profileRepository.getAvatars().collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                avatars = response.data?.avatars ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = response.message ?: "An unexpected error occurred",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateProfile(
        profilePhotoUrl: String,
        userFullName: String,
        username: String,
        userBio: String,
        userGender: String,
        userOccupation: String
    ) {
        _state.update {
            it.copy(
                profilePhotoUrl = profilePhotoUrl,
                userFullName = userFullName,
                username = username,
                userBio = userBio,
                userGender = userGender,
                userOccupation = userOccupation
            )
        }
    }
}