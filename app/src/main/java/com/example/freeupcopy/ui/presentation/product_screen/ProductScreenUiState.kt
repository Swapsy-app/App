package com.example.freeupcopy.ui.presentation.product_screen

import com.example.freeupcopy.domain.model.BargainOffers
import com.example.freeupcopy.domain.model.Comment
import com.example.freeupcopy.domain.model.Reply

data class ProductScreenUiState(
    val isLiked : Boolean = false,
    val pincode : String = "",
    val comment : String = "",
    val reply : String = "",
    val isImageOpen : Boolean = false,
    val imageIndex : Int = 1,
    val commentReplying : String = "",

    val isRupeeSelected : Boolean = true,
    val optionFocused : Int = 1,
    val recommendation : List<List<String>> = listOf(listOf("800","1000"),listOf("5000","7000")),
    val bargainTextField : String = "",
    val messageToSeller : String = "",
    val isPopupOpen : Boolean = false,

    val productLink : String = "Mock Link",
    val likeCounter: String = "34",
    val shareCounter: String = "35",
    val isFollowed : Boolean = false,
    val priceOffered: String = "1200",
    val priceOriginal: String = "3500",
    val coinsOffered: String = "7800",
    val specialOffer: Array<String> = arrayOf("4000","800"),
    val comments: List<Comment> = listOf(
        Comment(
            id = "c1",
            user = "Alice",
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
            user = "David",
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
            user = "Frank",
            userId = "u6",
            text = "Interesting perspective, but I think there's another way to look at this.",
            replies = emptyList(),
            timeStamp = "25 Dec"
        )
    ),
    val bargainOffersList : List<BargainOffers> = listOf(
        BargainOffers(
            id = "BO12345",
            user = "John Doe",
            userId = "U001",
            text = "Looking for a 20% discount on bulk orders.",
            timeStamp = "24 Dec"
        ),
        BargainOffers(
            id = "BO12346",
            user = "Jane Smith",
            userId = "U002",
            text = "Can I get free shipping for orders above $50?",
            timeStamp = "23 Dec"
        ),
        BargainOffers(
            id = "BO12347",
            user = "Alex Johnson",
            userId = "U003",
            text = "Would you accept a counteroffer of $30 for this item?",
            timeStamp = "22 Dec"
        ),
        BargainOffers(
            id = "BO12348",
            user = "Emily Davis",
            userId = "U004",
            text = "If I buy two items, can I get a third one free?",
            timeStamp = "21 Dec"
        ),
        BargainOffers(
            id = "BO12349",
            user = "Michael Brown",
            userId = "U005",
            text = "Is there any holiday discount available?",
            timeStamp = "20 Dec"
        )
    )

)
