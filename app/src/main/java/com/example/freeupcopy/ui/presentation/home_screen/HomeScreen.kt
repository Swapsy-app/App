package com.example.freeupcopy.ui.presentation.home_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.freeupcopy.ui.presentation.home_screen.componants.AppNameTopSection
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBar
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBarDefaults
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchTopSection

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
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
//            .navigationBarsPadding()
            //.padding(bottom = )
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Spacer(modifier = Modifier.statusBarsPadding())
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        //.offset(y = 6.dp)
                ) {

                    FlexibleTopBar(
                        scrollBehavior = scrollBehavior,
                        colors = FlexibleTopBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer
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
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            scrolledContainerColor = Color.Transparent
                        )
                    ) {
                        SearchTopSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 12.dp,
                                    top = 8.dp
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
            }
        }
    ) { innerPadding2->
        //if remove offset int the content of the home screen then add the
        // bottom padding that is commented out SearchTopSection

        LazyColumn(
            modifier = Modifier
                //.offset(y = 12.dp)
                //.navigationBarsPadding()
                .padding(top = innerPadding2.calculateTopPadding())
                .fillMaxSize()
                .padding()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
