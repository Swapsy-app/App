package com.example.freeupcopy.ui.presentation.product_screen

import com.example.freeupcopy.data.remote.dto.product.Comment
import com.example.freeupcopy.data.remote.dto.product.DeliveryEstimationResponse
import com.example.freeupcopy.data.remote.dto.product.ProductBargain
import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.product.UserSearchResult
import com.example.freeupcopy.data.remote.dto.sell.Price
import com.example.freeupcopy.data.remote.dto.sell.ProductDetail
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.model.StateAndCity

data class ProductUiState(
    val productId: String = "",
    val user: User? = null,
    val successMessage: String = "",


    val showPaymentModeDialog: Boolean = false,
    val selectedPaymentMode: String = "",


    val productDetail: ProductDetail? = null,
    val originalPriceDetail: Price? = null,

    val isWishlisted: Boolean = false,

    val isConfirmDeleteDialog: Boolean = false,
    val deleteCommentId: String = "",

    val isConfirmDeleteReply: Boolean = false,
    val deleteReplyId: String = "",

    val wishlistCount: Int? = null,

    val pincode: String = "",

    val comment: String = "",
    val comments: List<Comment> = emptyList(),

    // New comment replies state
    val commentReplies: Map<String, List<Reply>> = emptyMap(),

    val isLoadingMoreBargains: Boolean = false,
    val hasMoreBargains: Boolean = true,

    // New mention-related fields
    val isMentioning: Boolean = false,
    val mentionQuery: String = "",
    val mentionResults: List<UserSearchResult> = emptyList(),
    val mentionedUsers: List<String> = emptyList(), // Store usernames that have been mentioned

    val reply: String = "",
    val isImageOpen: Boolean = false,
    val imageIndex: Int = 1,

    val toReplyId: String? = null, // Track which reply is being replied to

    val isRupeeSelected: Boolean = true,
    val optionFocused: Int = 1,
    val tenPercentRecommended: Pair<String, String>? = null,
    val fifteenPercentRecommended: Pair<String, String>? = null,
    val bargainAmount: String = "",
    val bargainMessage: String = "",

    val isSheetOpen: Boolean = false,
    val isEditingBargain: Boolean = false,

    val bargainSelectedIndex: Int = 1,
    val bargainCurrencySelected: Currency = Currency.CASH,

    val productLink: String = "Mock Link",
    val isFollowed: Boolean = false,
    val listedCashPrice: String = "1200",
    val mrp: String = "3500",
    val listedCoinPrice: String = "7800",
    val specialOffer: Pair<String, String> = Pair("4000", "800"),

    // Bargain-related fields
    val bargains: List<ProductBargain> = emptyList(),
    val currentEditingBargainId: String? = null,
    val bargainCount: Int = 0,

    val deliveryEstimation: DeliveryEstimationResponse? = null,
    val pincodeLocation: StateAndCity? = null,

    val isLoading: Boolean = false,
    val error: String = ""
)
