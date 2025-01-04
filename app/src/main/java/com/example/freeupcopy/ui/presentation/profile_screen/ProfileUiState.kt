package com.example.freeupcopy.ui.presentation.profile_screen

import com.example.freeupcopy.domain.enums.PricingModel
import com.example.freeupcopy.domain.model.ProfileProductListing

data class ProfileUiState(
    val profilePhotoUrl: String = "",
    val userName: String = "sk_sahil_islam",
    val userRating: String = "4.52",
    val cashBalance: String = "120",
    val coinBalance: String = "2000",
    val isPackingMaterialOn: Boolean = true,
    val isBundleOffersOn: Boolean = false,
    val isOnlineModeOn: Boolean = true,

    val listedCount: String = "5",
    val pendingCount: String = "2",
    val deliveredCount: String = "3",
    val isListedActionRequired: Boolean = true,
    val isPendingActionRequired: Boolean = false,
    val isDeliveredActionRequired: Boolean = false,

    val profileProductListings: List<ProfileProductListing> = listOf(
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=1",
            productId = "1",
            title = "Vintage Leather Backpack",
            pricingModels = listOf(PricingModel.CASH, PricingModel.CASH_AND_COINS),
            cashPrice = "89.99",
            combinedPrice = Pair("89", "150"),
            favoriteCount = "342",
            shareCount = "156",
            offerCount = "45"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=2",
            productId = "2",
            title = "Wireless Noise-Canceling Headphones",
            pricingModels = listOf(PricingModel.CASH),
            cashPrice = "199.99",
            favoriteCount = "567",
            shareCount = "234",
            offerCount = "78"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=3",
            productId = "3",
            title = "Smart Fitness Watch",
            pricingModels = listOf(PricingModel.CASH_AND_COINS),
            combinedPrice = Pair("189", "450"),
            favoriteCount = "789",
            shareCount = "445",
            offerCount = "123"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=4",
            productId = "4",
            title = "Handmade Ceramic Mug Set",
            pricingModels = listOf(PricingModel.COINS),
            coinPrice = "500",
            favoriteCount = "234",
            shareCount = "89",
            offerCount = "34"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=5",
            productId = "5",
            title = "Sustainable Bamboo Cutlery Set",
            pricingModels = listOf(PricingModel.CASH, PricingModel.COINS, PricingModel.CASH_AND_COINS),
            cashPrice = "29.99",
            coinPrice = "100",
            combinedPrice = Pair("249", "500"),
            favoriteCount = "456",
            shareCount = "178",
            offerCount = "67"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=6",
            productId = "6",
            title = "Retro Polaroid Camera",
            pricingModels = listOf(PricingModel.CASH),
            cashPrice = "159.99",
            favoriteCount = "890",
            shareCount = "445",
            offerCount = "156"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=7",
            productId = "7",
            title = "Organic Cotton Yoga Mat",
            pricingModels = listOf(PricingModel.CASH_AND_COINS),
            combinedPrice = Pair("499", "800"),
            favoriteCount = "345",
            shareCount = "167",
            offerCount = "89"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=8",
            productId = "8",
            title = "Minimalist Wall Clock",
            pricingModels = listOf(PricingModel.COINS),
            coinPrice = "800",
            favoriteCount = "567",
            shareCount = "234",
            offerCount = "78"
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=9",
            productId = "9",
            title = "Portable Solar Charger",
            pricingModels = listOf(PricingModel.CASH, PricingModel.CASH_AND_COINS),
            cashPrice = "79.99",
            favoriteCount = "678",
            shareCount = "389",
            offerCount = "145",
            combinedPrice = Pair("79.99", "1200")
        ),
        ProfileProductListing(
            imageUrl = "https://picsum.photos/400/300?random=10",
            productId = "10",
            title = "Handcrafted Leather Wallet",
            pricingModels = listOf(PricingModel.CASH),
            cashPrice = "49.99",
            coinPrice = null,
            favoriteCount = "432",
            shareCount = "198",
            offerCount = "67"
        )
    )
)
