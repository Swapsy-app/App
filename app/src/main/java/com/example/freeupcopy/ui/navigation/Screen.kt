package com.example.freeupcopy.ui.navigation

import com.example.freeupcopy.domain.model.Price
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object WishListScreen : Screen()

    @Serializable
    data object SellScreen : Screen()

    //Sell Screens
    @Serializable
    data object SearchScreen : Screen()
    @Serializable
    data object ProductListingScreen : Screen()

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
    data class LocationScreen(val selectedLocation: Int?) : Screen()
    @Serializable
    data object AddLocationScreen : Screen()
    @Serializable
    data object GstScreen : Screen()
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
    data object ProductScreen : Screen()
    @Serializable
    data object ReplyScreen : Screen()

    //Profile Screens
    @Serializable
    data object PostedProductsScreen : Screen()
    @Serializable
    data object SellerProfileScreen : Screen()
    @Serializable
    data object EditProfileScreen : Screen()

    @Serializable
    data object MainScreen : Screen()
}
