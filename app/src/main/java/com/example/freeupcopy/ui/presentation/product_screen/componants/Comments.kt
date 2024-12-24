package com.example.freeupcopy.ui.presentation.product_page.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.domain.model.Comment
import com.example.freeupcopy.domain.model.Reply

@Composable
fun Comments(
    comment: List<Comment>,
    userComment: String,
    userReply: String,
    onCommentChange: (String) -> Unit,
    onReplyChange: (String) -> Unit,
    sendComment : () -> Unit,
    commentReplying : String,
    onReply: (String) -> Unit,
    sendReply: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Comments",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedTextField(
                value = userComment,
                onValueChange = { onCommentChange(it) },
                label = {
                    Text(text = "Add a comment...")
                },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = "post comment",
                modifier = Modifier
                    .clickable {
                        sendComment()
                    }
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        comment.forEach { commentItem ->
            CommentItem(comment = commentItem, nestingLevel = 0, userReply = userReply, onReplyChange = onReplyChange, sendReply = sendReply, userReplying = (commentItem.id.equals(commentReplying)), onReply = onReply)
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    nestingLevel: Int,
    userReplying : Boolean,
    onReplyChange: (String) -> Unit,
    userReply: String,
    sendReply : (String) -> Unit,
    onReply: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        VerticalDivider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(start = (nestingLevel * 16).dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = comment.user,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = comment.text,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            if(userReplying){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    OutlinedTextField(
                        value = userReply,
                        onValueChange = { onReplyChange(it) },
                        label = {
                            Text(text = "Add a reply...")
                        },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                    Icon(
                        imageVector = Icons.Rounded.Send,
                        contentDescription = "post comment",
                        modifier = Modifier
                            .clickable {
                                sendReply(comment.id)
                            }
                    )
                }
            }
            else{
                Text(
                    text = "Reply     ${comment.timeStamp}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            onReply(comment.id)
                        }
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            comment.replies.forEach { reply ->
                ReplyItem(reply = reply, nestingLevel = nestingLevel + 1)
            }
        }
    }
}

@Composable
fun ReplyItem(
    reply: Reply,
    nestingLevel: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        VerticalDivider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(start = (nestingLevel * 16).dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = reply.user,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = reply.text,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = reply.timeStamp,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun CommentsPreview(){
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
        commentReplying = "",
        sendReply = {},
        onReply = {},
        userReply = "",
        onReplyChange = {}
    )
}


@Preview(
    showBackground = true
)
@Composable
fun CommentItemPreview(){
    CommentItem(
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
        nestingLevel = 0,
        userReplying = true,
        sendReply = {},
        onReply = {},
        userReply = "",
        onReplyChange = {}
    )
}
