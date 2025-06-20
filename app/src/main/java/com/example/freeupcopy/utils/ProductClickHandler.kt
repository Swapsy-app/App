package com.example.freeupcopy.ui.presentation.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiEvent
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A wrapper class to handle product click functionality across multiple composables
 */
class ProductClickHandler(
    private val scope: CoroutineScope,
    private val lifecycleOwner: LifecycleOwner,
    private val productListingViewModel: ProductListingViewModel,
    private val onProductClick: (String) -> Unit,
    private val onLoadingStateChange: ((Boolean) -> Unit)? = null
) {
    /**
     * Handles the product click event with proper lifecycle checks and loading state management
     *
     * @param product The product that was clicked
     */
    fun handleProductClick(product: ProductCard) {
        val currentState = lifecycleOwner.lifecycle.currentState
        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            scope.launch {
                // Set loading state to true
                onLoadingStateChange?.invoke(true)
                productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(true))

                // Prepare product data for navigation
                productListingViewModel.onEvent(
                    ProductListingUiEvent.ProductClicked(
                        productId = product._id,
                        productImageUrl = if (product.images.size == 1) product.images[0] else "",
                        title = product.title
                    )
                )

                // Small delay to ensure events are processed
                delay(100)

                // Navigate to product screen
                onProductClick(product._id)

                // Reset loading state
                productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(false))
                onLoadingStateChange?.invoke(false)
            }
        }
    }

    fun handleProductValueClick(productId: String, productImageUrl: String, title: String) {
        val currentState = lifecycleOwner.lifecycle.currentState
        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            scope.launch {
                // Set loading state to true
                onLoadingStateChange?.invoke(true)
                productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(true))

                // Prepare product data for navigation
                productListingViewModel.onEvent(
                    ProductListingUiEvent.ProductClicked(
                        productId = productId,
                        productImageUrl = productImageUrl,
                        title = title
                    )
                )

                // Small delay to ensure events are processed
                delay(100)

                // Navigate to product screen
                onProductClick(productId)

                // Reset loading state
                productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(false))
                onLoadingStateChange?.invoke(false)
            }
        }
    }

    /**
     * Handles the product ID click event (when only ID is available)
     *
     * @param productId The ID of the product that was clicked
     */
    fun handleProductIdClick(productId: String) {
        val currentState = lifecycleOwner.lifecycle.currentState
        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            scope.launch {
                onLoadingStateChange?.invoke(true)
                productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(true))

                // Navigate directly with just the ID
                onProductClick(productId)

                productListingViewModel.onEvent(ProductListingUiEvent.IsLoading(false))
                onLoadingStateChange?.invoke(false)
            }
        }
    }
}

/**
 * Composable function to remember a ProductClickHandler instance
 */
@Composable
fun rememberProductClickHandler(
    productListingViewModel: ProductListingViewModel,
    onProductClick: (String) -> Unit,
    onLoadingStateChange: ((Boolean) -> Unit)? = null
): ProductClickHandler {
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    return remember(productListingViewModel, onProductClick) {
        ProductClickHandler(
            scope = scope,
            lifecycleOwner = lifecycleOwner,
            productListingViewModel = productListingViewModel,
            onProductClick = onProductClick,
            onLoadingStateChange = onLoadingStateChange
        )
    }
}
