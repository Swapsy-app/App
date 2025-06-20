package com.example.freeupcopy.ui.presentation.profile_screen.shipping_label

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R

@Composable
fun ShippingLabelOrderScreen(){
    Column(
        Modifier
            .fillMaxWidth()
    ){
        HeaderSectionShippingLabelOrder()
        Spacer(Modifier.height(8.dp))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // 2 items per row
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(16.dp))
        ) {
            items(10) { index ->
                ProductCard(index)
            }
        }
    }
}

@Composable
fun ProductCard(index: Int) {
    val productNames = listOf(
        "6 Labels & Bag",
        "18 Labels & Bag",
        "36 Labels & Bag",
        "52 Labels & Bag"
    )

    val priceOfferedList = listOf("99", "179", "329", "659")
    val priceOriginalList = listOf("100", "200", "400", "800")

    // Ensure the index is within bounds
    if (index !in productNames.indices) {
        return
    }

    val productThumbnail = painterResource(id = R.drawable.label) // Placeholder Image

//    ProductCard(
//        isBigCard = true,
//        containerColor = MaterialTheme.colorScheme.background,
//        companyName = null,
//        productName = productNames[index],
//        size = "N/A",
//        productThumbnail = productThumbnail,
//        priceOffered = priceOfferedList[index],
//        coinsOffered = null,
//        specialOffer = null,
//        priceOriginal = priceOriginalList[index],
//        badge = null,
//        isLiked = false,
//        onLikeClick = {},
//        priorityPriceType = null, // Set priorityPriceType to null for all products
//        isClothes = false
//    )
}

@Composable
private fun HeaderSectionShippingLabelOrder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.label),
            contentDescription = "cash on delivery",
            modifier = Modifier.size(60.dp)
        )
        Spacer(Modifier.width(32.dp))
        Text(
            "Buy From Us",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview(showBackground = true) @Composable
private fun Preview(){
    ShippingLabelOrderScreen()
}