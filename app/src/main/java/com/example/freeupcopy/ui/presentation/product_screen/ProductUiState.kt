package com.example.freeupcopy.ui.presentation.product_screen

import com.example.freeupcopy.data.remote.dto.product.Bargain
import com.example.freeupcopy.data.remote.dto.product.Comment
import com.example.freeupcopy.data.remote.dto.product.ProductBargain
import com.example.freeupcopy.data.remote.dto.product.Reply
import com.example.freeupcopy.data.remote.dto.product.User
import com.example.freeupcopy.data.remote.dto.product.UserSearchResult
import com.example.freeupcopy.data.remote.dto.sell.ProductDetail
import com.example.freeupcopy.data.remote.dto.sell.ProductDetailsResponse
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.model.BargainOffer

data class ProductUiState(
    val productId: String = "",
    val user: User? = null,

    val productDetailsResponse: ProductDetailsResponse? = null,
    val productDetail: ProductDetail? = null,

    val isLiked: Boolean = false,

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

//    val bargainOfferLists: List<BargainOffer> = listOf(
//        BargainOffer(
//            id = "BO12345",
//            username = "Johoe",
//            userId = "U001",
//            message = "Looking for a 20% discount on bulk orders.",
//            timeStamp = "2 hrs ago",
//            amount = "2000",
//            currency = Currency.CASH
//        ),
//        BargainOffer(
//            id = "BO12346",
//            username = "JaSmith",
//            userId = "U002",
//            message = "Can I get free shipping for orders above $50?",
//            timeStamp = "1 day ago",
//            amount = "5000",
//            currency = Currency.COIN
//        ),
//        BargainOffer(
//            id = "BO12347",
//            username = "Alehnson",
//            userId = "U003",
//            message = "Would you accept a counteroffer of $30 for this item?",
//            timeStamp = "2 days ago",
//            amount = "3000",
//            currency = Currency.COIN
//        ),
//        BargainOffer(
//            id = "BO12348",
//            username = "Emilvis",
//            userId = "U004",
//            message = "If I buy two items, can I get a third one free?",
//            timeStamp = "2 days ago",
//            amount = "4000",
//            currency = Currency.CASH
//        ),
//        BargainOffer(
//            id = "BO12349",
//            username = "Michaerown",
//            userId = "U005",
//            message = "Is there any holiday discount available?",
//            timeStamp = "1 week ago",
//            amount = "1000",
//            currency = Currency.CASH
//        )
//    ),

    val isLoading: Boolean = false,
    val error: String = ""
)
