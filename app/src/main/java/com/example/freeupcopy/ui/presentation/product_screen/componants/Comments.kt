package com.example.freeupcopy.ui.presentation.product_screen.componants

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.BASE_URL_AVATAR
import com.example.freeupcopy.data.remote.dto.product.Comment
import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.product.UserSearchResult
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.TextFieldContainerColor
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss
import com.example.freeupcopy.utils.getTimeAgo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Comments(
    user: User?,
    comments: List<Comment>,
    userComment: String,
    onCommentChange: (String) -> Unit,
    sendComment: () -> Unit,
    onUserClick: (String) -> Unit,

    onReplyClickComment: (String?) -> Unit,
    onReplyClickReply: (String?, String?) -> Unit,

    // NEW parameters for comment pagination:
    isLoadingMore: Boolean,
    hasMoreComments: Boolean,
    loadMoreComments: () -> Unit,

    onCancelMention: () -> Unit,
    onLongPressComment: (String, String) -> Unit,
    onLongPressReply: (String, String) -> Unit,

    isMentioning: Boolean,
    mentionResults: List<UserSearchResult>,
    onSelectMention: (UserSearchResult) -> Unit,

    commentReplies: Map<String, List<Reply>>,
    loadMoreReplies: (String) -> Unit, // New parameter to handle reply loading
    canLoadMoreReplies: (String, Int) -> Boolean // New parameter to check if more replies can be loaded
) {

    val showMentionSuggestions by remember(isMentioning, mentionResults) {
        mutableStateOf(isMentioning && mentionResults.isNotEmpty())
    }

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


        AnimatedVisibility(
            visible = showMentionSuggestions,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Surface(
                modifier = Modifier.padding(bottom = 12.dp),
//                shape = RoundedCornerShape(8.dp),
//                shadowElevation = 4.dp,
                color = Color.Transparent
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mentionResults) {
                        MentionSuggestionItem(
                            user = it,
                            onClick = {
                                onSelectMention(it)
                            }
                        )
                    }
                }
            }
        }




        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = userComment,
                onValueChange = { newValue ->
                    onCommentChange(newValue)
                },
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
                    if (user != null) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .padding(start = 6.dp, end = 4.dp)
                                .size(42.dp)
                                .clip(CircleShape),
                            model = user.avatar,
                            loading = {
                                painterResource(id = R.drawable.im_user)
                            },
                            error = {
                                painterResource(id = R.drawable.im_user)
                            },
                            contentDescription = "profile",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .padding(start = 6.dp, end = 4.dp)
                                .size(48.dp),
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "user profile",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
                        )
                    }

                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = TextFieldContainerColor,
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

        comments.forEach { commentItem ->
            CommentItem(
                comment = commentItem,
                onReplyClickComment = {
                    onReplyClickComment(it)
                },
                onReplyClickReply = { replyId ->
                    onReplyClickReply(commentItem._id, replyId)
                },
                onLongPress = { commentId ->
                    onLongPressComment(commentId, commentItem.userId._id)
                },
                onLongPressReply = { reply ->
                    onLongPressReply(reply._id, reply.userId._id)
                },
                onUserClick = { userId ->
                    onUserClick(userId)
                },
                commentReplies = commentReplies,
                onLoadMoreReplies = { commentId ->
                    loadMoreReplies(commentId)
                },
                canLoadMoreReplies = { commentId, replyCount ->
                    canLoadMoreReplies(commentId, replyCount)
                }

            )
            Spacer(modifier = Modifier.size(16.dp))
        }

        // — Load more indicator / button —
        when {
            isLoadingMore -> {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(Modifier.size(24.dp))
                }
            }

            hasMoreComments -> {
                Button(
                    onClick = loadMoreComments,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Load more comments")
                }
            }

            else -> {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (comments.isEmpty()) {
                        Text("No comments yet")
                    } else
                        Text("No more comments to load")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentItem(
    comment: Comment,
    onReplyClickComment: (String?) -> Unit,
    onReplyClickReply: (String?) -> Unit,
    onLongPress: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onLongPressReply: (Reply) -> Unit,

    commentReplies: Map<String, List<Reply>>,
    onLoadMoreReplies: (String) -> Unit, // New parameter to handle reply loading
    canLoadMoreReplies: (String, Int) -> Boolean // New parameter to check if more replies can be loaded
) {
    var showReplies by rememberSaveable { mutableStateOf(false) }

    val replies = commentReplies[comment._id] ?: emptyList()

    // Check if more replies can be loaded
    val canLoadMoreRepliesForComment = canLoadMoreReplies(comment._id, comment.replyCount)

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.Top,

                ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onUserClick(comment.userId._id)
                            }
                        ),
                    model = BASE_URL_AVATAR + comment.userId.avatar,
                    loading = {
                        painterResource(id = R.drawable.im_user)
                    },
                    error = {
                        painterResource(id = R.drawable.im_user)
                    },
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = { /* Handle click */ },
                                onLongClick = {
                                    onLongPress(comment._id)
                                },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    ) {

                        Row {
                            Text(
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            onUserClick(comment.userId._id)
                                        }
                                    ),
                                text = comment.userId.username,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = getTimeAgo(comment.createdAt),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
                        }
                        Spacer(modifier = Modifier.size(6.dp))

                        // Use updated ClickableMentionsText with taggedUsers
                        ClickableMentionsText(
                            text = comment.commentText,
                            taggedUsers = comment.taggedUsers,
                            onMentionClick = { userId ->
                                // Now we directly get userId, not username
                                onUserClick(userId)
                            }
                        )

                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text(
                                text = "Reply",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            onReplyClickComment(comment._id)
                                        }
                                    )
                            )


                            if (comment.replyCount > 0) {
                                Text(
                                    text = if (showReplies) "Hide Replies"
                                    else "Show ${comment.replyCount} Replies",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {
                                                showReplies = !showReplies
                                                if (showReplies && replies.isEmpty()) {
                                                    // Load replies if not already loaded
                                                    onLoadMoreReplies(comment._id)
                                                }
                                            }
                                        )
                                )
                            }
                        }
                    }

                    // Show replies only when showReplies is true
                    AnimatedVisibility(
                        visible = showReplies,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            replies.forEachIndexed { i, reply ->
                                ReplyItem(
                                    reply = reply,
                                    onReplyClick = { replyId ->
                                        onReplyClickReply(replyId)
                                    },
                                    onUserClick = { userId ->
                                        onUserClick(userId)
                                    },
                                    onLongPress = {
                                        onLongPressReply(reply)
                                    }
                                )
                                if (i != replies.size - 1) {
                                    Spacer(modifier = Modifier.size(12.dp))
                                }
                            }

                            // Load more replies button
                            if (canLoadMoreRepliesForComment) {
                                Button(
                                    onClick = {
                                        onLoadMoreReplies(comment._id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Text("Load More Replies")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReplyItem(
    reply: Reply,
    onLongPress: () -> Unit,
    onReplyClick: (String) -> Unit,
    onUserClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { /* Handle click */ },
                    onLongClick = {
                        onLongPress()
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            verticalAlignment = Alignment.Top
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            onUserClick(reply.userId._id)
                        }
                    ),
                model = BASE_URL_AVATAR + reply.userId.avatar,
                loading = {
                    painterResource(id = R.drawable.im_user)
                },
                error = {
                    painterResource(id = R.drawable.im_user)
                },
                contentDescription = "profile",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.size(8.dp))

            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    onUserClick(reply.userId._id)
                                }
                            ),
                        text = reply.userId.username,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = getTimeAgo(reply.createdAt),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                    )
                }

                // Use the updated ClickableMentionsText with taggedUsers
                ClickableMentionsText(
                    text = reply.replyText,
                    taggedUsers = reply.taggedUsers ?: emptyList(), // Handle possible null
                    onMentionClick = { userId ->
                        // Now we directly receive userId
                        onUserClick(userId)
                    },
//                    style = TextStyle(fontSize = 14.sp)
                )

                Text(
                    text = "Reply",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onReplyClick(reply._id)
                    }
                )
            }
        }
    }
}

@Composable
fun MentionSuggestionItem(
    user: UserSearchResult,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ButtonShape)
            .clickable { onClick() }
            .background(color = NoteContainerLight.copy(0.5f))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User avatar
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            model = BASE_URL_AVATAR + user.avatar,
            loading = {
                painterResource(id = R.drawable.im_user)
            },
            error = {
                painterResource(id = R.drawable.im_user)
            },
            contentDescription = "user avatar",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Username
        Text(
            text = user.username,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ClickableMentionsText(
    text: String,
    taggedUsers: List<User>,
    onMentionClick: (String) -> Unit,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified
) {
    val mentionPattern = "@(\\w+)".toRegex()
    val matches = mentionPattern.findAll(text)

    if (matches.none() || taggedUsers.isEmpty()) {
        // If no mentions found or no tagged users, just display regular text
        Text(text = text, style = style, color = color)
        return
    }

    // Create a map of username -> userId for quick lookup
    val taggedUserMap = taggedUsers.associateBy({ it.username }, { it._id })

    // Set up the annotated string for clickable spans
    val annotatedString = buildAnnotatedString {
        var lastIndex = 0

        for (match in matches) {
            // Add text before the mention
            append(text.substring(lastIndex, match.range.first))

            // Add the mention with styling and tag
            val username = match.groupValues[1]
            val mention = match.value // The full @username

            // Only make it clickable if this username is in the taggedUsers list
            val userId = taggedUserMap[username]
            if (userId != null) {
                // This is a valid mention - make it clickable and style it
                pushStringAnnotation(tag = "MENTION", annotation = userId)
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )) {
                    append(mention)
                }
                pop()
            } else {
                // This is just text that happens to look like a mention
                append(mention)
            }

            lastIndex = match.range.last + 1
        }

        // Add any remaining text after the last mention
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    // Create the Text with clickable mentions
    ClickableText(
        text = annotatedString,
        style = style,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "MENTION", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    // annotation.item is now the userId, not username
                    onMentionClick(annotation.item)
                }
        }
    )
}