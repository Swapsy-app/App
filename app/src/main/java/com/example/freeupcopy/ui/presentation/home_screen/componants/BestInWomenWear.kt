package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.sell.Product
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.ui.presentation.product_card.ProductCard

@Composable
fun BestInWomenWear(
    modifier: Modifier = Modifier,
    bestInWomenWear: List<ProductCard>,
    isLoading: Boolean,
    error: String,
    onProductClick: (ProductCard) -> Unit,
    onRetry: () -> Unit,
    onViewAll: () -> Unit,
    onLikeClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFDE1F2),
                        Color(0xFFD3FFBC).copy(0.5f),
                    ),
                    start = Offset(Float.POSITIVE_INFINITY, 0f),    // Top end (top-right)
                    end = Offset(0f, Float.POSITIVE_INFINITY)       // Bottom start (bottom-left)
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.im_women_style),
            contentDescription = null,
            modifier = Modifier
                .blur(5.dp)
                .offset(x = 20.dp, y = (-20).dp)
                .align(Alignment.TopEnd)
                .rotate(-130f),
        )

        Image(
            painter = painterResource(id = R.drawable.im_women_style2),
            contentDescription = null,
            modifier = Modifier
                .blur(5.dp)
                .height(260.dp)
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Best in Women Wear",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = 18.sp
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Incredible deals for Women",
                fontSize = 15.sp
            )
            Spacer(Modifier.size(8.dp))

            // Handle different states
            when {
                // Loading state
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // Error state
                error.isNotEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error loading products",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { onRetry() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFBDD9),
                                    contentColor = Color.Black
                                )
                            ) {
                                Text("Refresh")
                            }
                        }
                    }
                }

                // Empty list
                bestInWomenWear.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No products available",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                // Content loaded successfully
                else -> {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(bestInWomenWear) { product ->
                            ProductCard(
                                brand = product.brand,
                                title = product.title,
                                size = "null",
                                productThumbnail = if (product.images.size == 1) product.images[0] else null,
                                cashPrice = if (product.price.cashPrice != null) product.price.cashPrice.toInt()
                                    .toString() else null,
                                coinsPrice = if (product.price.coinPrice != null) product.price.coinPrice.toInt()
                                    .toString() else null,
                                combinedPrice =
                                    if (product.price.mixPrice != null)
                                        Pair(
                                            product.price.mixPrice.enteredCash.toInt()
                                                .toString(),
                                            product.price.mixPrice.enteredCoin.toInt()
                                                .toString()
                                        )
                                    else
                                        null,
                                mrp = product.price.mrp?.toInt().toString(),
                                badge = "null",
                                isLiked = product.isWishlisted,
                                onLikeClick = {
                                    onLikeClick(product._id)
                                },
                                onClick = {
                                    onProductClick(product)
                                },
                                user = product.seller
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.size(6.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(CircleShape)
                    .clickable {
                        onViewAll()
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View all incredible deals for women",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


//@Composable
//fun WomenStyleCard(
//    modifier: Modifier = Modifier,
//    item: WomenWearItem
//) {
//    ProductCard(
//        modifier = modifier,
//        brand = item.brand,
//        title = item.brand,
//        productThumbnail = "item.image",
//        size = item.size,
//        cashPrice = item.listedPrice,
//        coinsPrice = null,
//        combinedPrice = null,
//        mrp = item.mrp,
//        badge = null,
//        isLiked = false,
//        onLikeClick = {},
//        onClick = {}
//    )
//}
//
//data class WomenStyleItem(
//    val image: Painter,
//    val brand: String,
//    val size: String,
//    val listedPrice: String,
//    val mrp: String,
//    val onClick: () -> Unit
//)
//
//@Preview(showBackground = true)
//@Composable
//private fun WomenStylePreview() {
//    SwapGoTheme {
//        WomenStyle()
//    }
//}