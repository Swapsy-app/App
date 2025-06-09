package com.example.freeupcopy.ui.presentation.cart_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.domain.repository.CartRepository
import com.example.freeupcopy.ui.presentation.cart_screen.CartState
import com.example.freeupcopy.ui.presentation.cart_screen.CartUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    init {
        getCart()
        getCartSummary()
    }

    fun onEvent(event: CartUiEvent) {
        when (event) {
            is CartUiEvent.AddToCart -> addToCart(event.productId)
            is CartUiEvent.RemoveProduct -> removeFromCart(productId = event.productId)
            is CartUiEvent.RemoveSeller -> removeFromCart(sellerId = event.sellerId)
            is CartUiEvent.RefreshCart -> {
                getCart()
                getCartSummary()
            }
        }
    }

    private fun getCart() {
        viewModelScope.launch {
            cartRepository.getCart().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                cartItems = resource.data?.cart ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error loading cart"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCartSummary() {
        viewModelScope.launch {

            cartRepository.getCartSummary().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                totalProducts = resource.data?.totalProducts ?: 0,
                                totalCombos = resource.data?.totalCombos ?: 0
                            )
                        }
                    }
                    is Resource.Error -> {
                        // Handle error silently for summary
                    }
                    is Resource.Loading -> {
                        // Handle loading if needed
                    }
                }
            }
        }
    }

    private fun addToCart(productId: String) {
        viewModelScope.launch {

            cartRepository.addToCart(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = resource.data?.message ?: "Added to cart"
                            )
                        }
                        getCart() // Refresh cart
                        getCartSummary() // Refresh summary
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error adding to cart"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun removeFromCart(sellerId: String? = null, productId: String? = null) {
        viewModelScope.launch {

            cartRepository.removeFromCart(sellerId, productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = "") }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = resource.data?.message ?: "Removed from cart"
                            )
                        }
                        getCart() // Refresh cart
                        getCartSummary() // Refresh summary
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error removing from cart"
                            )
                        }
                    }
                }
            }
        }
    }
}
