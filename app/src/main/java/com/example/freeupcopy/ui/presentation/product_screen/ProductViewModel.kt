package com.example.freeupcopy.ui.presentation.product_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.product.AddCommentRequest
import com.example.freeupcopy.data.remote.dto.product.BargainOfferRequest
import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.enums.getCurrencyFromString
import com.example.freeupcopy.domain.repository.ProductRepository
import com.example.freeupcopy.domain.repository.SellRepository
import com.example.freeupcopy.domain.repository.ProfileRepository
import com.example.freeupcopy.domain.use_case.GetProductCardsUseCase
import com.example.freeupcopy.domain.use_case.ProductCardsQueryParameters
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.calculateFifteenPercent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val sellRepository: SellRepository,
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val profileRepository: ProfileRepository,
    private val getProductCardsUseCase: GetProductCardsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductUiState())
    val state = _state.asStateFlow()

    // Retrieve the productId from the saved state
    private val productId: String? = savedStateHandle["productId"]

    // In ProductViewModel class
    // Paging state for bargain offers
    private var currentBargainPage = 1

    // Similar products implementation using stateIn
    @OptIn(ExperimentalCoroutinesApi::class)
    val similarProducts = _state
        .map { state ->
            state.productDetail?.let { product ->
                // Extract category for filtering
                val category = product.category

                // Build search query from product name
                val searchQuery = buildSearchQuery(product.title)

                // Build filters map
                val filters = buildMap {
                    put("primaryCategory", category.primaryCategory)
                }

                // Return the search parameters
                ProductCardsQueryParameters(
                    search = searchQuery,
                    filters = filters
                )
            }
        }
        .filterNotNull() // Only proceed when we have valid parameters
        .flatMapLatest { params ->
            getProductCardsUseCase(params)
        }
        .map { pagingData ->
            // Filter out the current product from results
            pagingData.filter { productCard ->
                productCard._id != _state.value.productId
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )


    private var isLoadingMoreBargains = false
    private var hasMoreBargains = true

    // New properties for replies pagination
    private var currentRepliesPages = mutableMapOf<String, Int>()
    private var hasMoreReplies = mutableMapOf<String, Boolean>()
    private var isLoadingReplies = mutableMapOf<String, Boolean>()

    // Paging state
    private var currentPage = 1
    var isLoadingMore by mutableStateOf(false)
        private set

    // NEW: whether we expect more pages (false when last fetch was empty)
    var hasMoreComments by mutableStateOf(true)
        private set

    init {
        productId?.let {
            _state.update { u ->
                u.copy(
                    productId = productId
                )
            }
            getProductDetails(it)
            getWishlistCount(it)
            loadMoreComments()
            getSellerBasicInfo()
            getBargainOffersForProduct()
        }
    }

    fun onEvent(event: ProductUiEvent) {
        when (event) {

            is ProductUiEvent.SelectMention -> {
                // Get current comment text
                val currentText = _state.value.comment

                // Find the position of the last "@" character
                val lastAtPosition = currentText.lastIndexOf("@")

                if (lastAtPosition != -1) {
                    // Replace the incomplete mention with the selected username
                    val textBeforeMention = currentText.substring(0, lastAtPosition)
                    val newText = textBeforeMention + "@${event.user.username} "

                    // Add this user to the mentioned users list
                    val updatedMentionedUsers = _state.value.mentionedUsers.toMutableList()
                    updatedMentionedUsers.add(event.user._id)

                    _state.update {
                        it.copy(
                            comment = newText,
                            isMentioning = false,
                            mentionResults = emptyList(),
                            mentionedUsers = updatedMentionedUsers
                        )
                    }
                }
            }

            is ProductUiEvent.CancelMention -> {
                _state.update {
                    it.copy(
                        isMentioning = false,
                        mentionResults = emptyList()
                    )
                }
            }

            is ProductUiEvent.LikeProduct -> {
                //Later do some network call to update the like status
                _state.update {
                    it.copy(isLiked = !it.isLiked)
                }
            }

            is ProductUiEvent.ChangePincode -> {
                _state.update {
                    it.copy(pincode = event.pincode)
                }
            }

            is ProductUiEvent.ChangeComment -> {
                val newValue = event.comment

                // 1️⃣ Update the raw comment text
                _state.update { it.copy(comment = newValue) }

                // 2️⃣ Store the current state
                val currentState = _state.value

                // 3️⃣ Manage active mentioning (typing @username)
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
                    currentState.isMentioning && !newValue.contains("@") -> {
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
                        if (currentState.isMentioning) {
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

                val mentionedUsernames = Regex("@(\\w+)").findAll(newValue)
                    .map { it.groupValues[1] }
                    .toSet()

                // This map will help us track which user IDs correspond to which usernames
                val usernameToIdMap = mutableMapOf<String, String>()

                // Build the map from our existing mentionResults (users we've searched for)
                currentState.mentionResults.forEach { user ->
                    usernameToIdMap[user.username] = user._id
                }

                // Only remove users whose @username no longer appears in the text
                val updatedMentionedUsers = currentState.mentionedUsers.filter { userId ->
                    // Try to find a mention in the text that corresponds to this user ID
                    val mentionResults = currentState.mentionResults
                    val username = mentionResults.find { it._id == userId }?.username

                    // Keep this ID if we don't know its username (conservatively keep it)
                    // or if its username is still mentioned in the text
                    username == null || mentionedUsernames.contains(username)
                }

                // Only update state if the mentions have actually changed
                if (updatedMentionedUsers != currentState.mentionedUsers) {
                    _state.update {
                        it.copy(mentionedUsers = updatedMentionedUsers)
                    }
                }
            }


            is ProductUiEvent.PostComment -> {

                // 1️⃣ Grab and validate the draft comment
                val draft = _state.value.comment.trim()
                if (draft.isBlank()) return

                // 2️⃣ Extract usernames from the comment text (everything following @ symbols)
                val mentionPattern = "@([\\w]+)".toRegex()
                val taggedUsernames = mentionPattern.findAll(draft)
                    .map { it.groupValues[1] } // Get the actual username without the @ symbol
                    .toList()

                // 3️⃣ Build the request with extracted usernames
                val request = AddCommentRequest(
                    commentText = draft,
                    taggedUsernames = taggedUsernames
                )

                viewModelScope.launch {

                    // Make sure productId is not null before proceeding
                    val currentProductId = productId ?: return@launch

                    productRepository.addComment(currentProductId, request).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.update { it.copy(isLoading = true, error = "") }
                            }

                            is Resource.Success -> {

                                // Add null check for result.data
                                if (result.data == null) {
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            error = "Comment posted but returned null"
                                        )
                                    }
                                    return@collect
                                }

                                // 3️⃣ Take the newly returned Comment
                                val newComment = result.data.comment

                                // 4️⃣ Convert current state.comments into a MutableList
                                val updated = _state.value.comments.toMutableList()

                                // 5️⃣ Prepend the new comment
                                updated.add(0, newComment)

                                // 6️⃣ Commit back into state in one copy() call
                                _state.update { currentState ->
                                    currentState.copy(
                                        comments = updated,
                                        comment = "",        // clear the input
                                        isLoading = false,
                                        error = "",
                                        mentionedUsers = emptyList()
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "An unexpected error occurred",
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }


            is ProductUiEvent.ToggleConfirmDeleteDialog -> {
                val commentId = event.commentId
                val commentSenderId = event.commentSenderId

                // If we're showing the dialog (going from false to true), verify user ownership
                if (!_state.value.isConfirmDeleteDialog) {
                    val currentUserId = _state.value.user!!._id
                    // Only show the dialog if the comment sender is the current user
                    if (commentSenderId == currentUserId) {
                        _state.update { currentState ->
                            currentState.copy(
                                isConfirmDeleteDialog = true,
                                deleteCommentId = commentId
                            )
                        }
                    }
                    // If sender is not current user, do nothing
                } else {
                    // When hiding the dialog (going from true to false), always allow it
                    // This handles the cancel button case
                    _state.update { currentState ->
                        currentState.copy(
                            isConfirmDeleteDialog = false,
                            deleteCommentId = ""
                        )
                    }
                }
            }

            is ProductUiEvent.ToggleConfirmDeleteReply -> {
                val replyId = event.replyId
                val replySenderId = event.replySenderId

                // If we're showing the dialog (going from false to true), verify user ownership
                if (!_state.value.isConfirmDeleteReply) {
                    val currentUserId = _state.value.user!!._id
                    // Only show the dialog if the comment sender is the current user
                    if (replySenderId == currentUserId) {
                        _state.update { currentState ->
                            currentState.copy(
                                isConfirmDeleteReply = true,
                                deleteReplyId = replyId
                            )
                        }
                    }
                    // If sender is not current user, do nothing
                } else {
                    // When hiding the dialog (going from true to false), always allow it
                    // This handles the cancel button case
                    _state.update { currentState ->
                        currentState.copy(
                            isConfirmDeleteReply = false,
                            deleteReplyId = ""
                        )
                    }
                }
            }

            is ProductUiEvent.DeleteComment -> {
                _state.update {
                    it.copy(
                        isConfirmDeleteDialog = false,
                        deleteCommentId = ""
                    )
                }
                deleteComment(event.commentId)
            }

            is ProductUiEvent.DeleteReply -> {
                _state.update {
                    it.copy(
                        isConfirmDeleteReply = false,
                        deleteReplyId = ""
                    )
                }
                deleteReply(event.replyId)
            }

            is ProductUiEvent.LoadMoreReplies -> {
                loadMoreReplies(event.commentId)
            }


            is ProductUiEvent.PreviewImages -> {
                _state.update {
                    it.copy(isImageOpen = !it.isImageOpen)
                }
            }

            is ProductUiEvent.ChangeImage -> {
                _state.update {
                    it.copy(imageIndex = event.imageId)
                }
            }

            is ProductUiEvent.OnImagePreview -> {
                _state.update {
                    it.copy(isImageOpen = !it.isImageOpen)
                }
            }

            is ProductUiEvent.BargainOptionsClicked -> {
                val currentSheetState = _state.value.isSheetOpen


                val fifteenPercentCoin = calculateFifteenPercent(_state.value.listedCoinPrice)

                _state.update {
                    if (currentSheetState) {
                        // Sheet is closing, reset all bargain-related values
                        it.copy(
                            isSheetOpen = false,
                            bargainMessage = "",
                            bargainAmount = "",
                            bargainSelectedIndex = 1,
                            isEditingBargain = false,
                            bargainCurrencySelected = Currency.CASH, // Default currency
                            currentEditingBargainId = null,
                        )
                    } else {
                        // Sheet is opening
                        it.copy(
                            isSheetOpen = true,

                            )
                    }
                }
            }


            is ProductUiEvent.ChangeBargainMessage -> {
                _state.update {
                    it.copy(bargainMessage = event.message)
                }
            }

            is ProductUiEvent.ChangeBargainAmount -> {
                _state.update {
                    it.copy(bargainAmount = event.amount)
                }
            }

            is ProductUiEvent.BargainRequest -> {

                // Call the new makeOffer method
                makeOffer()
            }

            is ProductUiEvent.EditBargainOption -> {
                _state.update {
                    it.copy(
                        isSheetOpen = true,
                        isEditingBargain = true,
                        bargainSelectedIndex = 2,
                        bargainMessage = event.bargainOffer.message!!,
                        bargainAmount = event.bargainOffer.offeredPrice!!.toInt().toString(),
                        bargainCurrencySelected = getCurrencyFromString(event.bargainOffer.offeredIn),
                        currentEditingBargainId = event.bargainOffer._id,
                    )
                }
            }

            is ProductUiEvent.DeleteBargain -> {
                deleteBargainOffer(event.bargainId)
            }

            is ProductUiEvent.BargainUpdateRequest -> {
                updateOffer()
            }

            is ProductUiEvent.BargainSelectedChange -> {
                _state.update {
                    it.copy(
                        bargainSelectedIndex = event.index,
                        bargainAmount = ""
                    )
                }
            }

            is ProductUiEvent.BargainCurrencySelectedChange -> {
                _state.update {
                    it.copy(
                        bargainCurrencySelected = event.currency,
                        bargainAmount = ""
                    )
                }
            }

            is ProductUiEvent.OnReplyClick -> {
                _state.update {
                    it.copy(
                        toReplyId = event.replyId,
                        reply = ""
                    )
                }
            }

            is ProductUiEvent.ChangeReply -> {
                _state.update {
                    it.copy(reply = event.reply)
                }
            }

            is ProductUiEvent.OnFollow -> {
                _state.update {
                    it.copy(isFollowed = !it.isFollowed)
                }
            }

            is ProductUiEvent.IsLoading -> {
                _state.update {
                    it.copy(isLoading = event.isLoading)
                }
            }
        }
    }


    private fun deleteComment(commentId: String) {
        viewModelScope.launch {
            productRepository.deleteComment(commentId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                comments = _state.value.comments.filter { comment ->
                                    comment._id != commentId
                                }
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun makeOffer() {
        viewModelScope.launch {
            // Get current state values
            val currentState = _state.value
            val productId = currentState.productId ?: return@launch
            val amount = currentState.bargainAmount.toFloatOrNull() ?: return@launch
            val message = currentState.bargainMessage
            val currency = currentState.bargainCurrencySelected

            // Create the request object
            val request = BargainOfferRequest(
                offeredPrice = amount,
                offeredIn = currency.valueName,
                message = message,
                sellerReceives = if (currency == Currency.CASH) {
                    currentState.listedCashPrice
                } else {
                    currentState.listedCoinPrice
                }
            )

            // Call the repository method
            productRepository.makeOffer(productId, request).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }

                    is Resource.Success -> {
                        // Handle successful response
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isSheetOpen = false,
                                bargainMessage = "",
                                bargainAmount = "",
                                error = ""
                            )
                        }

                        // Refresh the bargain offers list
                        getBargainOffersForProduct()
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isSheetOpen = false,
                                error = result.message ?: "Failed to make offer"
                            )
                        }
                    }
                }
            }
        }
    }


    // Add this method to handle updating an existing bargain offer
    private fun updateOffer() {
        viewModelScope.launch {
            val currentState = _state.value
            val productId = currentState.productId
            val amount = currentState.bargainAmount.toFloatOrNull() ?: return@launch
            val message = currentState.bargainMessage
            val currency = currentState.bargainCurrencySelected

            val request = BargainOfferRequest(
                offeredPrice = amount,
                offeredIn = currency.name.lowercase(),
                message = message,
                sellerReceives = if (currency == Currency.CASH) {
                    currentState.listedCashPrice
                } else {
                    currentState.listedCoinPrice
                }
            )

            productRepository.updateOffer(productId, request).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isSheetOpen = false,
                                bargainMessage = "",
                                bargainAmount = "",
                                currentEditingBargainId = null,
                                isEditingBargain = false,
                                error = ""
                            )
                        }
                        getBargainOffersForProduct()
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isEditingBargain = false,
                                error = result.message ?: "Failed to update offer"
                            )
                        }
                    }
                }
            }
        }
    }

    // Add this method to handle deleting a bargain offer
    private fun deleteBargainOffer(bargainId: String) {
        viewModelScope.launch {
            productRepository.deleteOffer(bargainId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isSheetOpen = false,
                                currentEditingBargainId = null,
                                error = "",
                                isEditingBargain = false,
                                bargainMessage = "",
                                bargainAmount = ""

                            )
                        }
                        getBargainOffersForProduct()
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Failed to delete offer"
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadMoreBargains() {
        if (!isLoadingMoreBargains && hasMoreBargains) {
            getBargainOffersForProduct(loadMore = true)
        }
    }


    private fun getBargainOffersForProduct(loadMore: Boolean = false) {
        if (isLoadingMoreBargains || (!loadMore && !hasMoreBargains)) return

        isLoadingMoreBargains = true
        viewModelScope.launch {
            try {
                val pageToLoad = if (loadMore) currentBargainPage else 1
                val response = productRepository.getOffersForProduct(
                    productId = productId!!,
                    page = pageToLoad
                )
                val newBargains = response.bargains ?: emptyList()
                val updatedBargains = if (loadMore) {
                    _state.value.bargains + newBargains
                } else {
                    newBargains
                }
                // <-- PLACE THE UPDATE HERE
                _state.update {
                    it.copy(
                        bargains = updatedBargains,
                        isLoadingMoreBargains = false,
                        hasMoreBargains = response.hasNextPage
                    )
                }
                currentBargainPage++
                hasMoreBargains = response.hasNextPage
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoadingMoreBargains = false,
                        error = e.message ?: "Failed to fetch bargain offers"
                    )
                }
            } finally {
                isLoadingMoreBargains = false
            }
        }
    }


    private fun deleteReply(replyId: String) {
        // First, find the reply and its parent comment ID before deletion
        val replyToDelete = _state.value.commentReplies.values.flatten().find { it._id == replyId }
        val parentCommentId = replyToDelete?.commentId

        viewModelScope.launch {
            productRepository.deleteReply(replyId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update { currentState ->
                            // 1️⃣ Filter out the deleted reply
                            val updatedReplies =
                                currentState.commentReplies.mapValues { (_, replies) ->
                                    replies.filter { it._id != replyId }
                                }

                            // 2️⃣ Decrement replyCount on the parent Comment if we found the parentCommentId
                            val updatedComments = if (parentCommentId != null) {
                                currentState.comments.map { comment ->
                                    if (comment._id == parentCommentId) {
                                        // Make sure we don't go below zero
                                        val newCount = (comment.replyCount - 1).coerceAtLeast(0)
                                        comment.copy(replyCount = newCount)
                                    } else {
                                        comment
                                    }
                                }
                            } else {
                                currentState.comments
                            }

                            // 3️⃣ Emit new state with both changes
                            currentState.copy(
                                isLoading = false,
                                commentReplies = updatedReplies,
                                comments = updatedComments
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "An unexpected error occurred"
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


    /** Load next page of comments, append into state.comments, update flags */
    fun loadMoreComments() {
        if (isLoadingMore || !hasMoreComments) return

        isLoadingMore = true
        viewModelScope.launch {
            try {
                val response = productRepository.getCommentsForProduct(
                    productId = productId!!,
                    page = currentPage,
                    limit = 5
                )
                val newComments = response.data

                if (newComments.isNotEmpty()) {
                    // 1️⃣ Take current state.comments and append newComments immutably
                    val updated = _state.value.comments
                        .toMutableList()
                        .apply { addAll(newComments) }

                    // 2️⃣ Emit the updated comments list into state
                    _state.update {
                        it.copy(
                            comments = updated
                        )
                    }
                    currentPage++
                } else {
                    // No new items → end of list
                    hasMoreComments = false
                }
            } catch (e: Exception) {
                // TODO: you can also push an error into state.error here
            } finally {
                isLoadingMore = false
            }
        }
    }

    // Updated replies-related methods in the ViewModel
    fun loadMoreReplies(commentId: String) {
        // Check if we're already loading replies or if there are no more replies
        if (isLoadingReplies.getOrDefault(commentId, false)) return

        // Get current page for this comment (default to 1 if not set)
        val currentPage = currentRepliesPages.getOrDefault(commentId, 1)

        // Mark as loading
        isLoadingReplies[commentId] = true

        viewModelScope.launch {
            try {
                val response = productRepository.getRepliesForComment(
                    commentId = commentId,
                    page = currentPage,
                    limit = 5
                )
                val newReplies = response.data

                // Update replies state
                _state.update { currentState ->
                    val existingReplies = currentState.commentReplies[commentId] ?: emptyList()
                    val updatedReplies = existingReplies + newReplies
                    currentState.copy(
                        commentReplies = currentState.commentReplies +
                                (commentId to updatedReplies.distinctBy { it._id })
                    )
                }

                // Update pagination states
                if (newReplies.isNotEmpty()) {
                    currentRepliesPages[commentId] = currentPage + 1
                    hasMoreReplies[commentId] = true
                } else {
                    hasMoreReplies[commentId] = false
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("ProductViewModel", "Error loading replies: ${e.message}", e)
                hasMoreReplies[commentId] = false
            } finally {
                // Mark loading as complete
                isLoadingReplies[commentId] = false
            }
        }
    }

    // Check if more replies can be loaded for a comment
    fun canLoadMoreReplies(commentId: String, replyCount: Int): Boolean {
        val loadedRepliesCount = _state.value.commentReplies[commentId]?.size ?: 0
        return loadedRepliesCount < replyCount &&
                !isLoadingReplies.getOrDefault(commentId, false)
    }

    // Reset replies for a specific comment (useful when navigating away/back)
    fun resetRepliesForComment(commentId: String) {
        currentRepliesPages.remove(commentId)
        hasMoreReplies.remove(commentId)
        isLoadingReplies.remove(commentId)

        _state.update { currentState ->
            currentState.copy(
                commentReplies = currentState.commentReplies.minus(commentId)
            )
        }
    }


    /**
     * Inserts the newly posted [newReply] at the front of the list of replies
     * for its parent comment, so it shows up immediately in the UI.
     */
    fun prependReplyToComment(newReply: Reply) {
        _state.update { current ->
            // 1️⃣ Update the replies map
            val existing = current.commentReplies[newReply.commentId].orEmpty()
            val updatedReplies = listOf(newReply) + existing

            // 2️⃣ Increment replyCount on the parent Comment
            val updatedComments = current.comments.map { comment ->
                if (comment._id == newReply.commentId) {
                    comment.copy(replyCount = comment.replyCount + 1)
                } else {
                    comment
                }
            }

            // 3️⃣ Emit new state with both changes
            current.copy(
                commentReplies = current.commentReplies + (newReply.commentId to updatedReplies),
                comments = updatedComments
            )
        }
    }


    private fun getWishlistCount(productId: String) {
        viewModelScope.launch {
            sellRepository.getWishlistCount(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                wishlistCount = result.data?.wishlistCount ?: 0,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getProductDetails(productId: String) {
        viewModelScope.launch {
            sellRepository.getProductDetails(productId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
//                        var tenPercentRecommended: Pair<String?, String?> = Pair(null, null)
//                        var fifteenPercentRecommended: Pair<String?, String?> = Pair(null, null)
//                        _state.value.productDetail?.price?.cash?.let {
//                            val tenPercentCash = calculateTenPercent(_state.value.productDetail?.price?.cash?.enteredAmount.toString())
//                            val fifteenPercentCash = calculateFifteenPercent(_state.value.productDetail?.price?.cash?.enteredAmount.toString())
//                            tenPercentRecommended = tenPercentRecommended.copy(tenPercentCash)
//                            fifteenPercentRecommended = fifteenPercentRecommended.copy(fifteenPercentCash)
//                        }
//                        _state.value.productDetail?.price?.coin?.let {
//                            val tenPercentCoin = calculateTenPercent(_state.value.productDetail?.price?.coin?.enteredAmount.toString())
//                            val fifteenPercentCoin = calculateFifteenPercent(_state.value.productDetail?.price?.coin?.enteredAmount.toString())
//
//                            tenPercentRecommended
//                        }


                        _state.update {
                            it.copy(
                                productDetailsResponse = result.data,
                                productDetail = result.data?.product,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getSellerBasicInfo() {
        viewModelScope.launch {
            profileRepository.getUserBasicInfo().collect { result ->
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

    // Smart title processing for search
    private fun buildSearchQuery(productName: String): String {
        // Extract key terms from product name
        val nameKeywords = productName
            .split(" ")
            .filter { it.length > 3 && it.lowercase() !in COMMON_WORDS }
            .take(3)

        // Format the query
        return nameKeywords
            .distinct()
            .joinToString(" ")
            .trim()
    }

    // List of common words to exclude from search
    private val COMMON_WORDS = setOf(
        "with", "for", "and", "the", "this", "that", "from", "have", "has"
    )


    fun validateAll(): ValidationResult {
        if (_state.value.bargainMessage.isBlank()) return ValidationResult(
            false,
            "Bargain message cannot be empty"
        )
        if (_state.value.bargainAmount.isBlank()) return ValidationResult(
            false,
            "Bargain amount cannot be empty"
        )
        if (_state.value.bargainAmount.toInt() < 50) return ValidationResult(
            false,
            "Minimum bargain amount is 50"
        )

        return ValidationResult(true) // All validations passed

    }
}