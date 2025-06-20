package com.example.freeupcopy.ui.presentation.profile_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.navigation.Screen
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
    onGetStartedClick: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onShowLoginBottomSheet: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by profileViewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val lifeCycleOwner = LocalLifecycleOwner.current

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
                if(token != null) {
                    item {
                        ProfileBanner(
                            onClick = {

                            },
                            onWishlistClick = {
                                onNavigate(Screen.WishListScreen)
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
                }

                else {
                    // Guest user content
                    item {
                        GuestWelcomeBanner(
                            onGetStartedClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onGetStartedClick()
                                }
                            }
                        )
                    }
                    item {
                        GuestFeaturesBanner(
                            onLoginClick = onShowLoginBottomSheet
                        )
                    }
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
                        onOnlineModeClick = {
                            onNavigate(Screen.OnlineModeScreen)
                        },
                        onShippingGuideClick = {
                            onNavigate(Screen.ShippingLabelsScreen)
                        },
                        onCODClick = {
                            onNavigate(Screen.CODScreen)
                        }
                    )
                }
                item {
                    Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}


@Composable
fun GuestWelcomeBanner(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
               MaterialTheme.colorScheme.primaryContainer
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Animated welcome icon with gradient background
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
//            Icon(
//                imageVector = Icons.Rounded.AccountCircle,
//                contentDescription = null,
//                modifier = Modifier.size(50.dp),
//                tint = Color.White
//            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Welcome text with better typography
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Welcome to SwapGo!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Join our community to buy, sell, and trade amazing products. Sign in to unlock your personalized experience!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp
            )
        }

        // Beautiful login button with gradient and animation
        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Get Started For Free",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        // Subtle feature highlights
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FeatureHighlight(
                icon = Icons.Outlined.Lock,
                text = "Secure",
                tint = Color(0xFF4CAF50)
            )
            FeatureHighlight(
                painter = painterResource(R.drawable.ic_fast),
                text = "Fast",
                tint = Color(0xFF2196F3)
            )
            FeatureHighlight(
                painter = painterResource(R.drawable.ic_trusted),
                text = "Trusted",
                tint = Color(0xFFFF9800)
            )
        }
    }
}

@Composable
private fun FeatureHighlight(
    icon: ImageVector,
    text: String,
    tint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = tint.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FeatureHighlight(
    painter: Painter,
    text: String,
    tint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = tint.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun GuestFeaturesBanner(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header with icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = "Unlock Premium Features",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "See what you're missing:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Feature grid
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GuestFeatureCard(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.ic_favorite),
                    title = "Wishlist",
                    description = "Save favorites",
                    iconTint = Color(0xFFE91E63)
                )
                GuestFeatureCard(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.ic_your_orders),
                    title = "Orders",
                    description = "Track purchases",
                    iconTint = Color(0xFF2196F3)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GuestFeatureCard(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.ic_offer),
                    title = "Offers",
                    description = "Manage deals",
                    iconTint = Color(0xFFF38600)
                )
                GuestFeatureCard(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.ic_cash),
                    title = "Balance",
                    description = "Earn & spend",
                    iconTint = Color(0xFF4CAF50)
                )
            }
        }

        // Call to action
        OutlinedButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 1.5.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                )
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Explore All Features",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun GuestFeatureCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    description: String,
    iconTint: Color
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = iconTint.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = iconTint.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun GuestFeatureCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    description: String,
    iconTint: Color
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = iconTint.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = iconTint.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
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
            onGetStartedClick = {},
            onShowLoginBottomSheet = {}
        )
    }
}