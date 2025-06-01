package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.data.remote.dto.sell.ProductPrice
import com.example.freeupcopy.data.remote.dto.your_profile.UserProductCard
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ListingItem
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.PostedProductsViewModel

@Composable
fun ListedList(
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit,
    onActionClick: (String) -> Unit,
    viewModel: PostedProductsViewModel = hiltViewModel()
) {
    val listedProducts = viewModel.listedProducts.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        items(listedProducts.itemCount) { index ->
            listedProducts[index]?.let { product ->
                ListingItem(
                    imageUrl = product.images.firstOrNull() ?: "",
                    onClick = { onProductClick(product._id) },
                    title = product.title,
                    pricingModels = getPricingModels(product.price),
                    cashPrice = product.price.cashPrice?.toInt().toString(),
                    coinPrice = product.price.coinPrice?.toInt().toString(),
                    combinedPrice = product.price.mixPrice?.let {
                        Pair(it.enteredCash.toInt().toString(), it.enteredCoin.toInt().toString())
                    },
                    favoriteCount = "0", // You'll need to add these to your backend response
                    shareCount = "0",
                    offerCount = "0",
                    productId = product._id,
                    viewCount = "0",
                    isConfirmPending = product.status == "underReview",
                    isShippingGuide = product.status == "available",
                    onActionsClick = { onActionClick(product._id) }
                )

                HorizontalDivider(thickness = 1.5.dp)
            }
        }

        // Handle loading and error states
        listedProducts.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Error loading products")
                            Button(onClick = { retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

// Helper function to determine pricing models from UserProductPrice
private fun getPricingModels(price: ProductPrice): List<PricingModel> {
    val models = mutableListOf<PricingModel>()

    if (price.cashPrice != null) {
        models.add(PricingModel.CASH)
    }

    if (price.coinPrice != null) {
        models.add(PricingModel.COINS)
    }

    if (price.mixPrice != null) {
        models.add(PricingModel.CASH_AND_COINS)
    }

    return models
}