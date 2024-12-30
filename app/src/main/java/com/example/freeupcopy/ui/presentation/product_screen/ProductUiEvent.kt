package com.example.freeupcopy.ui.presentation.product_screen

import com.example.freeupcopy.domain.enums.Currency
import com.example.freeupcopy.domain.model.BargainOffer

sealed class ProductUiEvent {
    object LikeProduct : ProductUiEvent()
    data class ChangePincode(val pincode : String) : ProductUiEvent()
    data class ChangeComment(val comment : String) : ProductUiEvent()

    data object PreviewImages: ProductUiEvent()
    data class ChangeImage(val imageId : Int) : ProductUiEvent()
    data class ChangeBargainMessage(val message : String) : ProductUiEvent()
    data class ChangeBargainAmount(val amount : String) : ProductUiEvent()
    data class OnReplyClick(val replyId: String?): ProductUiEvent()
    data object BargainOptionsClicked: ProductUiEvent()
    data class BargainSelectedChange(val index: Int): ProductUiEvent()
    data class BargainCurrencySelectedChange(val currency: Currency): ProductUiEvent()

    data class EditBargainOption(val bargainOffer: BargainOffer) : ProductUiEvent()

    data class BargainRequest(val message: String, val amount: String): ProductUiEvent()
    data object OnImagePreview : ProductUiEvent()

    data class ChangeReply(val reply : String) : ProductUiEvent()
    data object OnFollow : ProductUiEvent()

}