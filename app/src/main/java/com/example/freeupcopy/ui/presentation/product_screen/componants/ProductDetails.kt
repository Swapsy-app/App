package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import kotlinx.coroutines.launch

@Composable
fun ProductDetails(
    title: String,
    condition: String,
    images: List<String>,
    productSize: String = "Bust 32in",
    brand: String = "Lymio",
    description: String,
    category: String,
    fabric: String?,
    occasion: String?,
    manufacturingCountry: String,
    color: String?,
    shape: String?,
    weight: String = "Product Weight",
    numberOfLikes: Int,
    shareCounter: Int,
    onImagePreview: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = "like icon",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(

                    text = numberOfLikes.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "share icon",
//                    modifier = Modifier.clickable {
//                        val shareIntent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(Intent.EXTRA_TEXT, productLink)
//                            type = "text/plain"
//                        }
//                        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
//                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = shareCounter.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                Text(
                    text = "Condition : ",
                    fontWeight = FontWeight.SemiBold,
                )
                Text(text = condition)
            }
            Row {
                Text(
                    text = "Size : ",
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = productSize,
                )
            }
            Row {
                Text(
                    text = "Brand : ",
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = brand,
                )
            }
        }

        DynamicImage(
            onImagePreview = onImagePreview,
            images = images
        )
        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Product Description",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = description,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CardShape.medium)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.04f))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Category",
                        value = category
                    )
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Manufactured Country",
                        value = manufacturingCountry
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (fabric != null) {
                        ProductSpecification(
                            modifier = Modifier.weight(1f),
                            heading = "Fabric",
                            value = fabric
                        )
                    }
                    if (color != null) {
                        ProductSpecification(
                            modifier = Modifier.weight(1f),
                            heading = "Color",
                            value = color
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (occasion != null) {
                        ProductSpecification(
                            modifier = Modifier.weight(1f),
                            heading = "Occasion",
                            value = occasion
                        )
                    }
                    if (shape != null) {
                        ProductSpecification(
                            modifier = Modifier.weight(1f),
                            heading = "Shape",
                            value = shape
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column {
                        Text(
                            text = "Weight",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = weight
                        )
                    }
                }
            }
        }


    }

}

@Composable
fun DynamicImage(
    modifier: Modifier = Modifier,
    images: List<String>,
    onImagePreview: (Int) -> Unit
) {

    val pagerState = rememberPagerState(initialPage = 0) { images.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            HorizontalPager(
                state = pagerState,
            ) { currentPage ->

//                Image(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(0.75f)
//                        .clickable(
//                            interactionSource = remember { MutableInteractionSource() },
//                            indication = null,
//                            onClick = {
//                                onImagePreview(pagerState.currentPage)
//                            }
//                        ),
//                    painter = painterResource(id = images[currentPage]),
//                    contentDescription = null
//                )
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.75f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onImagePreview(pagerState.currentPage)
                            }
                        ),
                    model = images[currentPage],
                    contentDescription = null,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                painter = painterResource(id = R.drawable.ic_logo_full),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                painter = painterResource(id = R.drawable.ic_logo_full),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )

            }

            IconButton(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < images.size) {
                        scope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.4f)
                )
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            IconButton(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.4f)
                ),
                onClick = {
                    val prevPage = pagerState.currentPage - 1
                    if (prevPage >= 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(prevPage)
                        }
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) {
            IndicatorDots(
                isSelected = it == currentPage,
                modifier = modifier
            )
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 8.dp, label = "")
    Box(
        modifier = modifier
            .padding(3.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) Color.Black else Color.Black.copy(0.2f)
            )
    )
}

@Composable
fun ProductSpecification(
    modifier: Modifier = Modifier,
    heading: String,
    value: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = heading,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
        )
        Text(
            text = value,
        )
    }
}

@Composable
fun ImagePreviewDialog(
    images: List<String>,
    initialImageIndex: Int,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = initialImageIndex,
        pageCount = { images.size }
    )

    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialImageIndex)

    // Keep thumbnail scroll position synced with main pager
    LaunchedEffect(pagerState.currentPage) {
        lazyListState.animateScrollToItem(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets(0.dp))
            .navigationBarsPadding()
            .background(Color.White)
    ) {
        // Main content pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            key = { images[it] }
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
//                Image(
//                    painter = painterResource(id = images[page]),
//                    contentDescription = "Image ${page + 1}",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Fit
//                )
                SubcomposeAsyncImage(
                    model = images[page],
                    contentDescription = "Image ${page + 1}",
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo_full),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo_full),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            }
        }

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${pagerState.currentPage + 1} / ${images.size}",
                color = Color.Black,
                fontSize = 16.sp
            )
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "CLOSE",
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }

        // Navigation arrows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = CircleShape
                    ),
                enabled = pagerState.currentPage > 0
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous image",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < images.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = CircleShape
                    ),
                enabled = pagerState.currentPage < images.size - 1
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next image",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Thumbnail strip at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp)
        ) {
            LazyRow(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                color = if (index == pagerState.currentPage)
                                    Color.Black.copy(alpha = 0.15f)
                                else
                                    Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                    ) {
//                        Image(
//                            painter = painterResource(id = images[index]),
//                            contentDescription = "Thumbnail ${index + 1}",
//                            modifier = Modifier
//                                .padding(4.dp)
//                                .fillMaxSize(),
//                            contentScale = ContentScale.Crop,
//                            alpha = 0.8f
//                        )
                        SubcomposeAsyncImage(
                            model = images[index],
                            contentDescription = "Thumbnail ${index + 1}",
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize(),
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_logo_full),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            error = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_logo_full),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProductDetails() {
    SwapGoTheme {
        ProductDetails(
//            imageSelected = 1,
//            changeImage = {},
            numberOfLikes = 34,
            shareCounter = 35,
            onImagePreview = {},
            title = "Product Title",
            condition = "New",
            description = "This is a sample product description. It provides details about the product, including its features, specifications, and other relevant information.",
            category = "Clothing",
            fabric = "Cotton",
            occasion = "Casual",
            manufacturingCountry = "India",
            color = "Red",
            shape = "Round",
            images = listOf("https://via.placeholder.com/150", "https://via.placeholder.com/150", "https://via.placeholder.com/150"),
        )
    }
}