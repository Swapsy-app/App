package com.example.freeupcopy

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.example.freeupcopy.ui.presentation.wish_list.WishListScreen
import com.example.freeupcopy.ui.theme.FreeUpCopyTheme
import kotlinx.serialization.Serializable


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreeUpCopyTheme {

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
                            enterTransition = {
                                fadeIn(tween(700))
                            },
                            exitTransition = {
                                fadeOut(tween(700))
                            }
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
                            enterTransition = {
                                fadeIn(tween(700))
                            },
                            exitTransition = {
                                fadeOut(tween(700))
                            }
                        ) {
                            CommunityScreen()
                        }

                        composable<Screen.WishListScreen> {
                            WishListScreen()
                        }

                        composable<Screen.ProfileScreen>(
                            enterTransition = {
                                fadeIn(tween(700))
                            },
                            exitTransition = {
                                fadeOut(tween(700))
                            }
                        ) {
                            ProfileScreen()
                        }

                        composable<Screen.SellScreen>(
                            enterTransition = {
                                fadeIn(tween(700))
                            },
                            exitTransition = {
                                fadeOut(tween(700))
                            }
                        ) {
                            SellScreen()
                        }

                        composable<Screen.SearchScreen> {
                            SearchScreen(
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.InboxScreen> {
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
    data object SellScreen : Screen()

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
}