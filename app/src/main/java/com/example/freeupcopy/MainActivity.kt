package com.example.freeupcopy

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.freeupcopy.domain.enums.SpecialOption
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.ui.navigation.CustomNavType
import com.example.freeupcopy.ui.navigation.Screen
import com.example.freeupcopy.ui.presentation.authentication_screen.connect_screen.ConnectScreen
import com.example.freeupcopy.ui.presentation.authentication_screen.forgot_password_screen.ForgotPasswordScreen
import com.example.freeupcopy.ui.presentation.authentication_screen.login_screen.LoginScreen
import com.example.freeupcopy.ui.presentation.authentication_screen.otp_screen.OtpVerificationScreen
import com.example.freeupcopy.ui.presentation.authentication_screen.signup_screen.SignUpScreen
import com.example.freeupcopy.ui.presentation.cart_screen.CartScreen
import com.example.freeupcopy.ui.presentation.cash_screen.CashScreen
import com.example.freeupcopy.ui.presentation.coin_screen.CoinScreen
import com.example.freeupcopy.ui.presentation.inbox_screen.InboxScreen
import com.example.freeupcopy.ui.presentation.main_screen.MainScreen
import com.example.freeupcopy.ui.presentation.product_screen.ProductScreen
import com.example.freeupcopy.ui.presentation.profile_screen.edit_profile_screen.EditProfileScreen
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.PostedProductsScreen
import com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen.SellerProfileScreen
import com.example.freeupcopy.ui.presentation.reply_screen.ReplyScreen
import com.example.freeupcopy.ui.presentation.search_screen.SearchScreen
import com.example.freeupcopy.ui.presentation.sell_screen.SellScreen
import com.example.freeupcopy.ui.presentation.sell_screen.SellViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.advance_setting_screen.AdvanceSettingScreen
import com.example.freeupcopy.ui.presentation.sell_screen.brand_screen.BrandScreen
import com.example.freeupcopy.ui.presentation.sell_screen.category_screen.CategoryScreen
import com.example.freeupcopy.ui.presentation.sell_screen.condition_screen.ConditionScreen
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen.AddLocationScreen
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.LocationScreen
import com.example.freeupcopy.ui.presentation.sell_screen.manufacturing_screen.ManufacturingScreen
import com.example.freeupcopy.ui.presentation.sell_screen.price_screen.PriceScreen
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.WeightScreen
import com.example.freeupcopy.ui.presentation.wish_list.WishListScreen
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.utils.sharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            SwapGoTheme(darkTheme = false) {

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    containerColor = MaterialTheme.colorScheme.surface,
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen,
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
                        navigation(
                            startDestination = "sell_screen",
                            route = "sell_graph"
                        ) {
                            composable<Screen.SellScreen>(
                                enterTransition = { fadeIn(tween(700)) },
                                exitTransition = { fadeOut(tween(700)) }
                            ) {
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)

                                SellScreen(
                                    sellViewModel = sellViewModel,
                                    onCategoryClick = {
                                        navController.navigate(Screen.CategoryScreen)
                                    },
                                    onWeightClick = { selectedWeightType ->
                                        navController.navigate(Screen.WeightScreen(selectedWeightType = selectedWeightType))
                                    },
                                    onConditionClick = { selectedCondition ->
                                        navController.navigate(Screen.ConditionScreen(selectedCondition = selectedCondition))
                                    },
//                                    onBrandClick = {
//                                        navController.navigate(Screen.BrandScreen(selectedBrand = selectedBrand))
//                                    },
                                    onManufacturingClick = { manufacturingCountry ->
                                        navController.navigate(Screen.ManufacturingScreen(selectedCountry = manufacturingCountry))
                                    },
                                    onLocationClick = { selectedLocation ->
                                        navController.navigate(Screen.LocationScreen(selectedLocation = selectedLocation))
                                    },
                                    onAdvanceSettingClick = {
                                        navController.navigate(Screen.GstScreen)
                                    },
                                    onPriceClick = { price ->
                                        navController.navigate(Screen.PriceScreen(price))
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    },
                                    onSpecificationClick = { option ->
                                        when(option) {
                                            SpecialOption.FABRIC -> {

                                            }
                                            SpecialOption.COLOUR -> {

                                            }
                                            SpecialOption.OCCASION -> {

                                            }
                                            SpecialOption.BRAND -> {

                                            }
                                            SpecialOption.MODEL_NUMBER -> {

                                            }
                                            SpecialOption.INCLUDES -> {

                                            }
                                            SpecialOption.STORAGE_CAPACITY -> {

                                            }
                                            SpecialOption.RAM -> {

                                            }
                                            SpecialOption.BATTERY_CAPACITY -> {

                                            }
                                            SpecialOption.MOBILE_NETWORK -> {

                                            }
                                            SpecialOption.SCREEN_SIZE -> {

                                            }
                                            SpecialOption.SIM_TYPE -> {

                                            }
                                            SpecialOption.WARRANTY -> {

                                            }
                                            SpecialOption.SIZE -> {

                                            }
                                            SpecialOption.SHAPE -> {

                                            }
                                            SpecialOption.LENGTH -> {

                                            }
                                            SpecialOption.EXPIRATION_DATE -> {

                                            }
                                        }
                                    }
                                )
                            }

                            composable<Screen.CategoryScreen> {
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)
                                CategoryScreen(
                                    sellViewModel = sellViewModel,
                                    onCategoryClick = {
                                        navController.popBackStack()
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable<Screen.WeightScreen> {
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)
                                val args = it.toRoute<Screen.WeightScreen>()
                                WeightScreen(
                                    selectedWeightType = args.selectedWeightType ?: "",
                                    onWeightClick = { navController.popBackStack() },
                                    onClose = { navController.popBackStack() },
                                    sellViewModel = sellViewModel
                                )
                            }

                            composable<Screen.ConditionScreen> {
                                val args = it.toRoute<Screen.ConditionScreen>()
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)

                                ConditionScreen(
                                    sellViewModel = sellViewModel,
                                    onConditionClick = { navController.popBackStack() },
                                    onClose = { navController.popBackStack() },
                                    selectedCondition = args.selectedCondition ?: ""
                                )
                            }

                            composable<Screen.ManufacturingScreen> {
                                val args = it.toRoute<Screen.ManufacturingScreen>()
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)

                                ManufacturingScreen(
                                    sellViewModel = sellViewModel,
                                    onManufacturingClick = { navController.popBackStack() },
                                    onClose = { navController.popBackStack() },
                                    manufacturingCountry = args.selectedCountry ?: "India"
                                )
                            }


                            composable<Screen.BrandScreen> {
                                val args = it.toRoute<Screen.BrandScreen>()
                                BrandScreen(
                                    navigatedBrand = args.selectedBrand ?: "",
                                    onBrandClick = { s ->
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("selected_brand", s)
                                        navController.popBackStack()
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable<Screen.LocationScreen> {
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)
                                val args = it.toRoute<Screen.LocationScreen>()

                                LocationScreen(
                                    sellViewModel = sellViewModel,
                                    onNewLocationClick = {
                                        navController.navigate(Screen.AddLocationScreen)
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    },
                                    selectedLocation = args.selectedLocation ?: 0,
                                    onLocationClick = {
                                        navController.popBackStack()
                                    },
                                )
                            }

                            composable<Screen.AddLocationScreen> {
                                AddLocationScreen(
                                    onLocationAdded = {
                                        navController.popBackStack()
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable<Screen.GstScreen> {
                                AdvanceSettingScreen(
                                    onClose = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable<Screen.PriceScreen>(
                                typeMap = mapOf(
                                    typeOf<Price?>() to CustomNavType.PriceType
                                )
                            ) {
                                val args = it.toRoute<Screen.PriceScreen>()
                                val sellViewModel = it.sharedViewModel<SellViewModel>(navController = navController)
                                PriceScreen(
                                    price =  args.price ?: Price(
                                        mrp = "",
                                        pricingModel = emptyList(),
                                        sellingCoin = "",
                                        sellingCash = "",
                                        sellingCashCoin = null,
                                        earningCash = "",
                                        earningCoin = "",
                                        earningCashCoin = null
                                    ),
                                    onClose = { navController.popBackStack() },
                                    onConfirmClick = { navController.popBackStack() },
                                    sellViewModel = sellViewModel
                                )
                            }
                        }

                        composable<Screen.WishListScreen> {
                            WishListScreen()
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

                        composable<Screen.SignUpScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            }

                        ) {
                            SignUpScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onCloseClick = {
                                    navController.navigate(Screen.MainScreen) {
                                        popUpTo(Screen.MainScreen) { inclusive = true }
                                    }
                                },
                                onLoginClick = {
                                    navController.navigate(Screen.LoginScreen) {
                                        popUpTo(Screen.ConnectScreen) { inclusive = false }
                                    }
                                },
                                onSuccessfulSignUp = { email ->
                                    navController.navigate(Screen.OtpScreen(email)) {
                                       // popUpTo(Screen.OtpScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Screen.LoginScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            }
                        ) {
                            LoginScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onCloseClick = {
                                    navController.navigate(Screen.MainScreen) {
                                        popUpTo(Screen.MainScreen) { inclusive = true }
                                    }
                                },
                                onSignUpClick = {
                                    navController.navigate(Screen.SignUpScreen) {
                                        popUpTo(Screen.ConnectScreen) { inclusive = false }
                                    }
                                },
                                onForgotPasswordClick = {
                                    navController.navigate(Screen.ForgotPasswordScreen)
                                },
                                onSuccessfulLogin = {
                                    navController.navigate(Screen.MainScreen) {
                                        popUpTo(Screen.MainScreen) { inclusive = true }
                                    }
                                },
                            )
                        }

                        composable<Screen.ConnectScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            },
//                            popExitTransition = {
//                                slideOutOfContainer(
//                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
//                                    animationSpec = tween(700)
//                                )
//                            }

                        ) {
                            ConnectScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onLoginClick = {
                                    navController.navigate(Screen.LoginScreen)
                                },
                                onSignUpClick = {
                                    navController.navigate(Screen.SignUpScreen)
                                }
                            )
                        }

                        composable<Screen.ForgotPasswordScreen>(
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(700)
                                )
                            }
                        ) {
                            ForgotPasswordScreen(
                                onBackClick = { navController.popBackStack() },
                                onSuccessfulOptSent = {
                                    //navController.navigate(Screen.OtpScreen)
                                }
                            )
                        }

                        composable<Screen.OtpScreen> {
                            val args = it.toRoute<Screen.OtpScreen>()
                            OtpVerificationScreen(
                                email = args.email ?: "",
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onSuccessfulVerification = {
                                    navController.navigate(Screen.LoginScreen) {
                                        popUpTo(Screen.ConnectScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Screen.ProductScreen> {
                            ProductScreen(
                                onReplyClick = {
                                    navController.navigate(Screen.ReplyScreen)
                                }
                            )
                        }

                        composable<Screen.ReplyScreen> {
                            ReplyScreen(
                                onClose = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.PostedProductsScreen> {
                            PostedProductsScreen(
                                onBack = {
                                    navController.popBackStack()
                                },
                                onProductClick = {
                                    navController.navigate(Screen.ProductScreen)
                                },
                            )
                        }

                        composable<Screen.SellerProfileScreen> {
                            SellerProfileScreen(
                                onBack = {
                                    navController.popBackStack()
                                },
                                onEditProfile = {
                                    navController.navigate(Screen.EditProfileScreen)
                                }
                            )
                        }

                        composable<Screen.EditProfileScreen> {
                            EditProfileScreen(
                                onClose = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.MainScreen>(
                            enterTransition = { fadeIn(tween(700)) },
                            exitTransition = { fadeOut(tween(700)) }
                        ) {
                            MainScreen(
                                onNavigate = { screen ->
                                    navController.navigate(screen)
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}

