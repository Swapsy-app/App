package com.example.freeupcopy.ui.presentation.product_screen

import com.example.freeupcopy.data.remote.dto.product.ProductBargain
import com.example.freeupcopy.data.remote.dto.product.UserSearchResult
import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.model.BargainOffer

sealed class ProductUiEvent {
    object LikeProduct : ProductUiEvent()
    data class ChangePincode(val pincode : String) : ProductUiEvent()

    data class ChangeComment(val comment : String) : ProductUiEvent()
    data class PostComment(val taggedUser: List<String>?): ProductUiEvent()
    data class DeleteComment(val commentId: String): ProductUiEvent()
    data class LoadMoreReplies(val commentId: String): ProductUiEvent()
    data class ToggleConfirmDeleteDialog(val commentId: String, val commentSenderId: String): ProductUiEvent()
    data class ToggleConfirmDeleteReply(val replyId: String, val replySenderId: String): ProductUiEvent()
    data class DeleteReply(val replyId: String): ProductUiEvent()

    // New mention events
    data class SelectMention(val user: UserSearchResult): ProductUiEvent()
    data object CancelMention: ProductUiEvent()

    data object PreviewImages: ProductUiEvent()
    data class ChangeImage(val imageId : Int) : ProductUiEvent()
    data class ChangeBargainMessage(val message : String) : ProductUiEvent()
    data class ChangeBargainAmount(val amount : String) : ProductUiEvent()
    data class OnReplyClick(val replyId: String?): ProductUiEvent()

    data object BargainOptionsClicked: ProductUiEvent()
    data class BargainSelectedChange(val index: Int): ProductUiEvent()
    data class BargainCurrencySelectedChange(val currency: Currency): ProductUiEvent()
    data class EditBargainOption(val bargainOffer: ProductBargain) : ProductUiEvent()
    data class DeleteBargain(val bargainId: String) : ProductUiEvent()
    data object BargainUpdateRequest: ProductUiEvent()
    data object BargainRequest: ProductUiEvent()

    data object OnImagePreview : ProductUiEvent()

    data class ChangeReply(val reply : String) : ProductUiEvent()
    data object OnFollow : ProductUiEvent()

    data class IsLoading(val isLoading: Boolean): ProductUiEvent()
    data class SimilarProductClicked(val productId: String, val productImageUrl: String, val title: String) : ProductUiEvent()
}