package com.example.freeupcopy.ui.presentation.reply_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReplyViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(ReplyUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: ReplyUiEvent) {
        when (event) {
            is ReplyUiEvent.ReplyChanged -> {
                _state.update { it.copy(reply = event.reply) }
            }

            is ReplyUiEvent.PostReply -> {
                // Post reply
            }
        }
    }
}