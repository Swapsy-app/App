package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun HomeDashboard(
    modifier: Modifier = Modifier,
    on99StoreClick: () -> Unit,
    on299CoinStoreClick: () -> Unit,
) {
    val items = listOf(
        HomeDashboardItem(
            title = "99 Store",
            description = "Incredible deals just for ₹99",
            iconColor = Color.Red,
            image = painterResource(id = R.drawable.ic_99_store),
            onClick = on99StoreClick
        ),
        HomeDashboardItem(
            title = "Buy Coins",
            description = "Massive discounts on all products",
            iconColor = Color.Blue,
            image = painterResource(id = R.drawable.ic_offer),
            onClick = {}
        ),
        HomeDashboardItem(
            title = "Borrow Coins",
            description = "Borrow coins for your purchases",
            iconColor = Color.Unspecified,
            image = painterResource(id = R.drawable.im_borrow),
            onClick = {}
        ),
        HomeDashboardItem(
            title = "299 Coins Store",
            description = "Buy products with coins @ ₹299",
            iconColor = Color.Unspecified,
            image = painterResource(id = R.drawable.coin),
            onClick = on299CoinStoreClick
        ),
        HomeDashboardItem(
            title = "SwapGo Assured",
            description = "Quality products with assurance",
            iconColor = Color.Unspecified,
            image = painterResource(id = R.drawable.ic_assured),
            onClick = {}
        ),
        HomeDashboardItem(
            title = "Shipping Materials",
            description = "All shipping materials available",
            iconColor = LinkColor,
            image = painterResource(id = R.drawable.ic_your_orders),
            onClick = {}
        )
    )
    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        Color(0xFFFFF7FC),
                        MaterialTheme.colorScheme.tertiary.copy(0.25f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Explore SwapGo",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Shop smart with our special stores and deals",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.65f),
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(2) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { colIndex ->
                        val index = rowIndex * 3 + colIndex
                        if (index < items.size) {
                            HomeDashBoardCard(
                                item = items[index],
                                modifier = Modifier
                                    .shadow(3.dp, shape = CardShape.small)
                                    .height(100.dp)
                                    .weight(1f)
//                                .aspectRatio(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeDashBoardCard(
    modifier: Modifier = Modifier,
    item: HomeDashboardItem
) {
    Column(
        modifier = modifier
            .clip(CardShape.small)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                item.onClick()
            }
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = item.image,
            contentDescription = null,
            tint = item.iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = item.title,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            lineHeight = 18.sp
        )
//        Spacer(modifier = Modifier.size(4.dp))
//        Text(
//            text = item.description,
//            fontSize = 11.sp,
//            lineHeight = 16.sp,
//            textAlign = TextAlign.Center
//        )
    }
}

data class HomeDashboardItem(
    val title: String,
    val description: String,
    val image: Painter,
    val iconColor: Color,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
private fun HomeDashboardPreview() {
    SwapGoTheme {
//        HomeDashBoardCard(
//            item = HomeDashboardItem(
//                title = "Clearance Sale",
//                description = "Massive discounts on all products",
//                iconColor = Color.Red,
//                image = painterResource(id = R.drawable.ic_offer),
//                onClick = {}
//            )
//        )
        HomeDashboard(
            on99StoreClick = {},
            on299CoinStoreClick = {}
        )
    }
}