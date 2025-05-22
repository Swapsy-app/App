package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBar
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBarDefaults
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.profile_screen.componants.ProfileProductTabRow
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ConfirmDialog
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.DeliveredList
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ListedList
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.PendingList
import com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen.components.ProductActionsBottomSheet
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostedProductsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onProductClick: () -> Unit,
    viewModel: PostedProductsViewModel = hiltViewModel()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    val lifeCycleOwner = LocalLifecycleOwner.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val pagerState = rememberPagerState(initialPage = 0) { 3 }

    LaunchedEffect(key1 = state.currentTabIndex) {
        pagerState.animateScrollToPage(state.currentTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            viewModel.onEvent(PostedProductsUiEvent.OnTabSelected(pagerState.currentPage))
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            //.navigationBarsPadding()
        ,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            Column {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = {
                        if (state.isSearching) {
                            SearchBar(
                                value = "",
                                isFocused = remember {
                                    mutableStateOf(false)
                                },
                                onFocusChange = {},
                                onValueChange = {},
                                onSearch = {  },
                                onCancel = {  },
                                containerColor = TextFieldContainerColor,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        } else {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Posted Products",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
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
                        IconButton(
                            onClick = {
                                viewModel.onEvent(PostedProductsUiEvent.OnSearchButtonClicked)
                            }
                        ) {
                            if (state.isSearching) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "close"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "search"
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )

                FlexibleTopBar(
                    colors = FlexibleTopBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                ) {
                    Column {
                        ProfileProductTabRow(
                            selectedTabIndex = state.currentTabIndex,
                            onClick = {
                                viewModel.onEvent(PostedProductsUiEvent.OnTabSelected(it))
                            },
                            tabData = state.tabData,
                            selectedSubCategoryIndex = when (state.currentTabIndex) {
                                0 -> state.listingFilterIndex
                                1 -> state.pendingFilterIndex
                                2 -> state.deliveredFilterIndex
                                else -> null
                            },
                            onSubCategoryClick = {
                                when (state.currentTabIndex) {
                                    0 -> viewModel.onEvent(PostedProductsUiEvent.OnListingFilterSelected(it))
                                    1 -> viewModel.onEvent(PostedProductsUiEvent.OnPendingFilterSelected(it))
                                    2 -> viewModel.onEvent(PostedProductsUiEvent.OnDeliveredFilterSelected(it))
                                }
                            }
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        )
                    }
                }
            }

        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = pagerState,
            ) { index ->
                when (index) {

                    0 -> {
                        ListedList(
                            onProductClick = { onProductClick() },
                            onActionClick = { showBottomSheet = true }
                        )
                    }

                    1 -> {
                        PendingList()
                    }

                    2 -> {
                        DeliveredList()
                    }
                }
            }
        }

        ProductActionsBottomSheet(
            isVisible = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            onHide = { /* Handle hide action */ },
            onDelete = {
                showBottomSheet = false
                showConfirmDialog = true
            }
        )

        if (showConfirmDialog) {
            ConfirmDialog(
                dialogText = "Are you sure you want to delete this product?" +
                        " This action cannot be undone.",
                onConfirm = {
                    showConfirmDialog = false
                    // Handle delete action
                },
                onCancel = {
                    showConfirmDialog = false
                },
                cancelButtonText = "Cancel",
                confirmButtonText = "Delete",
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PostedProductsScreenPreview() {
    SwapGoTheme {
        PostedProductsScreen(
            onBack = {},
            onProductClick = {}
        )
    }
}