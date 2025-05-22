package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    dialogText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    confirmButtonText: String = "Confirm",
    cancelButtonText: String = "Cancel",
    dialogTitle: String? = null
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(CardShape.medium)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
        ) {
            dialogTitle?.let {
                Text(
                    text = dialogTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(Modifier.size(6.dp))
            }
            Text(
//                text = "Are you sure you want to delete this product?" +
//                        " This action cannot be undone.",
                //fontSize = 16.sp
                text = dialogText,
            )

            Spacer(Modifier.size(24.dp))

            Row {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f),
                    shape = ButtonShape
                ) {
                    Text(cancelButtonText)
                }

                Spacer(Modifier.size(8.dp))

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .weight(1f),
                    shape = ButtonShape
                ) {
                    Text(confirmButtonText)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmDialogPreview() {
    SwapGoTheme {
        ConfirmDialog(
            dialogText = "Are you sure you want to delete this product?" +
                    " This action cannot be undone.",
            onConfirm = {},
            onCancel = {}
        )
    }
}