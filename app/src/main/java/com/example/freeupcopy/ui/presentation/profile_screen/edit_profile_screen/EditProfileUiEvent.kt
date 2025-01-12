package com.example.freeupcopy.ui.presentation.profile_screen.edit_profile_screen

sealed class EditProfileUiEvent {
    data class ProfilePhotoChanged(val profilePhotoUrl: String) : EditProfileUiEvent()
    data class UserFullNameChanged(val userFullName: String) : EditProfileUiEvent()
    data class UsernameChanged(val username: String) : EditProfileUiEvent()
    data class UserBioChanged(val userBio: String) : EditProfileUiEvent()
    data class UserGenderChanged(val userGender: String) : EditProfileUiEvent()
    data class UserOccupationChanged(val userOccupation: String) : EditProfileUiEvent()
}