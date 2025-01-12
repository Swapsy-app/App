package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.domain.model.ProfileProductListing
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ListingItem

@Composable
fun PendingList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(50) {
            ListingItem(
                imageUrl = "",
                onClick = {},
                onActionsClick = {},
                title = "Hello darkness smile friend",
                pricingModels = listOf(PricingModel.CASH, PricingModel.COINS, PricingModel.CASH_AND_COINS),
                cashPrice = "100",
                coinPrice = "450",
                combinedPrice = Pair("100", "450"),
                favoriteCount = "6",
                shareCount = "14",
                offerCount = "2",
                productId = "1",
                viewCount = "17",
            )
            HorizontalDivider(thickness = 1.5.dp)
        }

    }
}