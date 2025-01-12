package com.example.freeupcopy.ui.presentation.profile_screen.edit_profile_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(EditProfileUiState())
    val state = _state.asStateFlow()

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
                _state.update {
                    it.copy(username = event.username)
                }
            }
            is EditProfileUiEvent.UserBioChanged -> {
                _state.update {
                    it.copy(userBio = event.userBio)
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
        }
    }
}