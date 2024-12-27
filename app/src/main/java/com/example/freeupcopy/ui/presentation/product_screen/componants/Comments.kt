package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.Comment
import com.example.freeupcopy.domain.model.Reply
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Comments(
    comment: List<Comment>,
    userComment: String,
    userReply: String,
    onCommentChange: (String) -> Unit,
    onReplyChange: (String) -> Unit,
    sendComment: () -> Unit,
    onReplyClick: (String?) -> Unit,
    sendReply: (String) -> Unit,
    toReplyId: String?,
) {
    Column(
        modifier = Modifier
            .clearFocusOnKeyboardDismiss()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Row {
            Icon(
                painter = painterResource(R.drawable.ic_comment),
                contentDescription = "post comment",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Comments",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = userComment,
                onValueChange = { onCommentChange(it) },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Add a comment...",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.weight(1f),
                shape = TextFieldShape,
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 6.dp, end = 4.dp)
                            .size(48.dp),
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "user profile",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.03f),
                )
            )

            Spacer(modifier = Modifier.size(8.dp))

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(ButtonShape)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable {
                        sendComment()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = "post comment",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.size(24.dp))
        comment.forEach { commentItem ->
            CommentItem(
                comment = commentItem,
                userReply = userReply,
                isReplying = toReplyId == commentItem.id,
                onReplyChange = onReplyChange,
                sendReply = sendReply,
                onReplyClick = {
                    onReplyClick(it)
                },
                replyToReplyId = toReplyId,
//                onReplyToReplyClick = { reply ->
//                    onReplyToReplyClick(reply?.id)
//                }
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    isReplying: Boolean,
    replyToReplyId: String?,
    onReplyChange: (String) -> Unit,
    userReply: String,
    sendReply: (String) -> Unit,
    onReplyClick: (String?) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "User image",
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Row {
                        Text(
                            text = comment.user,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = comment.timeStamp,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = comment.text
                    )

                    // Only show Reply button if no reply UI is open
                    if (!isReplying) {
                        Text(
                            text = "Reply",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    onReplyClick(comment.id)
                                }
                        )
                    }

                    // Show reply UI for main comment
                    AnimatedVisibility(isReplying) {
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                modifier = Modifier
                                    .rotate(180f)
                                    .offset(x = 4.dp),
                                painter = painterResource(id = R.drawable.ic_reply),
                                contentDescription = "post reply",
                            )

                            ReplyTextField(
                                value = userReply,
                                onValueChange = { onReplyChange(it) },
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(focusRequester),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        //isKeyboardDone = true
                                        onReplyClick(null)
                                        focusManager.clearFocus()
                                    }
                                ),
                                placeholder = "Add a reply..."
                            )

                            IconButton(
                                onClick = {
//                                    sendReply(comment.id)
                                    onReplyClick(null)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "post reply",
                                )
                            }
                        }

                    }

                    // Show replies
                    Spacer(modifier = Modifier.size(12.dp))
                    comment.replies.forEachIndexed { i, reply ->
                        ReplyItem(
                            reply = reply,
                            parentCommentId = comment.id,
                            isReplying = replyToReplyId == reply.id,
                            onReplyClick = {
                                onReplyClick(it?.id)
                            },
                            onReplyChange = onReplyChange,
                            userReply = userReply,
                            sendReply = sendReply
                        )
                        if (i != comment.replies.size - 1) {
                            Spacer(modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReplyItem(
    reply: Reply,
    parentCommentId: String,
    isReplying: Boolean,
    onReplyClick: (Reply?) -> Unit,
    onReplyChange: (String) -> Unit,
    userReply: String,
    sendReply: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "User image",
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Row {
                    Text(
                        text = reply.user,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = reply.timeStamp,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                    )
                }
                Text(
                    text = reply.text
                )

                if (!isReplying) {
                    Text(
                        text = "Reply",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onReplyClick(reply)
                        }
                    )
                }

                AnimatedVisibility(isReplying) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(180f)
                                .offset(x = 4.dp),
                            painter = painterResource(id = R.drawable.ic_reply),
                            contentDescription = "post reply",
                        )

                        ReplyTextField(
                            value = userReply,
                            onValueChange = { onReplyChange(it) },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    onReplyClick(null)
                                    focusManager.clearFocus()
                                }
                            ),
                            placeholder = "Reply to ${reply.user}..."
                        )

                        IconButton(
                            onClick = {
                                //sendReply(parentCommentId)
                                onReplyClick(null)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "post reply",
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun CommentsPreview() {
    SwapsyTheme {
        Comments(
            comment = listOf(
                Comment(
                    id = "c1",
                    user = "Alice",
                    userId = "u1",
                    text = "This is a great post! Thanks for sharing.",
                    replies = listOf(
                        Reply(
                            id = "r1",
                            user = "Bob",
                            userId = "u2",
                            text = "I agree, very insightful!",
                            timeStamp = "25 Dec"
                        ),
                        Reply(
                            id = "r2",
                            user = "Charlie",
                            userId = "u3",
                            text = "Thanks for the info, Alice!",
                            timeStamp = "25 Dec"
                        )
                    ),
                    timeStamp = "25 Dec"
                ),
                Comment(
                    id = "c2",
                    user = "David",
                    userId = "u4",
                    text = "Can someone explain this part in more detail?",
                    replies = listOf(
                        Reply(
                            id = "r3",
                            user = "Eve",
                            userId = "u5",
                            text = "Sure, I can help. Which part do you need clarification on?",
                            timeStamp = "25 Dec"
                        )
                    ),
                    timeStamp = "25 Dec"
                ),
                Comment(
                    id = "c3",
                    user = "Frank",
                    userId = "u6",
                    text = "Interesting perspective, but I think there's another way to look at this.",
                    replies = emptyList(),
                    timeStamp = "25 Dec"
                )
            ),
            userComment = "",
            onCommentChange = {},
            sendComment = { },
            sendReply = {},
            userReply = "",
            onReplyChange = {},
            onReplyClick = {},
            toReplyId = null
        )
    }
}


//@Preview(
//    showBackground = true
//)
//@Composable
//fun CommentItemPreview() {
//    SwapsyTheme {
//        CommentItem(
//            Comment(
//                id = "c2",
//                user = "David",
//                userId = "u4",
//                text = "Can someone explain this part in more detail?",
//                replies = listOf(
//                    Reply(
//                        id = "r3",
//                        user = "Eve",
//                        userId = "u5",
//                        text = "Sure, I can help. Which part do you need clarification on?",
//                        timeStamp = "2 days ago"
//                    )
//                ),
//                timeStamp = "4 days ago"
//            ),
//            sendReply = {},
//            onReplyClick = {},
//            userReply = "",
//            onReplyChange = {},
//            isReplying = true,
//            replyToReplyId = "",
//        )
//    }
//}

@Composable
fun ReplyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = 0.4f
                ),
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        keyboardActions = keyboardActions,
        shape = TextFieldShape,
        keyboardOptions = keyboardOptions
    )
}
