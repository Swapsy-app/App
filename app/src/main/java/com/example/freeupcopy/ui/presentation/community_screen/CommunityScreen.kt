package com.example.freeupcopy.ui.presentation.community_screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.paging.compose.LazyPagingItems
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
fun CommunityScreen(
    modifier: Modifier = Modifier,
    onSellerClick: (String) -> Unit = {},
    viewModel: CommunityViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val state by viewModel.state.collectAsState()

    // Get the appropriate data based on selected tab
    val followersData = viewModel.followers.collectAsLazyPagingItems()
    val followingData = viewModel.following.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text("Community", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {

            PrimaryTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    text = {
                        Text(
                            text = "Following",
                            fontSize = 16.sp
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Text(
                            text = "Followers",
                            fontSize = 16.sp
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                )
            }

            // Content based on selected tab
            when (selectedTabIndex) {
                0 -> {
                    // Following Tab
                    FollowingContent(
                        followingData = followingData,
                        onSellerClick = onSellerClick
                    )
                }
                1 -> {
                    // Followers Tab
                    FollowersContent(
                        followersData = followersData,
                        onSellerClick = onSellerClick
                    )
                }
            }
        }
    }
}
@Composable
fun FollowingContent(
    followingData: LazyPagingItems<FollowingItem>,
    onSellerClick: (String) -> Unit
) {
    // Content area with handling for different states - exactly like WishListScreen
    Box(modifier = Modifier.fillMaxSize()) {
        if (followingData.itemCount == 0 && followingData.loadState.refresh is LoadState.NotLoading) {
            // Empty state
            EmptyCommunityMessage(type = "following")
        } else {
            // Display following items when available
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(followingData.itemCount) { index ->
                    followingData[index]?.let { item ->
                        SellerCard(
                            modifier = Modifier,
                            isOnline = true,
                            sellerUsername = item.following.username,
                            sellerRating = "4.77",
                            followUser = item.following,
                            onSellerClick = { onSellerClick(item.following._id) }
                        )
                    }
                }

                // Handle append loading state (loading more items) - exactly like WishListScreen
                followingData.apply {
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
                        followingData.itemCount != 0 && followingData.itemCount > 15
                    ) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                text = "No more following to load",
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
                                    onClick = { followingData.retry() },
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

        // Handle refresh loading and error states - exactly like WishListScreen
        followingData.apply {
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
                    if (followingData.itemCount == 0) {
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
                                    onClick = { followingData.retry() },
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

                is LoadState.Loading -> {
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
fun FollowersContent(
    followersData: LazyPagingItems<FollowerItem>,
    onSellerClick: (String) -> Unit
) {
    // Content area with handling for different states - exactly like WishListScreen
    Box(modifier = Modifier.fillMaxSize()) {
        if (followersData.itemCount == 0 && followersData.loadState.refresh is LoadState.NotLoading) {
            // Empty state
            EmptyCommunityMessage(type = "followers")
        } else {
            // Display followers items when available
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(followersData.itemCount) { index ->
                    followersData[index]?.let { item ->
                        SellerCard(
                            modifier = Modifier,
                            isOnline = true,
                            sellerUsername = item.follower.username,
                            sellerRating = "4.77",
                            followUser = item.follower,
                            onSellerClick = { onSellerClick(item.follower._id) }
                        )
                    }
                }

                // Handle append loading state (loading more items) - exactly like WishListScreen
                followersData.apply {
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
                        followersData.itemCount != 0 && followersData.itemCount > 15
                    ) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                text = "No more followers to load",
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
                                    onClick = { followersData.retry() },
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

        // Handle refresh loading and error states - exactly like WishListScreen
        followersData.apply {
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
                    if (followersData.itemCount == 0) {
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
                                    onClick = { followersData.retry() },
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

                is LoadState.Loading -> {
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
private fun EmptyCommunityMessage(type: String) {
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
                text = if (type == "followers") "No followers yet" else "You're not following anyone yet",
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