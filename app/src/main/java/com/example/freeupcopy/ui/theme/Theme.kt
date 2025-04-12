package com.example.freeupcopy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = SurfaceDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    tertiaryContainer = TertiaryContainerDark,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight1,
    onPrimary = OnPrimary,
    secondary = SecondaryLight,
    onSecondary = OnSecondary,
    tertiary = TertiaryLight,
    surface = SurfaceLight,
    primaryContainer = PrimaryContainerLight,
    secondaryContainer = SecondaryContainer,
    onPrimaryContainer = OnPrimaryContainerLight,
    tertiaryContainer = TertiaryContainer,
    onSurface = Color.Black,
    onTertiary = Color.Black

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val rippleConfiguration = RippleConfiguration(
        color = PrimaryLight,
        rippleAlpha = RippleAlpha(
            pressedAlpha = 0.3f,
            focusedAlpha = 0.3f,
            draggedAlpha = 0.2f,
            hoveredAlpha = 0.3f
        )
    )

    CompositionLocalProvider(
        LocalRippleConfiguration provides rippleConfiguration
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}