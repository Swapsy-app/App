package com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.profile_screen.seller_profile_screen.components.SellerProfileActionsBottomSheet
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProfileScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onEditProfile: (String, String, String, String, String, String) -> Unit,
    onNavigateFollow: (String, String) -> Unit,
    viewModel: SellerProfileViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    // Handle error with Snackbar
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank() && !state.isLoading) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
                // Clear error after showing
                viewModel.onEvent(SellerProfileUiEvent.ClearError)
            }
            Log.e("SellerProfileScreen", "Error: ${state.error}")
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        containerColor = if (state.error.contains(
                                "already following",
                                ignoreCase = true
                            )
                        ) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.errorContainer
                        },
                        contentColor = if (state.error.contains(
                                "already following",
                                ignoreCase = true
                            )
                        ) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    )
                }
            )
        },
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        if (state.sellerUsername != "") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = "@${state.sellerUsername}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500,
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Icon(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {
                                                val clip =
                                                    ClipData.newPlainText(
                                                        "username",
                                                        state.sellerUsername
                                                    )
                                                clipboardManager.setPrimaryClip(clip)
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Username copied",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                        )
                                        .size(18.dp)
                                        .alpha(0.5f),
                                    painter = painterResource(id = R.drawable.ic_copy),
                                    contentDescription = "copy"
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onBack()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    },
                    actions = {
                        if (!state.isMyProfile) {
                            IconButton(
                                onClick = { showBottomSheet = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = "more"
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = state.isLoading,
            state = pullRefreshState,
            onRefresh = {
                scope.launch {
                    viewModel.getUserProfile()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                // Profile header section
                Row {
                    if (state.sellerProfileImageUrl.isNotBlank()) {
                        SubcomposeAsyncImage(
                            modifier = modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            model = state.sellerProfileImageUrl,
                            loading = {
                                painterResource(id = R.drawable.im_user)
                            },
                            contentDescription = "profile",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            modifier = modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.im_user),
                            contentDescription = "profile",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Column(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = state.sellerName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Active ${state.lastActive}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            item {
                                SellerInfoButton(
                                    backgroundColor = Color(0xFFE5FFD8),
                                    text = "Ships in ${state.shippingTime ?: "1-2 days"}"
                                )
                            }
                            item {
                                if (state.occupation.isNotBlank()) {
                                    SellerInfoButton(
                                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                            0.8f
                                        ),
                                        text = state.occupation
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = state.sellerBio,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.size(20.dp))

                // Followers/Following section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onNavigateFollow("followers", state.sellerId)
                                }
                            }
                        )
                    ) {
                        Text(
                            text = state.followers,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = " Followers",
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Row(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onNavigateFollow("following", state.sellerId)
                                }
                            }
                        )
                    ) {
                        Text(
                            text = state.following,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = " Following",
                            fontSize = 15.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))

                // Seller info section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ButtonShape)
                        .background(TextFieldContainerColor)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_ranks),
                        contentDescription = "ranks",
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Achiever Seller  •  Joined ${state.joinedTime}",
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .alpha(0.5f),
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "info",
                    )
                }

                Spacer(modifier = Modifier.size(24.dp))

                // Action buttons section
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (state.isMyProfile) {
                        OutlinedButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            onClick = {
                                onEditProfile(
                                    state.sellerProfileImageUrl,
                                    state.sellerName,
                                    state.sellerUsername,
                                    state.sellerBio,
                                    state.gender,
                                    state.occupation,
                                )
                            },
                            shape = ButtonShape,
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
                        ) {
                            Text("Edit Profile")
                        }
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        onClick = {},
                        shape = ButtonShape,
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    ) {
                        Row {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "share",
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Share")
                        }
                    }

                    if (!state.isMyProfile) {
                        Button(
                            modifier = Modifier
                                .weight(1.4f)
                                .height(50.dp),
                            onClick = {
                                if (!state.isFollowLoading) {
                                    viewModel.onEvent(SellerProfileUiEvent.FollowClicked)
                                }
                            },
                            enabled = !state.isFollowLoading,
                            shape = ButtonShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!state.isFollowing) Color.Black else Color.Transparent,
                                contentColor = if (!state.isFollowing) Color.White else MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (!state.isFollowing) Color.Black else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                    alpha = 0.6f
                                )
                            )
                        ) {
                            if (state.isFollowLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                    color = if (!state.isFollowing) Color.White else MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = if (state.isFollowing) Icons.Rounded.CheckCircle else Icons.Rounded.Add,
                                        contentDescription = "follow",
                                    )
                                    Text(
                                        text = if (state.isFollowing) "Following" else "Follow"
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                // Stats section
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Column(
                            modifier = Modifier
                                .widthIn(110.dp)
                                .clip(ButtonShape)
                                .background(TextFieldContainerColor.copy(alpha = 0.5f))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                                    shape = ButtonShape
                                )
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = state.rating ?: "4.5",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = "star",
                                    tint = Color.Unspecified
                                )
                            }
                            Text(
                                text = "${state.numberOfReviews ?: "0"} reviews",
                                fontSize = 14.sp,
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .widthIn(110.dp)
                                .clip(ButtonShape)
                                .background(Color(0xFFE5FFD8).copy(0.5f))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                                    shape = ButtonShape
                                )
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = state.sold ?: "0",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Sold",
                                fontSize = 14.sp,
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .widthIn(110.dp)
                                .clip(ButtonShape)
                                .background(Color(0xFFFF928F).copy(alpha = 0.12f))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                                    shape = ButtonShape
                                )
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = state.cancelled ?: "0",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Cancelled as Seller",
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }
        }

        SellerProfileActionsBottomSheet(
            isVisible = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            onReport = { /* Handle report action */ },
            onBlock = {
                showBottomSheet = false
                showConfirmDialog = true
            }
        )
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.3f)),
            contentAlignment = Alignment.Center
        ) {
            PleaseWaitLoading()
        }
    }
}

@Composable
fun SellerInfoButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    text: String
) {
    Box(
        modifier = modifier
            .clip(ButtonShape)
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = ButtonShape
            )
            .padding(horizontal = 16.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}