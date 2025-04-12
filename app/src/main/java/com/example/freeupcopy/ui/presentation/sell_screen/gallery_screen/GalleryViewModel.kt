package com.example.freeupcopy.ui.presentation.sell_screen.gallery_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val sellRepository: SellRepository
): ViewModel() {

    private val _state = MutableStateFlow(GalleryUiState())
    val state= _state.asStateFlow()

    fun onEvent(event: GalleryUiEvent) {
        when (event) {
            is GalleryUiEvent.ChangeSelectedGalleryImages -> {
                _state.update { it.copy(selectedGalleryImages = event.selectedGalleryImages) }
            }
            is GalleryUiEvent.UploadImages -> {
                uploadImages(event.imageFiles)
            }
            is GalleryUiEvent.RemoveSelectedGalleryImage -> {
                _state.update { currentState ->
                    currentState.copy(
                        selectedGalleryImages = currentState.selectedGalleryImages.toMutableList().apply {
                            remove(event.imageUri)
                        }
                    )
                }
            }
            is GalleryUiEvent.UploadVideo -> {
                uploadVideo(event.videoFile)
            }
        }
    }

    // Helper to convert File to MultipartBody.Part.
    private fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    // Helper to convert a File to a MultipartBody.Part for videos.
    private fun prepareVideoPart(partName: String, file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    // Function to handle image uploads.
    private fun uploadImages(imageFiles: List<File>) {
        viewModelScope.launch {
            // Convert files into MultipartBody.Part list.
            val parts = imageFiles.map { file ->
                prepareFilePart("images", file)
            }
            sellRepository.uploadImages(parts).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = "",
                                onSuccessfulUpload = false
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                uploadedImage = resource.data?.images ?: emptyList(),
                                onSuccessfulUpload = true
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error uploading images",
                                onSuccessfulUpload = false
                            )
                        }
                    }
                }
            }
        }
    }

    // Function to handle video upload.
    private fun uploadVideo(videoFile: File) {
        viewModelScope.launch {
            val videoPart = prepareVideoPart("video", videoFile)
            sellRepository.uploadVideo(videoPart).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = "",
                                onSuccessfulUpload = false
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                uploadedVideo = resource.data?.video,
                                 onSuccessfulUpload = true
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error uploading video",
                                onSuccessfulUpload = false
                            )
                        }
                    }
                }
            }
        }
    }
}