package com.example.freeupcopy.ui.presentation.setting.componants.blocked

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.product_card.ProductCard

@Composable
fun BlockedProduct(){
    Column(Modifier.fillMaxSize()) {
        Text("Blocked Product", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // 2 items per row
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(16.dp))
        ) {
            items(10) { index ->
//                ProductCard(
//                    isBigCard = true,
//                    containerColor = MaterialTheme.colorScheme.background,
//                    companyName = listOf(
//                        "Adidas", "Nike", "Puma", null, "Under Armour",
//                        "Levi's", "Calvin Klein", "Tommy Hilfiger", "Lacoste", null
//                    )[index],
//                    productName = listOf(
//                        "Adidas Bomber Jacket", "Nike Air Max", "Puma Suede", "Reebok Classic", "Under Armour Hoodie",
//                        "Levi's Jeans", "Calvin Klein T-shirt", "Tommy Hilfiger Polo", "Lacoste L.12.12", "Ralph Lauren Shirt"
//                    )[index],
//                    size = listOf(
//                        "40 inches", "L", "M", "XL", "L",
//                        "32 inches", "S", "M", "XL", "L"
//                    )[index],
//                    productThumbnail = painterResource(id = R.drawable.bomber_jacket), // Assuming R.drawable.bomber_jacket is a placeholder
//                    priceOffered = listOf(
//                        null, "499", "399", null, "799",
//                        "1999", null, "1499", "1299", null
//                    )[index],
//                    coinsOffered = listOf(
//                        null, "1000", null, "599", null,
//                        null, "999", null, null, "1799"
//                    )[index],
//                    specialOffer = listOf(
//                        listOf("4000", "2000"), listOf("300","800"), null, null, null,
//                        null, null, listOf("1000", "500"), null, null
//                    )[index],
//                    priceOriginal = listOf(
//                        "3500", "2999", "1999", "1499", "1999",
//                        "3999", "1999", "2999", "2499", "3499"
//                    )[index],
//                    badge = listOf(
//                        "Trusted", "Sale", "New", null, "Limited Edition",
//                        "Classic", "Trendy", "Luxury", "Iconic", null
//                    )[index],
//                    isLiked = true,
//                    onLikeClick = {},
//                    priorityPriceType = null, // Set priorityPriceType to null for all products
//                    isClothes = listOf(
//                        true, false, true, false, true,
//                        true, true, true, false, true
//                    )[index]
//                )
            }
        }
    }
}
@Preview(showBackground = true) @Composable
fun PreviewBlockedProduct(){
    BlockedProduct()
}