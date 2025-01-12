package com.example.freeupcopy.ui.presentation.product_screen

import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.model.BargainOffer
import com.example.freeupcopy.domain.model.Comment
import com.example.freeupcopy.domain.model.Reply

data class ProductUiState(
    val isLiked : Boolean = false,
    val pincode : String = "",
    val comment : String = "",
    val reply : String = "",
    val isImageOpen : Boolean = false,
    val imageIndex : Int = 1,

    val toReplyId: String? = null, // Track which reply is being replied to

    val isRupeeSelected : Boolean = true,
    val optionFocused : Int = 1,
    val tenPercentRecommended : Pair<String, String>? = null,
    val fifteenPercentRecommended : Pair<String, String>? = null,
    val bargainAmount : String = "",
    val bargainMessage : String = "",
    val isSheetOpen : Boolean = false,
    val bargainSelectedIndex: Int = 1,
    val bargainCurrencySelected: Currency = Currency.CASH,

    val productLink : String = "Mock Link",
    val likeCounter: String = "34",
    val shareCounter: String = "35",
    val isFollowed : Boolean = false,
    val listedCashPrice: String = "1200",
    val mrp: String = "3500",
    val listedCoinPrice: String = "7800",
    val specialOffer: Pair<String, String> = Pair("4000","800"),
    val comments: List<Comment> = listOf(
        Comment(
            id = "c1",
            username = "Alice",
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
            username = "David",
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
            username = "Frank",
            userId = "u6",
            text = "Interesting perspective, but I think there's another way to look at this.",
            replies = emptyList(),
            timeStamp = "25 Dec"
        )
    ),
    val bargainOfferLists : List<BargainOffer> = listOf(
        BargainOffer(
            id = "BO12345",
            username = "Johoe",
            userId = "U001",
            message = "Looking for a 20% discount on bulk orders.",
            timeStamp = "2 hrs ago",
            amount = "2000",
            currency = Currency.CASH
        ),
        BargainOffer(
            id = "BO12346",
            username = "JaSmith",
            userId = "U002",
            message = "Can I get free shipping for orders above $50?",
            timeStamp = "1 day ago",
            amount = "5000",
            currency = Currency.COIN
        ),
        BargainOffer(
            id = "BO12347",
            username = "Alehnson",
            userId = "U003",
            message = "Would you accept a counteroffer of $30 for this item?",
            timeStamp = "2 days ago",
            amount = "3000",
            currency = Currency.COIN
        ),
        BargainOffer(
            id = "BO12348",
            username = "Emilvis",
            userId = "U004",
            message = "If I buy two items, can I get a third one free?",
            timeStamp = "2 days ago",
            amount = "4000",
            currency = Currency.CASH
        ),
        BargainOffer(
            id = "BO12349",
            username = "Michaerown",
            userId = "U005",
            message = "Is there any holiday discount available?",
            timeStamp = "1 week ago",
            amount = "1000",
            currency = Currency.CASH
        )
    )

)
