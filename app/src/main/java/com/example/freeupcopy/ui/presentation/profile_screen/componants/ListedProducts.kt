package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.freeupcopy.domain.model.ProfileProductListing

fun LazyListScope.ListedProducts(
    modifier: Modifier = Modifier,
    profileProductListings: List<ProfileProductListing>,
) {

    items(profileProductListings) { item ->
        ProfileProductListingItem(
            //modifier = Modifier.padding(horizontal = 8.dp),
            modifier = modifier,
            productId = item.productId,
            title = item.title,
            pricingModels = item.pricingModels,
            imageUrl = item.imageUrl,
            onClick = { },
            cashPrice = item.cashPrice,
            coinPrice = item.coinPrice,
            combinedPrice = item.combinedPrice,
            favoriteCount = item.favoriteCount,
            shareCount = item.shareCount,
            offerCount = item.offerCount,
        )
    }
}