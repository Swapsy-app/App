package com.example.freeupcopy.ui.presentation.product_screen

sealed class ProductUiEvent {
    object LikeProduct : ProductUiEvent()
    data class ChangePincode(val pincode : String) : ProductUiEvent()
    data class ChangeComment(val comment : String) : ProductUiEvent()

    data object PreviewImages: ProductUiEvent()
    data class ChangeImage(val imageId : Int) : ProductUiEvent()
    //data class ReplyToReplyToggle(val commentId: String, val reply : Reply) : ProductUiEvent()
    data class OnReplyClick(val replyId: String?): ProductUiEvent()

    data class ChangeReply(val reply : String) : ProductUiEvent()
    data object OnFollow : ProductUiEvent()

}