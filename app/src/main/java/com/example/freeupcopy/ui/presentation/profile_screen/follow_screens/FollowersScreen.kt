package com.example.freeupcopy.ui.presentation.profile_screen.follow_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.your_profile.FollowerItem
import com.example.freeupcopy.data.remote.dto.your_profile.FollowingItem
import com.example.freeupcopy.ui.presentation.community_screen.modals.SellerCard
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import java.net.UnknownHostException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowersScreen(
    modifier: Modifier = Modifier,
    type: String,
    onBackClick: () -> Unit,
    viewModel: FollowersViewModel = hiltViewModel(),
    onSellerClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Use the appropriate flow based on type
    val followersOrFollowing = if (type == "followers") {
        viewModel.followers.collectAsLazyPagingItems()
    } else {
        viewModel.following.collectAsLazyPagingItems()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    title = {
                        Text(
                            if (type == "followers") "Followers" else "Following",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }
        }
    ) { innerPadding ->
        // Content area with handling for different states
        Box(modifier = Modifier.fillMaxSize()) {
            if (followersOrFollowing.itemCount == 0 && followersOrFollowing.loadState.refresh is LoadState.NotLoading) {
                // Empty state
                EmptyFollowersMessage(type = type)
            } else {
                // Display followers/following items when available
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.surface),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(followersOrFollowing.itemCount) { index ->
                        followersOrFollowing[index]?.let { item ->
                            when (type) {
                                "followers" -> {
                                    val followerItem = item as FollowerItem
                                    SellerCard(
                                        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
                                        isOnline = true,
                                        sellerUsername = followerItem.follower.username,
                                        sellerRating = "4.77",
                                        onSellerClick = { onSellerClick(followerItem.follower._id) },
                                        followUser = followerItem.follower
                                    )
                                }
                                "following" -> {
                                    val followingItem = item as FollowingItem
                                    SellerCard(
                                        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
                                        isOnline = true,
                                        sellerUsername = followingItem.following.username,
                                        sellerRating = "4.77",
                                        onSellerClick = { onSellerClick(followingItem.following._id) },
                                        followUser = followingItem.following
                                    )
                                }
                            }
                        }
                    }

                    // Handle append loading state (loading more items) - exactly like WishListScreen
                    followersOrFollowing.apply {
                        if (loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        // End of pagination message
                        if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                            followersOrFollowing.itemCount != 0 && followersOrFollowing.itemCount > 15
                        ) {
                            item {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    text = if (type == "followers") "No more followers to load" else "No more following to load",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Error while loading more items
                        if (loadState.append is LoadState.Error) {
                            var message = ""
                            val e = (loadState.append as LoadState.Error).error
                            if (e is UnknownHostException) {
                                message = "No internet.\nCheck your connection"
                            } else if (e is Exception) {
                                message = e.message ?: "Unknown error occurred"
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(R.drawable.ic_error),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                    Spacer(Modifier.size(16.dp))
                                    Text(
                                        text = "Error: $message",
                                        modifier = Modifier.weight(1f),
                                        softWrap = true,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Button(
                                        modifier = Modifier.padding(start = 16.dp),
                                        onClick = { followersOrFollowing.retry() },
                                        shape = ButtonShape,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary,
                                            contentColor = MaterialTheme.colorScheme.onTertiary
                                        )
                                    ) {
                                        Text("Retry")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Handle refresh loading and error states - exactly like WishListScreen
        followersOrFollowing.apply {
            var message = ""
            if (loadState.refresh is LoadState.Error) {
                val e = (loadState.refresh as LoadState.Error).error
                if (e is UnknownHostException) {
                    message = "No internet.\nCheck your connection"
                } else if (e is Exception) {
                    message = e.message ?: "Unknown error occurred"
                }
            }

            when (loadState.refresh) {
                is LoadState.Error -> {
                    if (followersOrFollowing.itemCount == 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.im_error),
                                    contentDescription = null
                                )
                                Text(
                                    text = message.ifEmpty { "Unknown error occurred" },
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W500
                                )
                                Button(
                                    onClick = { followersOrFollowing.retry() },
                                    shape = ButtonShape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = MaterialTheme.colorScheme.onTertiary
                                    )
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }

                LoadState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                        PleaseWaitLoading(Modifier.align(Alignment.Center))
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun EmptyFollowersMessage(type: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.im_no_result_found),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (type == "followers") "No followers yet" else "Not following anyone yet",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (type == "followers") "When people follow you, they'll appear here" else "Start following people to see them here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}
