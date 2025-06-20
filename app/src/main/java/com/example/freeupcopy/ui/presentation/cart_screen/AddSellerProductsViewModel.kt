package com.example.freeupcopy.ui.presentation.cart_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.freeupcopy.common.Resource
import com.example.freeupcopy.data.remote.dto.cart.CartProduct
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductCard
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
class AddSellerProductsViewModel @Inject constructor(
    private val getUserProductsUseCase: GetUserProductsUseCase,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddSellerProductsState())
    val state: StateFlow<AddSellerProductsState> = _state.asStateFlow()

    private var sellerId: String = ""

    // Store current cart items for this seller
    private val _cartItems = MutableStateFlow<Set<String>>(emptySet())

    // Initialize _availableProducts BEFORE using it in combine
    private val _availableProducts = MutableStateFlow(PagingData.empty<UserProductCard>())

    // Combine available products with cart filtering
    val availableProducts = combine(
        _availableProducts,
        _cartItems
    ) { products, cartItemIds ->
        products.filter { product ->
            !cartItemIds.contains(product._id)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PagingData.empty()
    )

    fun initializeSeller(sellerId: String) {
        this.sellerId = sellerId
        // Only call loadCurrentCartItems in init
        loadCurrentCartItems()
        loadProducts()
    }

    private fun loadCurrentCartItems() {
        viewModelScope.launch {
            cartRepository.getCart().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // Find cart items for this specific seller
                        val sellerCart = resource.data?.cart?.find { it.seller._id == sellerId }
                        val cartProductIds =
                            sellerCart?.products?.map { it._id }?.toSet() ?: emptySet()

                        // Update added products list
                        val addedProducts = sellerCart?.products?.map { product ->
                            AddedProduct(
                                productId = product._id,
                                title = product.title,
                                image = product.image,
                                price = formatProductPrice(product),
                                selectedPaymentMode = product.selectedPaymentMode ?: "cash"
                            )
                        } ?: emptyList()

                        _cartItems.value = cartProductIds
                        _state.update {
                            it.copy(
                                addedProducts = addedProducts,
                                isLoading = false,
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        _cartItems.value = emptySet()
                        _state.update {
                            it.copy(
                                addedProducts = emptyList(),
                                error = resource.message ?: "Unknown error occurred",
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true, error = "")
                        }
                    }
                }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            getUserProductsUseCase(sellerId, "available").collect { pagingData ->
                _availableProducts.value = pagingData
            }
        }
    }

    fun onEvent(event: AddSellerProductsUiEvent) {
        when (event) {
            is AddSellerProductsUiEvent.AddToCartUi -> addToCart(
                productId = event.productId,
                productTitle = event.productTitle,
                productImageUrl = event.productImageUrl,
                productPrice = event.price,
                selectedMode = event.selectedMode
            )
            is AddSellerProductsUiEvent.RemoveFromCart -> removeFromCart(event.productId)
            is AddSellerProductsUiEvent.ShowAddedItemsSheet -> {
                _state.update { it.copy(showAddedItemsSheet = true) }
            }

            is AddSellerProductsUiEvent.HideAddedItemsSheet -> {
                _state.update { it.copy(showAddedItemsSheet = false) }
            }

            is AddSellerProductsUiEvent.ConfirmAddedItems -> {
                _state.update { it.copy(showAddedItemsSheet = false) }
            }

            is AddSellerProductsUiEvent.RefreshProductsUi -> {
                // Only refresh when explicitly requested
                loadCurrentCartItems()
                loadProducts()
            }
            is AddSellerProductsUiEvent.ClearError -> {
                _state.update { it.copy(error = "") }
            }
            is AddSellerProductsUiEvent.ClearMessage -> {
                _state.update { it.copy(message = "") }
            }
            is AddSellerProductsUiEvent.IsLoading -> {
                _state.update { it.copy(isLoading = event.isLoading) }
            }
        }
    }

    private fun addToCart(
        productId: String,
        selectedMode: String,
        productTitle: String,
        productImageUrl: String,
        productPrice: String
    ) {
        viewModelScope.launch {
            cartRepository.addToCart(productId, selectedMode).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        // Update local state only - don't call loadCurrentCartItems()
                        _state.update { currentState ->
                            // Add to cart items set
                            val updatedCartItems = currentState.addedProducts.toMutableList()

                            // Create new AddedProduct (you'll need to get product details)
                            val newAddedProduct = AddedProduct(
                                productId = productId,
                                title = productTitle, // You might want to get this from available products
                                image = productImageUrl, // You might want to get this from available products
                                price = productPrice, // You might want to get this from available products
                                selectedPaymentMode = selectedMode
                            )
                            updatedCartItems.add(newAddedProduct)

                            // Update cart items set for filtering
                            val updatedCartItemIds = _cartItems.value.toMutableSet()
                            updatedCartItemIds.add(productId)
                            _cartItems.value = updatedCartItemIds

                            currentState.copy(
                                addedProducts = updatedCartItems,
                                isLoading = false,
                                message = resource.data?.message ?: "Product added to cart successfully",
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error adding product to cart",
                                message = ""
                            )
                        }
                    }
                }
            }
        }
    }

    private fun removeFromCart(productId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId = productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        // Update local state only - don't call loadCurrentCartItems()
                        _state.update { currentState ->
                            // Remove from added products list
                            val updatedAddedProducts = currentState.addedProducts.filter {
                                it.productId != productId
                            }

                            // Update cart items set for filtering
                            val updatedCartItemIds = _cartItems.value.toMutableSet()
                            updatedCartItemIds.remove(productId)
                            _cartItems.value = updatedCartItemIds

                            currentState.copy(
                                addedProducts = updatedAddedProducts,
                                isLoading = false,
                                message = resource.data?.message ?: "Product removed from cart",
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message ?: "Error removing product from cart",
                                message = ""
                            )
                        }
                    }
                }
            }
        }
    }

    private fun formatProductPrice(product: CartProduct): String {
        return when {
            product.price.cash != null -> "₹${product.price.cash.toInt()}"
            product.price.coin != null -> "${product.price.coin.toInt()} coins"
            product.price.mixCash != null && product.price.mixCoin != null ->
                "₹${product.price.mixCash.toInt()} + ${product.price.mixCoin.toInt()} coins"

            else -> "Price not available"
        }
    }
}


data class AddSellerProductsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val message: String = "",
    val addedProducts: List<AddedProduct> = emptyList(),
    val showAddedItemsSheet: Boolean = false
)

data class AddedProduct(
    val productId: String,
    val title: String,
    val image: String,
    val price: String,
    val selectedPaymentMode: String
)

sealed class AddSellerProductsUiEvent {
    data class AddToCartUi(
        val productId: String,
        val selectedMode: String,
        val productTitle: String,
        val productImageUrl: String,
        val price: String
    ) : AddSellerProductsUiEvent()
    data class RemoveFromCart(val productId: String) : AddSellerProductsUiEvent()
    object ShowAddedItemsSheet : AddSellerProductsUiEvent()
    object HideAddedItemsSheet : AddSellerProductsUiEvent()
    object ConfirmAddedItems : AddSellerProductsUiEvent()
    object RefreshProductsUi : AddSellerProductsUiEvent()
    object ClearError : AddSellerProductsUiEvent()
    object ClearMessage : AddSellerProductsUiEvent()
    data class IsLoading(val isLoading: Boolean) : AddSellerProductsUiEvent()
}
