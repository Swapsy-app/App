package com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D


data class ConnectUiState(
    val transitionAnim: Boolean = false,
    val isLoading: Boolean = false,
    val error : String = "",
)