package com.example.freeupcopy.ui.presentation.reply_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.product.AddReplyRequest
import com.example.freeupcopy.domain.repository.ProductRepository
import com.example.freeupcopy.domain.repository.SellerProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sellerProfileRepository: SellerProfileRepository,
    private val productRepository: ProductRepository
): ViewModel() {
    private val _state = MutableStateFlow(ReplyUiState())
    val state = _state.asStateFlow()

    private val commentId: String? = savedStateHandle["commentId"]
    private val replyId: String? = savedStateHandle["replyId"]

    init {
        commentId?.let {
            if (replyId == null) {
                getUserBasicInfo()
                getCommentDetails(commentId)
            } else {
                getUserBasicInfo()
                getReplyDetails(replyId)
            }
        }
    }

    fun onEvent(event: ReplyUiEvent) {
        when (event) {
            is ReplyUiEvent.ReplyChanged -> {
                val newValue = event.reply

                // 1️⃣ Update the raw reply text
                _state.update { it.copy(reply = newValue) }

                // 2️⃣ Extract all mentions from the current reply text
                val mentions = Regex("@\\w+").findAll(newValue).map { it.value.substring(1) }.toList()

                // 3️⃣ Update the mentionedUsers list based on valid mentions
                val validMentionedUsers = _state.value.mentionResults
                    .filter { user -> mentions.contains(user.username) }
                    .map { it._id }

                // Remove any IDs from mentionedUsers that are no longer mentioned in the text
                val updatedMentionedUsers = _state.value.mentionedUsers.filter { it in validMentionedUsers }

                _state.update {
                    it.copy(mentionedUsers = updatedMentionedUsers)
                }

                // 4️⃣ Handle mention logic
                val lastWord = newValue.substringAfterLast(" ")
                val isMentionTrigger = lastWord.startsWith("@") && lastWord.length > 1

                when {
                    // Start a mention
                    isMentionTrigger -> {
                        val query = lastWord.substring(1)
                        _state.update {
                            it.copy(
                                isMentioning = true,
                                mentionQuery = query
                            )
                        }
                        searchUsers(query)
                    }

                    // Cancel mention if we were mentioning but no "@" remains
                    _state.value.isMentioning && !newValue.contains("@") -> {
                        _state.update {
                            it.copy(isMentioning = false)
                        }
                        viewModelScope.launch {
                            delay(300) // Match animation duration
                            _state.update {
                                it.copy(mentionResults = emptyList())
                            }
                        }
                    }

                    // Handle case where we're not mentioning anymore
                    else -> {
                        if (_state.value.isMentioning) {
                            _state.update {
                                it.copy(isMentioning = false)
                            }
                            viewModelScope.launch {
                                delay(300) // Match animation duration
                                _state.update {
                                    it.copy(mentionResults = emptyList())
                                }
                            }
                        }
                    }
                }
            }


            is ReplyUiEvent.PostReply -> {
                postReply()
            }

            // NEW MENTION HANDLING EVENTS

            is ReplyUiEvent.SelectMention -> {
                val currentText = _state.value.reply
                val lastAtPosition = currentText.lastIndexOf("@")

                if (lastAtPosition != -1) {
                    val textBeforeMention = currentText.substring(0, lastAtPosition)
                    val mentionText = "@${event.user.username}"

                    // Check if the mention already exists in the text
                    val mentionAlreadyInText = currentText.contains(mentionText)

                    // Check if the user ID is already in the mentionedUsers list
                    val mentionAlreadyInList = _state.value.mentionedUsers.contains(event.user._id)

                    if (!mentionAlreadyInText && !mentionAlreadyInList) {
                        val newText = "$textBeforeMention$mentionText "

                        val updatedMentionedUsers = _state.value.mentionedUsers.toMutableList()
                        updatedMentionedUsers.add(event.user._id)

                        _state.update {
                            it.copy(
                                reply = newText,
                                isMentioning = false,
                                mentionResults = emptyList(),
                                mentionedUsers = updatedMentionedUsers
                            )
                        }
                    } else {
                        // Optionally, you can provide feedback to the user that the mention already exists
                        _state.update {
                            it.copy(
                                isMentioning = false,
                                mentionResults = emptyList()
                            )
                        }
                    }
                }
            }


            is ReplyUiEvent.CancelMention -> {
                _state.update {
                    it.copy(
                        isMentioning = false,
                        mentionResults = emptyList()
                    )
                }
            }
        }
    }

    private fun getCommentDetails(commentId: String) {
        viewModelScope.launch {
            productRepository.getCommentById(commentId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        val commentUser = result.data?.comment?.userId
                        val textReplying = result.data?.comment?.commentText ?: "* No text *"
                        val currentMentionedUsers = _state.value.mentionedUsers.toMutableList()

                        commentUser?.let { user ->
                            if (!currentMentionedUsers.contains(user._id)) {
                                currentMentionedUsers.add(user._id)
                            }

                            // Automatically add the username mention to the reply text
                            _state.update {
                                it.copy(
                                    reply = "@${user.username} "
                                )
                            }
                        }

                        _state.update {
                            it.copy(
                                commentUser = commentUser,
                                textReplying = textReplying,
                                isLoading = false,
                                mentionedUsers = currentMentionedUsers
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false,
                                commentUser = null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getReplyDetails(replyId: String) {
        viewModelScope.launch {
            productRepository.getReplyById(replyId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        val replyUser = result.data?.reply?.userId
                        val textReplying = result.data?.reply?.replyText ?: "* No text *"
                        val currentMentionedUsers = _state.value.mentionedUsers.toMutableList()

                        replyUser?.let { user ->
                            if (!currentMentionedUsers.contains(user._id)) {
                                currentMentionedUsers.add(user._id)
                            }

                            // Automatically add the username mention to the reply text
                            _state.update {
                                it.copy(
                                    reply = "@${user.username} "
                                )
                            }
                        }

                        _state.update {
                            it.copy(
                                commentUser = replyUser,
                                textReplying = textReplying,
                                isLoading = false,
                                mentionedUsers = currentMentionedUsers
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false,
                                commentUser = null
                            )
                        }
                    }
                }
            }
        }
    }


    private fun postReply() {
        val commentId = this.commentId!!
        val replyText = state.value.reply.trim()

        if (commentId.isEmpty() || replyText.isEmpty()) {
            return
        }

        // Extract usernames from the reply text (everything following @ symbols)
        val mentionPattern = "@([\\w]+)".toRegex()
        val taggedUsernames = mentionPattern.findAll(replyText)
            .map { it.groupValues[1] } // Get the actual username without the @ symbol
            .toList()

        viewModelScope.launch {
            productRepository.addReply(
                commentId = commentId,
                request = AddReplyRequest(
                    replyText = replyText,
                    taggedUsernames = taggedUsernames
                )
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                reply = "", // Clear input
                                successfulReply = result.data?.reply,
                                error = "",
                                onSuccessFullAdd = true,
                                mentionedUsers = emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Failed to post reply"
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Search users with the given query
     */
    private fun searchUsers(query: String) {
        viewModelScope.launch {
            try {
                val results = productRepository.searchUsers(query)

                // If results are empty, delay the update to allow exit animation
                if (results.isEmpty()) {
                    _state.update {
                        it.copy(isMentioning = false)
                    }
                    delay(300) // Adjust according to your animation duration
                }

                // Update state with search results (or empty list after delay)
                _state.update {
                    it.copy(mentionResults = results)
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error searching users: ${e.message}", e)
                // Reset mention state on error
                _state.update {
                    it.copy(
                        isMentioning = false,
                        mentionResults = emptyList(),
                        error = "Failed to search users"
                    )
                }
            }
        }
    }


    private fun getUserBasicInfo() {
        viewModelScope.launch {
            sellerProfileRepository.getUserBasicInfo().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                user = result.data?.user,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false,
                                user = null
                            )
                        }
                    }
                }
            }
        }
    }
}