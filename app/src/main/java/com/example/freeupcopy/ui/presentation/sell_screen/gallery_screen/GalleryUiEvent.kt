package com.example.freeupcopy.ui.presentation.sell_screen.gallery_screen

import android.net.Uri
import java.io.File

sealed class GalleryUiEvent {
    data class ChangeSelectedGalleryImages(val selectedGalleryImages: List<Uri>) : GalleryUiEvent()
    data class UploadImages(val imageFiles: List<File>) : GalleryUiEvent()
    data class RemoveSelectedGalleryImage(val imageUri: Uri) : GalleryUiEvent()

    // New event for uploading a video.
    data class UploadVideo(val videoFile: File) : GalleryUiEvent()
}