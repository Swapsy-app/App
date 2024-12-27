package com.example.freeupcopy.ui.presentation.product_screen.componants

import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapsyTheme
import kotlinx.coroutines.launch

@Composable
fun ProductDetails(
    productTitle: String = "Product Title",
    productCondition: String = "New with Price Tag",
    productSize: String = "Bust 32in",
    productBrand: String = "Adidas",
    productDescription: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula eros ut libero dictum, a pretium ligula hendrerit. Curabitur auctor magna id felis interdum, vitae feugiat velit.",
    productCategory: String = "Office Supplies & Stationery",
    productFabric: String = "Product Fabric",
    productOccasion: String = "Product Occasion",
    productPlaceOfOrigin: String = "India",
    productColor: String = "Product Color",
    productShape: String = "Product Shape",
    productWeight: String = "Product Weight",
    imageSelected: Int,
    changeImage: (Int) -> Unit,
    isLiked: Boolean,
    numberOfLikes: Int,
    productLink: String,
    shareCounter: Int
) {
    val context = LocalContext.current
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
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "like icon",
                    tint = Color.Red,
                    modifier = Modifier.clickable { }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(

                    text = numberOfLikes.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = "share icon",
                    modifier = Modifier.clickable {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, productLink)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = shareCounter.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = productTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                Text(
                    text = "Condition : ",
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = productCondition,
                )
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
                    text = productBrand,
                )
            }
        }


        //Spacer(modifier = Modifier.size(24.dp))
        DynamicImage(
            imageSelected = imageSelected,
            changeImage = changeImage
        )
        Spacer(modifier = Modifier.size(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Text(
                text = productDescription,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Category",
                        value = productCategory
                    )
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Manufactured Country",
                        value = productPlaceOfOrigin
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Fabric",
                        value = productFabric
                    )
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Color",
                        value = productColor
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Occasion",
                        value = productOccasion
                    )
                    ProductSpecification(
                        modifier = Modifier.weight(1f),
                        heading = "Shape",
                        value = productShape
                    )
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
                            text = productWeight
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
    images: List<Painter> = listOf(
        painterResource(id = R.drawable.ic_comment),
        painterResource(id = R.drawable.ic_campaign),
        painterResource(id = R.drawable.ic_add_location),
        painterResource(id = R.drawable.ic_community),
        painterResource(id = R.drawable.ic_favorite),

        ),
    changeImage: (Int) -> Unit,
    imageSelected: Int,
) {
    val pagerState = rememberPagerState(initialPage = 0) { images.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            HorizontalPager(
                state = pagerState,
            ) { currentPage ->

                Card(
                    modifier.fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier
                            .size(300.dp),
                        painter = images[currentPage],
                        contentDescription = null
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    //.padding(30.dp)
                    .size(48.dp)
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
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.LightGray
                )
            }

            IconButton(
                modifier = Modifier
                    //.padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
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
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = Color.LightGray
                )
            }
        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )
    }

}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        modifier = modifier,
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
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) Color.Black else Color.Gray
            )
    ) {

    }
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


@Preview
@Composable
fun PreviewProductDetails() {
    SwapsyTheme {
        ProductDetails(
            imageSelected = 1,
            changeImage = {},
            isLiked = false,
            numberOfLikes = 34,
            productLink = "Mock Link",
            shareCounter = 35
        )
    }
}