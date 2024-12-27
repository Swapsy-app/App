package com.example.freeupcopy.ui.presentation.product_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductUiState())
    val state = _state.asStateFlow()

    fun onClose(){
        /* todo */
    }


    fun changeImage(int: Int){
        _state.value = _state.value.copy(
            imageIndex = int
        )
    }

    fun sendReply(id : String){
        /* todo */
    }

    fun sendComment(){
        /* todo */
    }

    fun onRupee(){
        _state.value = _state.value.copy(
            isRupeeSelected = true
        )
    }
    fun onCoin(){
        _state.value = _state.value.copy(
            isRupeeSelected = false
        )
    }
    fun onFocus0(){
        _state.value = _state.value.copy(
            optionFocused = 0
        )
    }
    fun onFocus1(){
        _state.value = _state.value.copy(
            optionFocused = 1
        )
    }
    fun onFocus2(){
        _state.value = _state.value.copy(
            optionFocused = 2
        )
    }
    fun onChangeBargainText(str : String){
        _state.value = _state.value.copy(
            bargainTextField = str
        )
    }
    fun onChangeMessageToSeller(str : String){
        _state.value = _state.value.copy(
            messageToSeller = str
        )
    }
    fun onOpenPopup(){
        _state.value = _state.value.copy(
            isPopupOpen = true
        )
    }
    fun onClosePopup(){
        _state.value = _state.value.copy(
            isPopupOpen = false
        )
    }

    fun onEvent(event: ProductUiEvent) {
        when(event){
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
                _state.update {
                    it.copy(comment = event.comment)
                }
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
//            is ProductUiEvent.ReplyToReplyToggle -> {
//                _state.update { currentState ->
//                    val isSameReply = currentState.replyToReplyId == event.reply.id
//                    currentState.copy(
//                        commentReplyingId = "", // Clear comment replying state
//                        replyToReplyId = if (isSameReply) "" else event.reply.id, // Toggle reply-to-reply state
//                        replyingToUser = if (isSameReply) "" else event.reply.user, // Update user mention
//                        reply = if (isSameReply) "" else "@${event.reply.user} " // Add or clear username mention
//                    )
//                }
//            }

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
        }
    }
}