package com.example.freeupcopy.ui.navigation

import com.example.freeupcopy.domain.enums.Settings
import com.example.freeupcopy.domain.model.Price
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object WishListScreen : Screen()

    @Serializable
    data object SellScreen : Screen()

    @Serializable
    data object CategorySelectScreen : Screen()

    //Sell Screens
    @Serializable
    data object SearchScreen : Screen()
    @Serializable
    data class ProductListingScreen(
        val query: String,
        val priceType: String? = null,
        val maxPriceCash: String? = null,
        val maxPriceCoin: String? = null,
        val primaryCategory: String? = null,
        val secondaryCategory: String? = null,
        val tertiaryCategory: String? = null,
    ) : Screen()

    @Serializable
    data object InboxScreen : Screen()

    @Serializable
    data object CartScreen : Screen()

    @Serializable
    data object CashScreen : Screen()

    @Serializable
    data object CoinScreen : Screen()

    //Sell Screens
    @Serializable
    data object CategoryScreen : Screen()
    @Serializable
    data class WeightScreen(val selectedWeightType: String?) : Screen()
    @Serializable
    data class BrandScreen(val selectedBrand: String?) : Screen()
    @Serializable
    data class ManufacturingScreen(val selectedCountry: String?) : Screen()
    @Serializable
    data class ConditionScreen(val selectedCondition: String?) : Screen()
    @Serializable
    data class LocationScreen(val selectedLocationId: String?) : Screen()
    @Serializable
    data object AddLocationScreen : Screen()
    @Serializable
    data class GstScreen(val gst: String?): Screen()
    @Serializable
    data class PriceScreen(val price: Price?) : Screen()

    //Authentication Screens
    @Serializable
    data object ConnectScreen : Screen()
    @Serializable
    data object SignUpScreen : Screen()
    @Serializable
    data object LoginScreen : Screen()
    @Serializable
    data object ForgotPasswordScreen : Screen()
    @Serializable
    data class OtpScreen(val email: String?) : Screen()

    //Product Screens
    @Serializable
    data class ProductScreen(val productId: String) : Screen()
    @Serializable
    data class ReplyScreen(val commentId: String?, val replyId: String?) : Screen()

    //Profile Screens
    @Serializable
    data object PostedProductsScreen : Screen()
    @Serializable
    data class SellerProfileScreen(val userId: String?) : Screen()
    @Serializable
    data class EditProfileScreen(
        val profilePhotoUrl: String?,
        val userFullName: String?,
        val username: String?,
        val aboutMe: String?,
        val gender: String?,
        val occupation: String?
    ) : Screen()

    @Serializable
    data object MainScreen : Screen()

    @Serializable
    data class GalleryScreen(val numberOfUploadedImages: Int?): Screen()

    @Serializable
    data object SettingsScreen : Screen()

    @Serializable
    data class SecondarySettingsScreen(val screenType: Settings?) : Screen()
}
