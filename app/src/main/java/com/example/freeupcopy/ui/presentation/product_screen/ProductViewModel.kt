package com.example.freeupcopy.ui.presentation.product_screen

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.utils.ValidationResult
import com.example.freeupcopy.utils.calculateFifteenPercent
import com.example.freeupcopy.utils.calculateTenPercent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductUiState())
    val state = _state.asStateFlow()

    fun onClose(){
        /* todo */
    }

    fun sendReply(id : String){
        /* todo */
    }

    fun sendComment(){
        /* todo */
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

            is ProductUiEvent.OnImagePreview -> {
                _state.update {
                    it.copy(isImageOpen = !it.isImageOpen)
                }
            }

            is ProductUiEvent.BargainOptionsClicked -> {
                val tenPercentCash = calculateTenPercent(_state.value.listedCashPrice)
                val tenPercentCoin = calculateTenPercent(_state.value.listedCoinPrice)

                val fifteenPercentCash = calculateFifteenPercent(_state.value.listedCashPrice)
                val fifteenPercentCoin = calculateFifteenPercent(_state.value.listedCoinPrice)

                _state.update {
                    it.copy(
                        isSheetOpen = !it.isSheetOpen,
                        tenPercentRecommended = Pair(tenPercentCash, tenPercentCoin),
                        fifteenPercentRecommended = Pair(fifteenPercentCash, fifteenPercentCoin)
                    )
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
                _state.update {
                    it.copy(
                        isSheetOpen = false,
                        bargainMessage = "",
                        bargainAmount = event.amount
                    )
                }
            }

            is ProductUiEvent.EditBargainOption -> {
                _state.update {
                    it.copy(
                        isSheetOpen = true,
                        bargainSelectedIndex = 2,
                        bargainMessage = event.bargainOffer.message,
                        bargainAmount = event.bargainOffer.amount,
                        bargainCurrencySelected = event.bargainOffer.currency
                    )
                }
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
        }
    }

    fun validateAll(): ValidationResult {
        if (_state.value.bargainAmount.toInt() < 50) return ValidationResult(false, "Minimum bargain amount is 50")

        return ValidationResult(true) // All validations passed

    }
}