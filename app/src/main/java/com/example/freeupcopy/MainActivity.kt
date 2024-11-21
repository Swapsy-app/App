package com.example.freeupcopy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.freeupcopy.ui.presentation.favorite_screen.FavoriteScreen
import com.example.freeupcopy.ui.presentation.home_screen.HomeScreen
import com.example.freeupcopy.ui.presentation.home_screen.componants.CustomNavigationBar
import com.example.freeupcopy.ui.presentation.profile_screen.ProfileScreen
import com.example.freeupcopy.ui.presentation.sell_screen.SellScreen
import com.example.freeupcopy.ui.presentation.wish_list.WishListScreen
import com.example.freeupcopy.ui.theme.FreeUpCopyTheme
import kotlinx.serialization.Serializable


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreeUpCopyTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        currentRoute?.let { route ->
                            if(!route.hasRoute(Screen.ScreenE::class)){
                                CustomNavigationBar(
                                    navController = navController,
                                    onHomeClick = {
                                        navController.navigate(Screen.ScreenA) {
                                            popUpTo(Screen.ScreenA) { inclusive = true }
                                        }
                                    },
                                    onWishListClick = {
                                        navController.navigate(Screen.ScreenB) {
                                            popUpTo(Screen.ScreenA) { inclusive = false }
                                        }
                                    },
                                    onNotificationClick = {
                                        navController.navigate(Screen.ScreenC) {
                                            popUpTo(Screen.ScreenA) { inclusive = false }
                                        }
                                    },
                                    onProfileClick = {
                                        navController.navigate(Screen.ScreenD) {
                                            popUpTo(Screen.ScreenA) { inclusive = false }
                                        }
                                    }
                                )
                            }
                        }
                    },
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ScreenA
                    ) {
                        composable<Screen.ScreenA> {
                            HomeScreen()
                        }

                        composable<Screen.ScreenB> {
                            WishListScreen()
                        }

                        composable<Screen.ScreenC> {
                            FavoriteScreen()
                        }

                        composable<Screen.ScreenD> {
                            ProfileScreen()
                        }

                        composable<Screen.ScreenE> {
                            SellScreen()
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
    data object ScreenA : Screen()

    @Serializable
    data object ScreenB : Screen()

    @Serializable
    data object ScreenC : Screen()

    @Serializable
    data object ScreenD : Screen()

    @Serializable
    data object ScreenE : Screen()
}