package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.sell.ProductCard
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import kotlin.math.max
import kotlin.random.Random

@Composable
fun BestInMen(
    modifier: Modifier = Modifier,
    bestInMenProducts: List<ProductCard>,
    isLoading: Boolean,
    error: String,
    onRetry: () -> Unit,
    onProductClick: (ProductCard) -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            ColorfulBackground(
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "Best in Men",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "View All",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
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
                Text(
                    text = "Incredible deals for Men",
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
                                    onClick = { onRetry() }
                                ) {
                                    Text("Refresh")
                                }
                            }
                        }
                    }

                    // Empty list
                    bestInMenProducts.isEmpty() -> {
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
                        Row {
                            (0 until minOf(3, bestInMenProducts.size)).forEach { index ->
                                Column(
                                    modifier = Modifier
                                        .aspectRatio(0.75f)
                                        .weight(1f)
                                        .padding(4.dp)
                                        .clip(CardShape.small)
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
                                            shape = CardShape.medium
                                        )
                                ) {
                                    SubcomposeAsyncImage(
                                        model = bestInMenProducts[index].images[0],
                                        contentDescription = "Product Image",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable(
                                                onClick = { onProductClick(bestInMenProducts[index]) },
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ),
                                        contentScale = ContentScale.Crop,
                                        loading = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_logo_full),
                                                    contentDescription = null
                                                )
                                            }
                                        },
                                        error = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_logo_full),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        // Second row
                        Row {
                            (3 until minOf(6, bestInMenProducts.size)).forEach { index ->
                                Column(
                                    modifier = Modifier
                                        .aspectRatio(0.75f)
                                        .weight(1f)
                                        .padding(8.dp)
                                        .clip(CardShape.medium)
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
                                            shape = CardShape.medium
                                        )
                                ) {
                                    SubcomposeAsyncImage(
                                        model = bestInMenProducts[index].images[0],
                                        contentDescription = "Product Image",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable(
                                                onClick = { onProductClick(bestInMenProducts[index]) },
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ),
                                        contentScale = ContentScale.Crop,
                                        loading = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_logo_full),
                                                    contentDescription = null
                                                )
                                            }
                                        },
                                        error = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_logo_full),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Remove pagination-specific code since we're using a regular List now
            }
        }
    }
}

@Composable
fun ColorfulBackground(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .blur(radius = 6.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Draw a radial gradient
        val gradient = Brush.radialGradient(
            colors = listOf(
                Color(0xFFCFFFF2),
                Color(0xFFF6FFFA),
            ),
            center = Offset(canvasWidth / 2f, canvasHeight / 2f),
            radius = max(canvasWidth, canvasHeight) / 2f
        )
        drawRect(brush = gradient)

        // Draw a pattern of shapes
        for (i in 0..10) {
            val offsetX = Random.nextFloat() * canvasWidth
            val offsetY = Random.nextFloat() * canvasHeight
            val size = Random.nextFloat() * 50 + 20

            // Draw a random shape (circle, rectangle, or triangle)
            when (Random.nextInt(2)) {
                0 -> drawCircle(
                    color = Color(0xFF9CFFD7),
                    radius = size / 2,
                    center = Offset(offsetX, offsetY)
                )

                1 -> drawCircle(
                    color = Color(0xFFADD4EE),
                    radius = size / 2,
                    center = Offset(offsetX - size / 2, offsetY - size / 2)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NinetyNineStorePreview() {
//    SwapGoTheme {
//        BestInMen()
//    }
//}