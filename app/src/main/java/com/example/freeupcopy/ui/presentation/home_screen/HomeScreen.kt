package com.example.freeupcopy.ui.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.home_screen.componants.AppNameTopSection
import com.example.freeupcopy.ui.presentation.home_screen.componants.BestInMen
import com.example.freeupcopy.ui.presentation.home_screen.componants.CategoriesHomeRow
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBar
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBarDefaults
import com.example.freeupcopy.ui.presentation.home_screen.componants.HomeCarousel
import com.example.freeupcopy.ui.presentation.home_screen.componants.HomeDashboard
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchTopSection
import com.example.freeupcopy.ui.presentation.home_screen.componants.WomenStyle
import com.example.freeupcopy.ui.presentation.home_screen.componants.WomenWear
import com.example.freeupcopy.ui.theme.SwapGoTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    lazyColumnState: LazyListState,
    onSearchBarClick: () -> Unit,
    onInboxClick: () -> Unit,
    onCartClick: () -> Unit,
    onCoinClick: () -> Unit,
    onCashClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Spacer(modifier = Modifier.statusBarsPadding())
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        //.offset(y = 6.dp)
                ) {

                    FlexibleTopBar(
                        scrollBehavior = scrollBehavior,
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        AppNameTopSection(
                            onCoinClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onCoinClick()
                                }
                            },
                            onCashClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onCashClick()
                                }
                            }
                        )
                    }
                }
                Box {
                    FlexibleTopBar(
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            scrolledContainerColor = Color.Transparent
                        )
                    ) {
                        SearchTopSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 12.dp, vertical = 8.dp
                                ),
                            onSearchBarClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onSearchBarClick()
                                }
                            },
                            onInboxClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onInboxClick()
                                }
                            },
                            onCartClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onCartClick()
                                }
                            }
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            }
        }
    ) { innerPadding->

        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            item {
                CategoriesHomeRow()
            }
            item {
                HomeCarousel(
                    modifier = Modifier.zIndex(1f),
                    onImagePreview = {}
                )
            }
            item {
                HomeDashboard(
                    modifier = Modifier.zIndex(1f)
                )
            }
            item {
                WomenStyle()
            }
            item {
                WomenWear()
            }
            item {
                BestInMen(
                    modifier = Modifier.zIndex(0f)
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    SwapGoTheme {
        HomeScreen(
            lazyColumnState = LazyListState(),
            onSearchBarClick = {},
            onInboxClick = {},
            onCartClick = {},
            onCoinClick = {},
            onCashClick = {}
        )
    }
}
