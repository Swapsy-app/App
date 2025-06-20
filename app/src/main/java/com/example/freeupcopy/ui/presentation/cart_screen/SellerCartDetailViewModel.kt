package com.example.freeupcopy.ui.presentation.cart_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.cart.Seller
import com.example.freeupcopy.domain.repository.CartRepository
import com.example.freeupcopy.domain.use_case.GetUserProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerCartDetailViewModel @Inject constructor(
    private val getUserProductsUseCase: GetUserProductsUseCase,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SellerCartDetailState())
    val state: StateFlow<SellerCartDetailState> = _state.asStateFlow()

    private val sellerId: String = savedStateHandle.get<String>("sellerId") ?: ""

    // Get all seller's available products
    private val allProducts = getUserProductsUseCase(sellerId, "available")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    // Filter available products to exclude items already in cart
    val availableProducts = combine(allProducts, _state) { products, state ->
        products.filter { product ->
            !state.cartItems.containsKey(product._id)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    // Get products that are in cart with their details
    val cartProducts = combine(allProducts, _state) { products, state ->
        products.filter { product ->
            state.cartItems.containsKey(product._id)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    init {
        // Load current cart items when ViewModel is initialized
        loadCurrentCartItems()
    }

    fun onEvent(event: SellerCartDetailEvent) {
        when (event) {
            is SellerCartDetailEvent.AddProduct -> addProductToCart(event.productId, event.selectedMode)
            is SellerCartDetailEvent.RemoveProduct -> removeProductFromCart(event.productId)
            is SellerCartDetailEvent.UpdatePaymentMode -> updatePaymentMode(event.productId, event.newMode)
            is SellerCartDetailEvent.LoadCartItems -> loadCurrentCartItems()

            is SellerCartDetailEvent.IsLoading -> {
                _state.update { it.copy(isLoading = event.isLoading) }
            }

            is SellerCartDetailEvent.ClearError -> {
                _state.update {
                    it.copy(error = "")
                }
            }
            is SellerCartDetailEvent.ClearMessage -> {
                _state.update {
                    it.copy(successMessage = "")
                }
            }
        }
    }

    private fun addProductToCart(productId: String, selectedMode: String) {
        viewModelScope.launch {
            // Make API call to add to cart
            cartRepository.addToCart(productId, selectedMode).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        // Update local state
                        _state.update { currentState ->
                            val updatedItems = currentState.cartItems.toMutableMap()
                            updatedItems[productId] = selectedMode
                            currentState.copy(
                                cartItems = updatedItems,
                                isLoading = false,
                                successMessage = "Product added to cart"
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error adding product to cart"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun removeProductFromCart(productId: String) {
        viewModelScope.launch {
            // Make API call to remove from cart
            cartRepository.removeFromCart(productId = productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        // Update local state
                        _state.update { currentState ->
                            val updatedItems = currentState.cartItems.toMutableMap()
                            updatedItems.remove(productId)
                            currentState.copy(
                                cartItems = updatedItems,
                                isLoading = false,
                                successMessage = "Product removed from cart"
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error removing product from cart"
                            )
                        }
                    }
                }
            }
        }
    }

    // Updated updatePaymentMode to handle single product
    private fun updatePaymentMode(productId: String, newMode: String) {
        viewModelScope.launch {

            // Call API to update single product payment mode
            cartRepository.updateProductPaymentMode(
                sellerId = sellerId,
                productId = productId,
                selectedPriceMode = newMode
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        // Update local state
                        _state.update { currentState ->
                            val updatedItems = currentState.cartItems.toMutableMap()
                            updatedItems[productId] = newMode
                            currentState.copy(
                                cartItems = updatedItems,
                                isLoading = false,
                                successMessage = resource.data?.message ?: "Payment mode updated successfully"
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error updating payment mode"
                            )
                        }
                    }
                }
            }
        }
    }


    private fun loadCurrentCartItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }

            cartRepository.getCart().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        // Find the cart items for this specific seller
                        val sellerCart = resource.data?.cart?.find { it.seller._id == sellerId }

                        val currentCartItems = sellerCart?.products?.associate { product ->
                            product._id to product.selectedPaymentMode
                        } ?: emptyMap()

                        _state.update {
                            it.copy(
                                isLoading = false,
                                cartItems = currentCartItems,
                                seller = sellerCart?.seller,
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error loading cart items"
                            )
                        }
                    }
                }
            }
        }
    }
}

// State and Events remain the same
data class SellerCartDetailState(
    val isLoading: Boolean = false,
    val cartItems: Map<String, String> = emptyMap(), // productId -> selectedPriceMode
    val error: String = "",
    val successMessage: String = "",
    val seller: Seller? = null
)

sealed class SellerCartDetailEvent {
    data class AddProduct(val productId: String, val selectedMode: String) : SellerCartDetailEvent()
    data class RemoveProduct(val productId: String) : SellerCartDetailEvent()
    data class UpdatePaymentMode(val productId: String, val newMode: String) : SellerCartDetailEvent()
    data object LoadCartItems : SellerCartDetailEvent()

    object ClearError : SellerCartDetailEvent()
    object ClearMessage : SellerCartDetailEvent()
    data class IsLoading(val isLoading: Boolean) : SellerCartDetailEvent()
}
