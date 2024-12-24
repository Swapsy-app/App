package com.example.freeupcopy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.ui.presentation.product_screen.ProductScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductScreenViewModel : ViewModel() {
    private val _productScreenUiState = MutableStateFlow(ProductScreenUiState())
    val productScreenUiState : StateFlow<ProductScreenUiState> = _productScreenUiState

    fun likeProduct(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isLiked = !_productScreenUiState.value.isLiked
        )
    }

    fun onClose(){
        /* todo */
    }

    fun changePincode(newPincode : String){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            pincode = newPincode
        )
    }

    fun changeComment(newComment : String){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            comment = newComment
        )
    }

    fun openImage(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isImageOpen = true
        )
    }

    fun closeImage(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isImageOpen = false
        )
    }

    fun changeImage(int: Int){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            imageIndex = int
        )
    }

    fun clickOnReply(id : String){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            commentReplying = id,
            reply = ""
        )
    }

    fun changeReply(newComment : String){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            reply = newComment
        )
    }

    fun onFollow(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isFollowed = !_productScreenUiState.value.isFollowed
        )
    }

    fun sendReply(id : String){
        /* todo */
    }

    fun sendComment(){
        /* todo */
    }

    fun onRupee(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isRupeeSelected = true
        )
    }
    fun onCoin(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isRupeeSelected = false
        )
    }
    fun onFocus0(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            optionFocused = 0
        )
    }
    fun onFocus1(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            optionFocused = 1
        )
    }
    fun onFocus2(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            optionFocused = 2
        )
    }
    fun onChangeBargainText(str : String){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            bargainTextField = str
        )
    }
    fun onChangeMessageToSeller(str : String){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            messageToSeller = str
        )
    }
    fun onOpenPopup(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isPopupOpen = true
        )
    }
    fun onClosePopup(){
        _productScreenUiState.value = _productScreenUiState.value.copy(
            isPopupOpen = false
        )
    }
}