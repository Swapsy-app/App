package com.example.freeupcopy.ui.presentation.profile_screen.edit_profile_screen

data class EditProfileUiState(
    val profilePhotoUrl: String = "",
    val userFullName: String = "",
    val username: String = "",
    val userBio: String = "",
    val userGender: String = "",
    val userOccupation: String = "",
    val avatars: List<String> = emptyList(),

    val isLoading: Boolean = false,
    val error: String = "",

    val usernameError: String = "",
    val userBioError: String = "",

    val navigateBack: Boolean = false,
)
