package com.example.freeupcopy

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.freeupcopy.ui.presentation.cart_screen.CartScreen
import com.example.freeupcopy.ui.presentation.cash_screen.CashScreen
import com.example.freeupcopy.ui.presentation.coin_screen.CoinScreen
import com.example.freeupcopy.ui.presentation.community_screen.CommunityScreen
import com.example.freeupcopy.ui.presentation.home_screen.HomeScreen
import com.example.freeupcopy.ui.presentation.home_screen.componants.CustomNavigationBar
import com.example.freeupcopy.ui.presentation.inbox_screen.InboxScreen
import com.example.freeupcopy.ui.presentation.profile_screen.ProfileScreen
import com.example.freeupcopy.ui.presentation.search_screen.SearchScreen
import com.example.freeupcopy.ui.presentation.sell_screen.SellScreen
import com.example.freeupcopy.ui.presentation.sell_screen.componants.BrandScreen
import com.example.freeupcopy.ui.presentation.sell_screen.componants.CategoryScreen
import com.example.freeupcopy.ui.presentation.sell_screen.componants.ConditionScreen
import com.example.freeupcopy.ui.presentation.sell_screen.componants.ManufacturingScreen
import com.example.freeupcopy.ui.presentation.sell_screen.componants.WeightScreen
import com.example.freeupcopy.ui.presentation.wish_list.WishListScreen
import com.example.freeupcopy.ui.theme.FreeUpCopyTheme
import kotlinx.serialization.Serializable


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            FreeUpCopyTheme(darkTheme = false) {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    containerColor = MaterialTheme.colorScheme.surface,
                    bottomBar = {
                        currentRoute?.let { route ->
                            if (
                                route.hasRoute(Screen.HomeScreen::class)
                                || route.hasRoute(Screen.CommunityScreen::class)
                                || route.hasRoute(Screen.ProfileScreen::class)
                                || route.hasRoute(Screen.InboxScreen::class)
                            ) {
                                CustomNavigationBar(
                                    navController = navController,
                                    onHomeClick = {
                                        navController.navigate(Screen.HomeScreen) {
                                            popUpTo(Screen.HomeScreen) { inclusive = true }
                                        }
                                    },
                                    onCommunityClick = {
                                        navController.navigate(Screen.CommunityScreen) {
                                            popUpTo(Screen.HomeScreen) { inclusive = false }
                                        }
                                    },
                                    onWishListClick = {
                                        navController.navigate(Screen.WishListScreen) {
                                            popUpTo(Screen.HomeScreen) { inclusive = false }
                                        }
                                    },
                                    onProfileClick = {
                                        navController.navigate(Screen.ProfileScreen) {
                                            popUpTo(Screen.HomeScreen) { inclusive = false }
                                        }
                                    },
                                    onSellClick = {
                                        navController.navigate(Screen.SellScreen(null, null)) {
                                            popUpTo(Screen.HomeScreen) { inclusive = false }
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen,
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            ) + fadeIn(tween(300, 200))
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            ) + fadeOut(tween(250, 100))
                        }
                    ) {
                        composable<Screen.HomeScreen>(
                            enterTransition = { fadeIn(tween(700)) },
                            exitTransition = { fadeOut(tween(700)) }
                        ) {
                            HomeScreen(
                                innerPadding = innerPadding,
                                onSearchBarClick = {
                                    navController.navigate(Screen.SearchScreen)
                                },
                                onInboxClick = {
                                    navController.navigate(Screen.InboxScreen)
                                },
                                onCartClick = {
                                    navController.navigate(Screen.CartScreen)
                                },
                                onCoinClick = {
                                    navController.navigate(Screen.CoinScreen)
                                },
                                onCashClick = {
                                    navController.navigate(Screen.CashScreen)
                                }
                            )
                        }

                        composable<Screen.CommunityScreen>(
                            enterTransition = { fadeIn(tween(700)) },
                            exitTransition = { fadeOut(tween(700)) }
                        ) {
                            CommunityScreen()
                        }

                        composable<Screen.WishListScreen> {
                            WishListScreen()
                        }

                        composable<Screen.ProfileScreen>(
                            enterTransition = { fadeIn(tween(700)) },
                            exitTransition = { fadeOut(tween(700)) }
                        ) {
                            ProfileScreen()
                        }

                        composable<Screen.SellScreen>(
                            enterTransition = { fadeIn(tween(700)) },
                            exitTransition = { fadeOut(tween(700)) }
                        ) {
                            val selectedCategory = it.savedStateHandle.get<String>("selected_category")
                            val selectedWeight = it.savedStateHandle.get<String>("selected_weight")
                            val selectedBrand = it.savedStateHandle.get<String>("selected_brand")
                            val selectedCondition = it.savedStateHandle.get<String>("selected_condition")
                            val selectedCountry = it.savedStateHandle.get<String>("selected_country")
                            SellScreen(
                                onCategoryClick = {
                                    navController.navigate(Screen.CategoryScreen)
                                    selectedCategory ?: ""
                                },
                                onWeightClick = {
                                    navController.navigate(Screen.WeightScreen(selectedWeight = selectedWeight))
                                },
                                onConditionClick = {
                                    navController.navigate(Screen.ConditionScreen(selectedCondition = selectedCondition))
                                },
                                onBrandClick = {
                                    navController.navigate(Screen.BrandScreen(selectedBrand = selectedBrand))
                                    //selectedBrand ?: ""
                                },
                                onManufacturingClick = {
                                    navController.navigate(Screen.ManufacturingScreen(selectedCountry = selectedCountry))
                                },
                                selectedWeight = selectedWeight ?: "",
                                selectedCategory = selectedCategory ?: "",
                                selectedBrand = selectedBrand ?: "",
                                selectedCondition = selectedCondition ?: "",
                                selectedCountry = selectedCountry ?: "India"
                            )
                        }

                        composable<Screen.SearchScreen> {
                            SearchScreen(
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.InboxScreen>(
                            enterTransition = { fadeIn(tween(700)) },
                            exitTransition = { fadeOut(tween(700)) }
                        ) {
                            InboxScreen()
                        }

                        composable<Screen.CartScreen> {
                            CartScreen()
                        }

                        composable<Screen.CashScreen> {
                            CashScreen()
                        }

                        composable<Screen.CoinScreen> {
                            CoinScreen()
                        }
                        composable<Screen.CategoryScreen> {
                            CategoryScreen(
                                onCategoryClick = { s ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("selected_category", s)
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.WeightScreen> {
                            val args = it.toRoute<Screen.WeightScreen>()
                            WeightScreen(
                                selectedWeight = args.selectedWeight ?: "",
                                onWeightClick = { s ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("selected_weight", s)
                                    navController.popBackStack()
                                },
                                onClose = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.BrandScreen> {
                            val args = it.toRoute<Screen.BrandScreen>()
                            BrandScreen(
                                onClose = {
                                    navController.popBackStack()
                                },
                                navigatedBrand = args.selectedBrand ?: "",
                                onBrandClick = { s ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("selected_brand", s)
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.ConditionScreen> {
                            val args = it.toRoute<Screen.ConditionScreen>()
                            ConditionScreen(
                                onConditionClick = { s ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("selected_condition", s)
                                    navController.popBackStack()
                                },
                                onClose = {
                                    navController.popBackStack()
                                },
                                selectedCondition = args.selectedCondition ?: ""
                            )
                        }

                        composable<Screen.ManufacturingScreen> {
                            val args = it.toRoute<Screen.ManufacturingScreen>()
                            ManufacturingScreen(
                                onManufacturingClick = { s ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("selected_country", s)
                                    navController.popBackStack()
                                },
                                onClose = {
                                    navController.popBackStack()
                                },
                                manufacturingCountry = args.selectedCountry ?: "India"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object CommunityScreen : Screen()

    @Serializable
    data object WishListScreen : Screen()

    @Serializable
    data object ProfileScreen : Screen()

    @Serializable
    data class SellScreen(
        val selectedCategory: String?,
        val selectedWeight: String?,
    ) : Screen()

    @Serializable
    data object SearchScreen : Screen()

    @Serializable
    data object InboxScreen : Screen()

    @Serializable
    data object CartScreen : Screen()

    @Serializable
    data object CashScreen : Screen()

    @Serializable
    data object CoinScreen : Screen()

    @Serializable
    data object CategoryScreen : Screen()

    @Serializable
    data class WeightScreen(
        val selectedWeight: String?
    ) : Screen()

    @Serializable
    data class BrandScreen(
        val selectedBrand: String?
    ) : Screen()

    @Serializable
    data class ManufacturingScreen(
        val selectedCountry: String?
    ) : Screen()

    @Serializable
    data class ConditionScreen(
        val selectedCondition: String? = ""
    ) : Screen()
}

