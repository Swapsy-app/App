package com.example.freeupcopy.ui.presentation.profile_screen

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.ui.navigation.Screen
import com.example.freeupcopy.ui.presentation.main_screen.MainUiEvent
import com.example.freeupcopy.ui.presentation.offer_screen.OffersUiEvent
import com.example.freeupcopy.ui.presentation.profile_screen.componants.BalanceSection
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ProfileBanner
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ProfileTopBar
import com.example.freeupcopy.ui.presentation.profile_screen.componants.SellerHub
import com.example.freeupcopy.ui.presentation.profile_screen.componants.YourPostedProducts
import com.example.freeupcopy.ui.theme.SwapGoTheme
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userId: String?,
    token: String?,
    onPostedProductClick: () -> Unit,
    onViewProfileClick: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onShowLoginBottomSheet: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by profileViewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                profileViewModel.onEvent(ProfileUiEvent.ClearError)
            }
            Log.e("ProfileScreen", "Error: ${state.error}")
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            ProfileTopBar(
                user = state.user,
                userRating = state.userRating,
                onSettingsClick = {
                    onNavigate(Screen.SettingsScreen)
                },
                onViewProfileClick = {
                    onViewProfileClick()
                },
                isLoggedIn = token != null,
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when {

                        // Network error styling
                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> {
                            Color(0xFFFF9800).copy(alpha = 0.9f) // Orange for network issues
                        }

                        // Authentication errors
                        state.error.contains("unauthorized", ignoreCase = true) ||
                                state.error.contains("login", ignoreCase = true) ||
                                state.error.contains("token", ignoreCase = true) -> {
                            Color(0xFF9C27B0).copy(alpha = 0.9f) // Purple for auth issues
                        }

                        // General errors
                        else -> {
                            MaterialTheme.colorScheme.errorContainer // Red for general errors
                        }
                    },
                    contentColor = when {
                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> Color.White
                        state.error.contains("unauthorized", ignoreCase = true) ||
                                state.error.contains("login", ignoreCase = true) ||
                                state.error.contains("token", ignoreCase = true) -> Color.White
                        else -> MaterialTheme.colorScheme.onErrorContainer
                    },
                    shape = RoundedCornerShape(12.dp),
                    actionColor = when {
                        state.error.contains("network", ignoreCase = true) ||
                                state.error.contains("internet", ignoreCase = true) ||
                                state.error.contains("connection", ignoreCase = true) -> Color.White
                        state.error.contains("unauthorized", ignoreCase = true) ||
                                state.error.contains("login", ignoreCase = true) ||
                                state.error.contains("token", ignoreCase = true) -> Color.White
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                item {
                    ProfileBanner(
                        onClick = {

                        },
                        onWishlistClick = {
                            if(token == null) {
                                onShowLoginBottomSheet()
                            } else {
                                onNavigate(Screen.WishListScreen)
                            }
                        },
                        onYourOrdersClick = {},
                        onYourOffersClick = {
                            onNavigate(Screen.OfferScreen(userId = userId))
                        }
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
            onPostedProductClick = {},
            onViewProfileClick = {},
            onNavigate = {},
            userId = "",
            token = null,
            onShowLoginBottomSheet = {}
        )
    }
}