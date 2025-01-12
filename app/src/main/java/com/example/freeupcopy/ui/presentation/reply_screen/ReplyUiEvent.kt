package com.example.freeupcopy.ui.presentation.reply_screen

sealed class ReplyUiEvent {
    data class ReplyChanged(val reply: String) : ReplyUiEvent()
    object PostReply : ReplyUiEvent()
}