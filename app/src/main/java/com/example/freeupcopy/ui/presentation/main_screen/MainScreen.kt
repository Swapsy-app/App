package com.example.freeupcopy.ui.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.navigation.Screen
import com.example.freeupcopy.ui.presentation.community_screen.CommunityScreen
import com.example.freeupcopy.ui.presentation.home_screen.HomeScreen
import com.example.freeupcopy.ui.presentation.home_screen.componants.BottomNavigationItem
import com.example.freeupcopy.ui.presentation.home_screen.componants.CustomNavigationBarItem
import com.example.freeupcopy.ui.presentation.profile_screen.ProfileScreen
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val lifecycleOwner = LocalLifecycleOwner.current

    val lazyState = rememberScrollPositionListState("home_screen")


    // Sync pager with bottom navigation
    LaunchedEffect(state) {
        if (state.currentPage >= 0) {
            // Use scrollToPage instead of animateScrollToPage for instant switching
            pagerState.scrollToPage(state.currentPage)
        }
    }

    // Only update selectedIndex when page changes programmatically
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            viewModel.onEvent(MainUiEvent.CurrentPageChange(pagerState.currentPage))
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets(0.dp)),
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            SwapGoNavigationBar1(
                selectedIndex = pagerState.currentPage,
                onSelectedChange = {
                    viewModel.onEvent(MainUiEvent.CurrentPageChange(it))
                },
                onWishListClick = {
                    val currentState = lifecycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        onNavigate(Screen.WishListScreen)
                    }
                },
                onSellClick = {
                    val currentState = lifecycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        onNavigate(Screen.SellScreen)
                    }
                }
            )
        }
    ) { innerPadding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            userScrollEnabled = false,
            pageSpacing = 0.dp,      // Remove spacing between pages

        ) { page ->

            Box(
                Modifier
                    .fillMaxSize()
            ) {
                when (page) {
                    0 -> {
                        HomeScreen(
                            lazyColumnState = lazyState,
                            onSearchBarClick = {},
                            onInboxClick = {},
                            onCartClick = {},
                            onCoinClick = {},
                            onCashClick = {}
                        )
                    }

                    1 -> {
                        CommunityScreen()
                    }

                    2 -> {
                        //navigate to wish list screen
                    }

                    3 -> {
                        ProfileScreen(
                            onPostedProductClick = {
                                val currentState = lifecycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onNavigate(Screen.PostedProductsScreen)
                                }
                            },
                            onViewProfileClick = {
                                val currentState = lifecycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onNavigate(Screen.SellerProfileScreen)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SwapGoNavigationBar1(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit,
    onWishListClick: () -> Unit,
    onSellClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {

        CustomBottomBar1(
            modifier = Modifier.align(Alignment.BottomCenter),
            windowInsets = windowInsets,
            onWishListClick = onWishListClick,
            selectedIndex = selectedIndex,
            onSelectedChange = onSelectedChange
        )
        Box(
            modifier = Modifier
                .size(70.dp)
                .offset(y = (-12).dp)
                .shadow(6.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(
                    onClick = onSellClick
                )
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sell",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

@Composable
fun CustomBottomBar1(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    onWishListClick: () -> Unit,
) {

    val items = listOf(
        BottomNavigationItem(
            contentDescription = "Home",
            selectedIcon = painterResource(id = R.drawable.ic_home),
            unselectedIcon = painterResource(id = R.drawable.ic_home),
            onClick = {  }
        ),
        BottomNavigationItem(
            contentDescription = "Community",
            selectedIcon = painterResource(id = R.drawable.ic_community_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_community),
            onClick = {  }
        ),
        BottomNavigationItem(
            contentDescription = "WishList",
            selectedIcon = painterResource(id = R.drawable.ic_favorite_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_favorite),
            onClick = onWishListClick
        ),
        BottomNavigationItem(
            contentDescription = "Profile",
            selectedIcon = painterResource(id = R.drawable.ic_person_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_person),
            onClick = {  }
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .windowInsetsPadding(windowInsets)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        items.forEachIndexed { index, item ->
            CustomNavigationBarItem(
                selected = index == selectedIndex,
                onClick = {
                    if(index != 2) {
                        onSelectedChange(index)
                    }
                    item.onClick()
                },
                icon = item.unselectedIcon,
                selectedIcon = item.selectedIcon,
                contentDescription = item.contentDescription
            )
            if (index == 1) Spacer(modifier = Modifier.size(60.dp)) // Leave space for the FAB
        }
    }
}

private val saveMap = mutableMapOf<String, KeyParams>()

private data class KeyParams(
    val params: String = "",
    val index: Int,
    val scrollOffset: Int,
)

/**
 * Save scroll state on all time.
 * @param key value for comparing screen
 * @param params arguments for find different between equals screen
 * @param initialFirstVisibleItemIndex see [LazyListState.firstVisibleItemIndex]
 * @param initialFirstVisibleItemScrollOffset see [LazyListState.firstVisibleItemScrollOffset]
 */
@Composable
fun rememberScrollPositionListState(
    key: String,
    params: String = "",
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0,
): LazyListState {
    val scrollState =
        rememberSaveable(saver = LazyListState.Saver) {
            var savedValue = saveMap[key]
            if (savedValue?.params != params) savedValue = null
            val savedIndex = savedValue?.index ?: initialFirstVisibleItemIndex
            val savedOffset = savedValue?.scrollOffset ?: initialFirstVisibleItemScrollOffset
            LazyListState(
                savedIndex,
                savedOffset,
            )
        }
    DisposableEffect(Unit) {
        onDispose {
            val lastIndex = scrollState.firstVisibleItemIndex
            val lastOffset = scrollState.firstVisibleItemScrollOffset
            saveMap[key] = KeyParams(params, lastIndex, lastOffset)
        }
    }
    return scrollState
}


@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    SwapGoTheme {
        MainScreen(
            onNavigate = {},
        )
    }
}