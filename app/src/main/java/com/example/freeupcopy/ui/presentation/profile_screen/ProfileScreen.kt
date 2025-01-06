package com.example.freeupcopy.ui.presentation.profile_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.ui.presentation.profile_screen.componants.BalanceSection
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ProfileBanner
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ProfileTopBar
import com.example.freeupcopy.ui.presentation.profile_screen.componants.SellerHub
import com.example.freeupcopy.ui.presentation.profile_screen.componants.YourPostedProducts
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    onPostedProductClick: () -> Unit,
    onViewProfileClick: () -> Unit,
    innerPadding: PaddingValues
) {
    val state by profileViewModel.state.collectAsState()


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(bottom = innerPadding.calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            ProfileTopBar(
                profilePhotoUrl = state.profilePhotoUrl,
                userName = state.userName,
                userRating = state.userRating,
                onSettingsClick = {

                },
                onViewProfileClick = {
                    onViewProfileClick()
                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                item {
                    ProfileBanner(
                        onClick = {},
                        onWishlistClick = {},
                        onYourOrdersClick = {},
                        onYourOffersClick = {}
                    )
                }
                item {
                    BalanceSection(
                        modifier = Modifier.padding(top = 5.dp),
                        cashBalance = state.cashBalance,
                        coinBalance = state.coinBalance
                    )
                }

                item {
                    YourPostedProducts(
//                        modifier = Modifier.padding(vertical = 5.dp)
                        listedCount = state.listedCount,
                        pendingCount = state.pendingCount,
                        deliveredCount = state.deliveredCount,
                        isListedActionRequired = state.isListedActionRequired,
                        isPendingActionRequired = state.isPendingActionRequired,
                        isDeliveredActionRequired = state.isDeliveredActionRequired,
                        onDeliveredClick = {},
                        onListedClick = {},
                        onPendingClick = {},
                        onPostedProductClick = onPostedProductClick
                    )
                }

                item {
                    SellerHub(
                        modifier = Modifier.padding(vertical = 5.dp),
                        isCodOn = state.isPackingMaterialOn,
                        isBundleOffersOn = state.isBundleOffersOn,
                        isOnlineModeOn = state.isOnlineModeOn,
                        onPackingMaterialClick = {},
                        onBundleOffersClick = {},
                        onRanksClick = {},
                        onOnlineModeClick = {},
                        onShippingGuideClick = {}
                    )
                }

//                stickyHeader {
//                    ProfileProductTabRow(
//                        modifier = Modifier.fillMaxWidth(),
//                        selectedTabIndex = currentIndex,
//                        onClick = { index ->
//                            currentIndex = index
//                        },
//                        listedProductDetails = ListProductDetails(
//                            totalCount = "12",
//                            subCategories = listOf(
//                                TabItemSubCategory("Active", "8"),
//                                TabItemSubCategory("Drafts", "0"),
//                                TabItemSubCategory("Under Review", "1"),
//                                TabItemSubCategory("Unavailable", "3")
//                            )
//                        ),
//                        soldProductDetails = SoldProductDetails(
//                            totalCount = "5",
//                            subCategories = listOf(
//                                TabItemSubCategory("Order Received", "1"),
//                                TabItemSubCategory("Shipped", "1"),
//                                TabItemSubCategory("Issues", "3"),
//                            )
//                        ),
//                        deliveredProductDetails = DeliveredProductDetails(
//                            totalCount = "3",
//                            subCategories = listOf(
//                                TabItemSubCategory("Completed", "3"),
//                                TabItemSubCategory("Cancelled", "0")
//                            )
//                        )
//                    )
//                }
//
//                when (currentIndex) {
//
//                    0 -> {
//                        items(state.profileProductListings) { item ->
//                            ProfileProductListingItem(
//                                modifier = Modifier.padding(horizontal = 8.dp),
//                                productId = item.productId,
//                                title = item.title,
//                                pricingModels = item.pricingModels,
//                                imageUrl = item.imageUrl,
//                                onClick = { },
//                                cashPrice = item.cashPrice,
//                                coinPrice = item.coinPrice,
//                                combinedPrice = item.combinedPrice,
//                                favoriteCount = item.favoriteCount,
//                                shareCount = item.shareCount,
//                                offerCount = item.offerCount,
//                            )
//                        }
//                    }
//
//                    1 -> {
//                        items(state.profileProductListings) { item ->
//                            Text(
//                                text = item.title,
//                                modifier = Modifier.padding(8.dp)
//                            )
//                        }
//                    }
//
//                    2 -> {
//                        items(state.profileProductListings) { item ->
//                            ProfileProductListingItem(
//                                modifier = Modifier.padding(horizontal = 8.dp),
//                                productId = item.productId,
//                                title = item.title,
//                                pricingModels = item.pricingModels,
//                                imageUrl = item.imageUrl,
//                                onClick = { },
//                                cashPrice = item.cashPrice,
//                                coinPrice = item.coinPrice,
//                                combinedPrice = item.combinedPrice,
//                                favoriteCount = item.favoriteCount,
//                                shareCount = item.shareCount,
//                                offerCount = item.offerCount,
//                            )
//                        }
//                    }
//                }
                item {
                    Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    SwapGoTheme {
        ProfileScreen(
            innerPadding = PaddingValues(0.dp),
            onPostedProductClick = {},
            onViewProfileClick = {}
        )
    }
}