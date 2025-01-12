package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ListingItem
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun ListedList(
    modifier: Modifier = Modifier,
    onProductClick: () -> Unit,
    onActionClick: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {

//        items(50) {
//            ListingItem(
//                imageUrl = "",
//                onClick = {
//                    val currentState = lifeCycleOwner.lifecycle.currentState
//                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
//                        onProductClick()
//                    }
//                },
//                title = "Fidget Spinner Phone",
//                pricingModels = listOf(
//                    PricingModel.CASH,
//                    PricingModel.COINS,
//                    PricingModel.CASH_AND_COINS
//                ),
//                cashPrice = "200",
//                coinPrice = "780",
//                combinedPrice = Pair("160", "120"),
//                favoriteCount = "6",
//                shareCount = "14",
//                offerCount = "2",
//                productId = "1",
//                viewCount = "126",
//            )
        items(profileProductListings) {
            ListingItem(
                imageUrl = it.imageUrl,
                onClick = { onProductClick() },
                title = it.title,
                pricingModels = it.pricingModels,
                cashPrice = it.cashPrice,
                coinPrice = it.coinPrice,
                combinedPrice = it.combinedPrice,
                favoriteCount = it.favoriteCount,
                shareCount = it.shareCount,
                offerCount = it.offerCount,
                productId = it.productId,
                viewCount = it.viewCount,
                isConfirmPending = it.isConfirmPending,
                isShippingGuide = it.isShippingGuide,
                onActionsClick = { onActionClick() }
            )

            HorizontalDivider(thickness = 1.5.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListedListPreview() {
    SwapGoTheme {
        ListedList(
            onProductClick = {},
            onActionClick = {}
        )
    }
}

val profileProductListings: List<ListItem> = listOf(
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=1",
        productId = "1",
        title = "Vintage Leather Backpack",
        pricingModels = listOf(PricingModel.CASH, PricingModel.CASH_AND_COINS),
        cashPrice = "89.99",
        combinedPrice = Pair("89", "150"),
        favoriteCount = "342",
        shareCount = "156",
        offerCount = "45",
        viewCount = "234",
        isConfirmPending = true,
        isShippingGuide = false
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=2",
        productId = "2",
        title = "Wireless Noise-Canceling Headphones",
        pricingModels = listOf(PricingModel.CASH),
        cashPrice = "199.99",
        favoriteCount = "567",
        shareCount = "234",
        offerCount = "78",
        viewCount = "456",
        isConfirmPending = false,
        isShippingGuide = true
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=3",
        productId = "3",
        title = "Smart Fitness Watch",
        pricingModels = listOf(PricingModel.CASH_AND_COINS),
        combinedPrice = Pair("189", "450"),
        favoriteCount = "789",
        shareCount = "445",
        offerCount = "123",
        viewCount = "567",
        isConfirmPending = false,
        isShippingGuide = false
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=4",
        productId = "4",
        title = "Handmade Ceramic Mug Set",
        pricingModels = listOf(PricingModel.COINS),
        coinPrice = "500",
        favoriteCount = "234",
        shareCount = "89",
        offerCount = "34",
        viewCount = "345",
        isConfirmPending = false,
        isShippingGuide = false
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=5",
        productId = "5",
        title = "Sustainable Bamboo Cutlery Set",
        pricingModels = listOf(PricingModel.CASH, PricingModel.COINS, PricingModel.CASH_AND_COINS),
        cashPrice = "29.99",
        coinPrice = "100",
        combinedPrice = Pair("249", "500"),
        favoriteCount = "456",
        shareCount = "178",
        offerCount = "67",
        viewCount = "234",
        isConfirmPending = false,
        isShippingGuide = false
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=6",
        productId = "6",
        title = "Retro Polaroid Camera",
        pricingModels = listOf(PricingModel.CASH),
        cashPrice = "159.99",
        favoriteCount = "890",
        shareCount = "445",
        offerCount = "156",
        viewCount = "456",
        isConfirmPending = false,
        isShippingGuide = false
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=7",
        productId = "7",
        title = "Organic Cotton Yoga Mat",
        pricingModels = listOf(PricingModel.CASH_AND_COINS),
        combinedPrice = Pair("499", "800"),
        favoriteCount = "345",
        shareCount = "167",
        offerCount = "89",
        viewCount = "567",
        isConfirmPending = false,
        isShippingGuide = false
    ),
    ListItem(
        imageUrl = "https://picsum.photos/400/300?random=8",
        productId = "8",
        title = "Minimalist Wall Clock",
        pricingModels = listOf(PricingModel.COINS),
        coinPrice = "800",
        favoriteCount = "567",
        shareCount = "234",
        offerCount = "78",
        viewCount = "456",
        isConfirmPending = false,
        isShippingGuide = false
    ),
//    ProfileProductListing(
//        imageUrl = "https://picsum.photos/400/300?random=9",
//        productId = "9",
//        title = "Portable Solar Charger",
//        pricingModels = listOf(PricingModel.CASH, PricingModel.CASH_AND_COINS),
//        cashPrice = "79.99",
//        favoriteCount = "678",
//        shareCount = "389",
//        offerCount = "145",
//        combinedPrice = Pair("79.99", "1200")
//    ),
//    ProfileProductListing(
//        imageUrl = "https://picsum.photos/400/300?random=10",
//        productId = "10",
//        title = "Handcrafted Leather Wallet",
//        pricingModels = listOf(PricingModel.CASH),
//        cashPrice = "49.99",
//        coinPrice = null,
//        favoriteCount = "432",
//        shareCount = "198",
//        offerCount = "67"
//    )
)