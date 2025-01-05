package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductActionsBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onHide: () -> Unit,
    onDelete: () -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            windowInsets = WindowInsets(0.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionsButton(
                    action = "Hide",
                    onClick = onHide
                )


                ActionsButton(
                    action = "Delete",
                    onClick = onDelete,
                    textColor = MaterialTheme.colorScheme.error,
                    backgroundColor = Color(0xFFFF928F).copy(alpha = 0.12f)
                )
                ActionsButton(
                    action = "Cancel",
                    onClick = onDismiss
                )
            }
        }
    }
}

@Composable
fun ActionsButton(
    modifier: Modifier = Modifier,
    action: String,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(ButtonShape)
            .clickable { onClick() }
            .background(backgroundColor)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = action,
            fontWeight = FontWeight.W500,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductActionsBottomSheetPreview() {
    SwapGoTheme {
        ActionsButton(
            action = "Hide",
            onClick = {}
        )
    }
}