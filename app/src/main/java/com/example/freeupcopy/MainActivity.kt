package com.example.freeupcopy

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.freeupcopy.data.pref.SwapGoPref
import com.example.freeupcopy.domain.enums.Settings
import com.example.freeupcopy.domain.enums.SpecialOption
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.ui.navigation.AuthState
import com.example.freeupcopy.ui.navigation.AuthStateManager
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
import com.example.freeupcopy.ui.presentation.home_screen.CategorySelectScreen
import com.example.freeupcopy.ui.presentation.inbox_screen.InboxScreen
import com.example.freeupcopy.ui.presentation.main_screen.MainScreen
import com.example.freeupcopy.ui.presentation.product_listing.ProductListing
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
import com.example.freeupcopy.ui.presentation.sell_screen.gallery_screen.CustomGalleryScreen
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.add_location_screen.AddLocationScreen
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.LocationScreen
import com.example.freeupcopy.ui.presentation.sell_screen.manufacturing_screen.ManufacturingScreen
import com.example.freeupcopy.ui.presentation.sell_screen.price_screen.PriceScreen
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.WeightScreen
import com.example.freeupcopy.ui.presentation.setting.SettingsScreen
import com.example.freeupcopy.ui.presentation.setting_screen.SecondarySettingsScreen
import com.example.freeupcopy.ui.presentation.wish_list.WishListScreen
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.utils.sharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authStateManager: AuthStateManager

    @Inject
    lateinit var swapGoPref: SwapGoPref

    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMaterial3Api
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load refresh token during splash screen
        var isTokenLoaded = false
        var refreshToken: String? = null

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // Keep splash screen visible until both token and user data are loaded
                if (!isTokenLoaded) {
                    lifecycleScope.launch {
                        swapGoPref.getRefreshToken().collect { token ->
                            refreshToken = token
                            isTokenLoaded = true
                            viewModel.setRefreshToken(token)
                        }
                    }
                    return@setKeepOnScreenCondition true
                }

                // Also wait for user data to be loaded
                !viewModel.isUserDataLoaded.value
            }
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            SwapGoTheme(darkTheme = false) {
                // Get the token from ViewModel
                val token by viewModel.refreshToken.collectAsState()
                val user by viewModel.user.collectAsState()

                // Add state for login bottom sheet
                val showLoginBottomSheet = remember { mutableStateOf(false) }

                val navController = rememberNavController()
                val authState by authStateManager.authState.collectAsState()

                val scope = rememberCoroutineScope()

                // Monitor auth state changes
                LaunchedEffect(authState) {
                    when (authState) {
                        AuthState.UNAUTHENTICATED -> {
                            // Show login bottom sheet instead of direct navigation
                            showLoginBottomSheet.value = true
                        }

                        else -> { /* No action needed */ }
                    }
                }

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
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)

                                val uploadedImages =
                                    it.savedStateHandle.get<List<String>>("uploaded_images")
                                        ?: emptyList()
                                val uploadedVideo =
                                    it.savedStateHandle.get<String>("uploaded_video") ?: ""

                                SellScreen(
                                    uploadedImages = uploadedImages,
                                    uploadedVideo = uploadedVideo,
                                    sellViewModel = sellViewModel,
                                    onCategoryClick = {
                                        navController.navigate(Screen.CategoryScreen)
                                    },
                                    onWeightClick = { selectedWeightType ->
                                        navController.navigate(
                                            Screen.WeightScreen(
                                                selectedWeightType = selectedWeightType
                                            )
                                        )
                                    },
                                    onConditionClick = { selectedCondition ->
                                        navController.navigate(
                                            Screen.ConditionScreen(
                                                selectedCondition = selectedCondition
                                            )
                                        )
                                    },
//                                    onBrandClick = {
//                                        navController.navigate(Screen.BrandScreen(selectedBrand = selectedBrand))
//                                    },
                                    onManufacturingClick = { manufacturingCountry ->
                                        navController.navigate(
                                            Screen.ManufacturingScreen(
                                                selectedCountry = manufacturingCountry
                                            )
                                        )
                                    },
                                    onLocationClick = { selectedLocationId ->
                                        navController.navigate(
                                            Screen.LocationScreen(
                                                selectedLocationId = selectedLocationId
                                            )
                                        )
                                    },
                                    onAdvanceSettingClick = { gst ->
                                        navController.navigate(Screen.GstScreen(gst = gst))
                                    },
                                    onAddImageVideoClick = { numberOfUploadedImages ->
                                        navController.navigate(
                                            Screen.GalleryScreen(
                                                numberOfUploadedImages
                                            )
                                        )
                                    },
                                    onPriceClick = { price ->
                                        navController.navigate(Screen.PriceScreen(price))
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    },
                                    onSpecificationClick = { option ->
                                        when (option) {
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
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)
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

                            composable<Screen.GalleryScreen> {
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)
                                val args = it.toRoute<Screen.GalleryScreen>()
                                CustomGalleryScreen(
                                    sellViewModel = sellViewModel,
                                    numberOfUploadedImages = args.numberOfUploadedImages ?: 0,
                                    onClose = { uploadedImages, uploadedVideo ->
                                        if (uploadedImages.isNotEmpty()) {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("uploaded_images", uploadedImages)
                                        }
                                        if (uploadedVideo?.isNotEmpty() == true) {
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("uploaded_video", uploadedVideo)
                                        }
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable<Screen.WeightScreen> {
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)
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
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)

                                ConditionScreen(
                                    sellViewModel = sellViewModel,
                                    onConditionClick = { navController.popBackStack() },
                                    onClose = { navController.popBackStack() },
                                    selectedCondition = args.selectedCondition ?: ""
                                )
                            }

                            composable<Screen.ManufacturingScreen> {
                                val args = it.toRoute<Screen.ManufacturingScreen>()
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)

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
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)
                                val args = it.toRoute<Screen.LocationScreen>()

                                LocationScreen(
                                    sellViewModel = sellViewModel,
                                    onNewLocationClick = {
                                        navController.navigate(Screen.AddLocationScreen)
                                    },
                                    onClose = {
                                        navController.popBackStack()
                                    },
                                    selectedLocationId = args.selectedLocationId ?: "",
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
                                val args = it.toRoute<Screen.GstScreen>()
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)
                                AdvanceSettingScreen(
                                    onClose = {
                                        navController.popBackStack()
                                    },
                                    sellViewModel = sellViewModel,
                                    gst = args.gst ?: "",
                                    onSuccessfulUpdate = {
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
                                val sellViewModel =
                                    it.sharedViewModel<SellViewModel>(navController = navController)
                                PriceScreen(
                                    price = args.price ?: Price(
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
                            WishListScreen(
                                onProductClick = {
                                    navController.navigate(Screen.ProductScreen(it))
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.SearchScreen> {
                            SearchScreen(
                                onBack = {
                                    navController.popBackStack()
                                },
                                onSearch = { query ->
                                    navController.navigate(Screen.ProductListingScreen(query = query.trim()))
                                },
                                onRecentProductClick = { productId ->
                                    navController.navigate(Screen.ProductScreen(productId = productId))
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
                            CashScreen(
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable<Screen.CoinScreen> {
                            CoinScreen(
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
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
                                        popUpTo(Screen.ConnectScreen) { inclusive = true }
                                    }
//                                    navController.popBackStack(
//                                        route = Screen.ConnectScreen,
//                                        inclusive = true
//                                    )
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
//                                    navController.navigate(Screen.LoginScreen) {
//                                        popUpTo(Screen.ConnectScreen) { inclusive = true }
//                                    }
                                    navController.navigate(Screen.MainScreen) {
                                        popUpTo(Screen.MainScreen) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Screen.ProductScreen> {
                            ProductScreen(
                                navController = navController,
                                onReplyClickComment = { commentId ->
                                    navController.navigate(
                                        Screen.ReplyScreen(
                                            commentId = commentId,
                                            replyId = null
                                        )
                                    )
                                },
                                onReplyClickReply = { commentId, replyId ->
                                    navController.navigate(
                                        Screen.ReplyScreen(
                                            commentId = commentId,
                                            replyId = replyId
                                        )
                                    )
                                },
                                onBack = {
                                    navController.popBackStack()
                                },
                                onUserClick = { userId ->
                                    navController.navigate(Screen.SellerProfileScreen(userId))
                                }
                            )
                        }

                        composable<Screen.ReplyScreen> {
                            ReplyScreen(
                                onClose = {
                                    navController.popBackStack()
                                },
                                onReplyPosted = { newReply ->
                                    // assume `newReply` is the Reply object returned from your API
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("new_reply", newReply)
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
                                    navController.navigate(Screen.ProductScreen(""))
                                },
                            )
                        }

                        composable<Screen.SellerProfileScreen> {
                            SellerProfileScreen(
                                onBack = {
                                    navController.popBackStack()
                                },
                                onEditProfile = { profilePhotoUrl, userFullName, username, aboutMe, gender, occupation ->
                                    navController.navigate(
                                        Screen.EditProfileScreen(
                                            profilePhotoUrl = profilePhotoUrl,
                                            userFullName = userFullName,
                                            username = username,
                                            aboutMe = aboutMe,
                                            gender = gender,
                                            occupation = occupation
                                        )
                                    )
                                }
                            )
                        }

                        composable<Screen.EditProfileScreen> {
                            val args = it.toRoute<Screen.EditProfileScreen>()
                            EditProfileScreen(
                                profilePhotoUrl = args.profilePhotoUrl ?: "",
                                userFullName = args.userFullName ?: "",
                                username = args.username ?: "",
                                userBio = args.aboutMe ?: "",
                                userGender = args.gender ?: "",
                                userOccupation = args.occupation ?: "",
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
                                token = token,
                                onShowLoginBottomSheet = {
                                    showLoginBottomSheet.value = true
                                },
                                onNavigate = { screen ->
                                    navController.navigate(screen)
                                }
                            )
                        }

                        composable<Screen.ProductListingScreen> {
                            val args = it.toRoute<Screen.ProductListingScreen>()
                            val query = args.query ?: ""

                            // Regular search query handling
                            ProductListing(
                                query = query,
                                onBack = {
                                    navController.popBackStack()
                                },
                                onProductClick = { productId ->
                                    navController.navigate(Screen.ProductScreen(productId = productId))
                                }
                            )
                        }

                        composable<Screen.CategorySelectScreen> {
                            CategorySelectScreen(
                                onClose = {
                                    navController.popBackStack()
                                },
                                onCategoryClick = { primaryCategory, secondaryCategory, tertiaryCategory ->
                                    navController.navigate(
                                        Screen.ProductListingScreen(
                                            query = "",
                                            primaryCategory = primaryCategory,
                                            secondaryCategory = secondaryCategory,
                                            tertiaryCategory = tertiaryCategory
                                        )
                                    )
                                }
                            )
                        }

                        composable<Screen.SettingsScreen> {
                            SettingsScreen(
                                token = token,
                                onShowLoginBottomSheet = {
                                    showLoginBottomSheet.value = true
                                },
                                onRedirectToHome = {

                                },
                                onNavigate = { screenType ->
                                    if (screenType == Settings.MANAGE_TAX_INFO) {
                                        navController.navigate(Screen.GstScreen(null))
                                    } else if (screenType == Settings.ADDRESS) {
                                        navController.navigate(Screen.LocationScreen(null))
                                    } else {
                                        navController.navigate(
                                            Screen.SecondarySettingsScreen(
                                                screenType
                                            )
                                        )
                                    }
                                }
                            )
                        }
                        composable<Screen.SecondarySettingsScreen> {
                            val args = it.toRoute<Screen.SecondarySettingsScreen>()
                            val screenType = args.screenType ?: Settings.ACCOUNT_SETTINGS
                            SecondarySettingsScreen(
                                screenType = screenType,
                                onBack = {
                                    navController.popBackStack()
                                },
                            )
                        }
                    }
                }
                // Show login bottom sheet when needed
                if (showLoginBottomSheet.value) {
                    LoginBottomSheet(
                        onDismiss = {
                            scope.launch {
                                showLoginBottomSheet.value = false
                                authStateManager.setAuthenticated()
                            }
                        },
                        onLogin = {
                            showLoginBottomSheet.value = false
                            navController.navigate(Screen.ConnectScreen)
//                            {
//                                popUpTo(navController.graph.id) { inclusive = true }
//                            }
                        },
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBottomSheet(
    onDismiss: () -> Unit,
    onLogin: () -> Unit,
    navController: NavController
) {
    // Get the current back stack entry to determine if we're at the start destination
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val startDestination = navController.graph.startDestinationRoute
    val isAtStartDestination = currentRoute == startDestination

    ModalBottomSheet(
        onDismissRequest = {
            // Only pop back if we're not at the start destination
            if (!isAtStartDestination) {
                navController.popBackStack()
            }
            onDismiss()
        },
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Please Log In",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "You need to be logged in to continue using this feature.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onLogin() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            TextButton(
                onClick = {
                    // Only pop back if we're not at the start destination
                    if (!isAtStartDestination) {
                        navController.popBackStack()
                    }
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
