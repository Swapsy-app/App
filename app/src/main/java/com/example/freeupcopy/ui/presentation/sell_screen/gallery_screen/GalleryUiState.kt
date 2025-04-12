package com.example.freeupcopy.ui.presentation.sell_screen.gallery_screen

import android.net.Uri

data class GalleryUiState(
    val selectedGalleryImages: List<Uri> = emptyList(),
    val uploadedImage: List<String> = emptyList(),
    val uploadedVideo: String? = null,

    val onSuccessfulUpload: Boolean = false,

    val isLoading: Boolean = false,
    val error: String = ""
)