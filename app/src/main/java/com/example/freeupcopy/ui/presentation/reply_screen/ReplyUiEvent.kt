package com.example.freeupcopy.ui.presentation.reply_screen

import com.example.freeupcopy.data.remote.dto.product.UserSearchResult

sealed class ReplyUiEvent {
    data class ReplyChanged(val reply: String) : ReplyUiEvent()
    data object PostReply : ReplyUiEvent()

    // New mention events
    data class SelectMention(val user: UserSearchResult): ReplyUiEvent()
    data object CancelMention: ReplyUiEvent()
}