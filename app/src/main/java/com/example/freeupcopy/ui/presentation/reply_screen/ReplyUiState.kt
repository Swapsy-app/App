package com.example.freeupcopy.ui.presentation.reply_screen

import com.example.freeupcopy.domain.model.Comment

data class ReplyUiState(
    val reply: String = "",
    val comment: Comment? = Comment(
        id = "c2",
        username = "davidloves69",
        userId = "u4",
        text = "Can someone explain this part in more detail?",
        replies = emptyList(),
        timeStamp = "4 days ago"
    ),
    val isReplying: Boolean = false,

    val isLoading: Boolean = false,
    val error: String = ""
)