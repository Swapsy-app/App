package com.example.freeupcopy.ui.presentation.reply_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.domain.model.Comment
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun CommentRow(
    modifier: Modifier = Modifier,
    comment: Comment
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = comment?.username ?: "Unknown",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.W500
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(1f)
                .clip(CardShape.medium)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CardShape.medium
                )
                .background(NoteContainerLight.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = comment?.text ?: "No text",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentRowPreview() {
    SwapGoTheme {
        CommentRow(
            comment = Comment(
                id = "c2",
                username = "davidloves69",
                userId = "u4",
                text = "Can someone explain this part in more detail?",
                replies = emptyList(),
                timeStamp = "4 days ago"
            ),
        )
    }
}