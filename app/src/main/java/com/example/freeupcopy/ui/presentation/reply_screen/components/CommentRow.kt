package com.example.freeupcopy.ui.presentation.reply_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.BASE_URL_AVATAR
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun CommentRow(
    modifier: Modifier = Modifier,
    commentUser: User,
    text: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .padding(start = 6.dp, end = 4.dp)
                    .size(24.dp)
                    .clip(CircleShape),
                model = BASE_URL_AVATAR + commentUser.avatar,
                loading = {
                    painterResource(id = R.drawable.im_user)
                },
                error = {
                    painterResource(id = R.drawable.im_user)
                },
                contentDescription = "profile",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = commentUser.username,
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
                text = text,
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
            commentUser = User(
                _id = "1",
                username = "John Doe",
                avatar = "avatar.png"
            ),
            text = "This is a comment."
        )
    }
}