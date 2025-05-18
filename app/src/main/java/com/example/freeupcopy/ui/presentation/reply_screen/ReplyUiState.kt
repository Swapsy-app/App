package com.example.freeupcopy.ui.presentation.reply_screen

import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.product.UserSearchResult

data class ReplyUiState(
    val reply: String = "",
    val commentUser: User? = null,
    val textReplying: String = "",
    val isReplying: Boolean = false,

    val onSuccessFullAdd: Boolean = false,
    val successfulReply: Reply? = null,

    val isMentioning: Boolean = false,
    val mentionQuery: String = "",
    val mentionResults: List<UserSearchResult> = emptyList(),
    val mentionedUsers: List<String> = emptyList(), // Store usernames that have been mentioned

    val user: User? = null,
    val successMessage: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)