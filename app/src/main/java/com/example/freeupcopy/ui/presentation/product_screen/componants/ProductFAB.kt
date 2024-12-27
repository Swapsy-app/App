package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProductFAB(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(48.dp)
            .clickable { onClick() }
            .border(
                1.dp,
                MaterialTheme.colorScheme.onPrimaryContainer.copy(0.25f),
                CircleShape
            )
            .background(MaterialTheme.colorScheme.primaryContainer.copy(0.9f)),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}