// ui/presentation/cart/CartScreen.kt
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.cart_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.freeupcopy.common.Constants
import com.example.freeupcopy.data.remote.dto.cart.CartProduct
import com.example.freeupcopy.data.remote.dto.cart.SellerCart
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun CartScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart (${state.totalProducts})",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.cartItems.isEmpty()) {
                EmptyCartContent(
                    onContinueShopping = onNavigateBack
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.cartItems) { sellerCart ->
                        SellerCartCard(
                            sellerCart = sellerCart,
                            onRemoveProduct = { productId ->
                                viewModel.onEvent(CartUiEvent.RemoveProduct(productId))
                            },
                            onRemoveSeller = { sellerId ->
                                viewModel.onEvent(CartUiEvent.RemoveSeller(sellerId))
                            }
                        )
                    }
                }

                // Checkout Section
                CheckoutSection(
                    totalCombos = state.totalCombos,
                    onCheckout = onNavigateToCheckout
                )
            }

            // Show error message
            if (state.error.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = state.error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Show success message
            if (state.message.isNotEmpty()) {
                LaunchedEffect(state.message) {
                    // You can show a snackbar here
                }
            }
        }
    }
}

@Composable
fun EmptyCartContent(
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add some products to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinueShopping,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue Shopping")
        }
    }
}

@Composable
fun SellerCartCard(
    sellerCart: SellerCart,
    onRemoveProduct: (String) -> Unit,
    onRemoveSeller: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = CardShape.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Seller Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = Constants.BASE_URL_AVATAR + sellerCart.seller.avatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = sellerCart.seller.username,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = { onRemoveSeller(sellerCart.seller.username) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove all from seller"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Products
            sellerCart.products.forEach { product ->
                CartProductItem(
                    product = product,
                    onRemove = { onRemoveProduct(product.title) } // You might need to pass product ID
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Divider()

            Spacer(modifier = Modifier.height(8.dp))

            // Total Price
            TotalPriceSection(sellerCart = sellerCart)
        }
    }
}

@Composable
fun CartProductItem(
    product: CartProduct,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = formatPrice(product.price),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remove item",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun TotalPriceSection(sellerCart: SellerCart) {
    Column {
        Text(
            text = "Total:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        val totalPrice = sellerCart.totalPrice
        if (totalPrice.totalCash > 0) {
            Text("Cash: ₹${totalPrice.totalCash}")
        }
        if (totalPrice.totalCoin > 0) {
            Text("Coins: ${totalPrice.totalCoin}")
        }
        if (totalPrice.totalMix.cash > 0 || totalPrice.totalMix.coin > 0) {
            Text("Mix: ₹${totalPrice.totalMix.cash} + ${totalPrice.totalMix.coin} coins")
        }
    }
}

@Composable
fun CheckoutSection(
    totalCombos: Int,
    onCheckout: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Ready to checkout $totalCombos combo${if (totalCombos > 1) "s" else ""}?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceed to Checkout")
            }
        }
    }
}

private fun formatPrice(price: com.example.freeupcopy.data.remote.dto.cart.ProductPrice): String {
    return when {
        price.cash != null -> "₹${price.cash}"
        price.coin != null -> "${price.coin} coins"
        price.mixCash != null && price.mixCoin != null -> "₹${price.mixCash} + ${price.mixCoin} coins"
        else -> "Price not available"
    }
}

@Preview
@Composable
fun CartScreenPreview() {
    SwapGoTheme {
        SellerCartCard(
            sellerCart = SellerCart(
                seller = com.example.freeupcopy.data.remote.dto.cart.Seller(
                    username = "Test Seller",
                    avatar = "https://example.com/avatar.jpg"
                ),
                products = listOf(
                    CartProduct(
                        title = "Test Product 1",
                        image = "https://example.com/product1.jpg",
                        price = com.example.freeupcopy.data.remote.dto.cart.ProductPrice(
                            cash = 500.0,
                            coin = null,
                            mixCash = null,
                            mixCoin = null
                        )
                    ),
                    CartProduct(
                        title = "Test Product 2",
                        image = "https://example.com/product2.jpg",
                        price = com.example.freeupcopy.data.remote.dto.cart.ProductPrice(
                            cash = null,
                            coin = 1000.0,
                            mixCash = null,
                            mixCoin = null
                        )
                    )
                ),
                totalPrice = com.example.freeupcopy.data.remote.dto.cart.TotalPrice(
                    totalCash = 500.0,
                    totalCoin = 1000.0,
                    totalMix = com.example.freeupcopy.data.remote.dto.cart.MixPrice(0.0, 0.0)
                )
            ),
            onRemoveProduct = {},
            onRemoveSeller = {}
        )
    }
}
