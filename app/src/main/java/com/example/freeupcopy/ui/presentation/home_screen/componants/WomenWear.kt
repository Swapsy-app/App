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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun WomenWear(
    modifier: Modifier = Modifier,
    items: List<WomenWearItem> = listOf(
        WomenWearItem(
            image = painterResource(R.drawable.we3),
            brand = "Adidas",
            size = "L",
            listedPrice = "2000",
            mrp = "5000",
            onClick = {}
        ),
        WomenWearItem(
            image = painterResource(R.drawable.we4),
            brand = "Adidas",
            size = "L",
            listedPrice = "2000",
            mrp = "5000",
            onClick = {}
        ),
        WomenWearItem(
            image = painterResource(R.drawable.we5),
            brand = "Adidas",
            size = "L",
            listedPrice = "2000",
            mrp = "5000",
            onClick = {}
        ),
        WomenWearItem(
            image = painterResource(R.drawable.we1),
            brand = "Adidas",
            size = "L",
            listedPrice = "2000",
            mrp = "5000",
            onClick = {}
        ),
        WomenWearItem(
            image = painterResource(R.drawable.we2),
            brand = "Adidas",
            size = "L",
            listedPrice = "2000",
            mrp = "5000",
            onClick = {}
        )
    )
) {
    Box(
        Modifier
//            .background(
//                Color(0xFFAB2330)
//            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6E191D),
                        Color(0xFFAB2330),
                        Color(0xFFFFD700).copy(0.5f),
                        Color.White,
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .fillMaxWidth()  // Add this to ensure the Box takes full width
    ) {
        Image(
            painter = painterResource(R.drawable.im_women_ethnic_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()  // Add this to maintain aspect ratio
                .alpha(0.3f)
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    val colors = listOf(
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Transparent
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.DstIn
                    )
                },
            contentScale = ContentScale.FillWidth  // Change back to FillWidth to maintain aspect ratio
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(CircleShape)
                    .clickable { }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View All",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp),
                    tint = Color(0xFFFFD700)
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Explore\nFinest Women's Ethnic Wear",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFFFFF8DC),
            )

            Spacer(Modifier.size(4.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "\"Elegant traditional styles for every occasion.\"",
                fontSize = 15.sp,
                color = Color(0xFFFFD700),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.W500
            )
            Spacer(Modifier.size(24.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) {
                    WomenWearCard(
                        item = it
                    )
                }
            }

            Spacer(Modifier.size(16.dp))
            Image(
                painter = painterResource(id = R.drawable.im_women_swirl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                colorFilter = ColorFilter.tint(Color(0xFFFFD700))
            )
        }

    }

}

@Composable
fun WomenWearCard(
    modifier: Modifier = Modifier,
    item: WomenWearItem
) {
    ProductCard(
        modifier = modifier,
        brand = item.brand,
        title = item.brand,
        productThumbnail = "item.image",
        size = item.size,
        cashPrice = item.listedPrice,
        coinsPrice = null,
        combinedPrice = null,
        mrp = item.mrp,
        badge = null,
        isLiked = false,
        onLikeClick = {},
        onClick = {}
    )
}

data class WomenWearItem(
    val image: Painter,
    val brand: String,
    val size: String,
    val listedPrice: String,
    val mrp: String,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
private fun PreviewWomenWear() {
    SwapGoTheme {
        WomenWear()
//        WomenWearCard(
//            item = WomenWearItem(
//                image = painterResource(R.drawable.bomber_jacket),
//                brand = "Adidas",
//                size = "L",
//                listedPrice = "2000",
//                mrp = "5000",
//                onClick = {}
//            )
//        )
    }
}