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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.home_screen.componants.AppNameTopSection
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBar
import com.example.freeupcopy.ui.presentation.home_screen.componants.FlexibleTopBarDefaults
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchTopSection
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.ProductListing

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
    ProductListing(onBack = { /*TODO*/ })
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 items per row
            modifier = Modifier
                .padding(top = innerPadding2.calculateTopPadding())
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            items(10) { index ->
                ProductCard(
                    companyName = listOf(
                        "Adidas", "Nike", "Puma", null, "Under Armour",
                        "Levi's", "Calvin Klein", "Tommy Hilfiger", "Lacoste", null
                    )[index],
                    productName = listOf(
                        "Adidas Bomber Jacket", "Nike Air Max", "Puma Suede", "Reebok Classic", "Under Armour Hoodie",
                        "Levi's Jeans", "Calvin Klein T-shirt", "Tommy Hilfiger Polo", "Lacoste L.12.12", "Ralph Lauren Shirt"
                    )[index],
                    size = listOf(
                        "40 inches", "L", "M", "XL", "L",
                        "32 inches", "S", "M", "XL", "L"
                    )[index],
                    productThumbnail = painterResource(id = R.drawable.bomber_jacket), // Assuming R.drawable.bomber_jacket is a placeholder
                    priceOffered = listOf(
                        null, "499", "399", null, "799",
                        "1999", null, "1499", "1299", null
                    )[index],
                    coinsOffered = listOf(
                        null, "1000", null, "599", null,
                        null, "999", null, null, "1799"
                    )[index],
                    specialOffer = listOf(
                        listOf("4000", "2000"), listOf("300","800"), null, null, null,
                        null, null, listOf("1000", "500"), null, null
                    )[index],
                    priceOriginal = listOf(
                        "3500", "2999", "1999", "1499", "1999",
                        "3999", "1999", "2999", "2499", "3499"
                    )[index],
                    badge = listOf(
                        "Trusted", "Sale", "New", null, "Limited Edition",
                        "Classic", "Trendy", "Luxury", "Iconic", null
                    )[index],
                    isLiked = false,
                    onLikeClick = {},
                    priorityPriceType = null, // Set priorityPriceType to null for all products
                    isClothes = listOf(
                        true, false, true, false, true,
                        true, true, true, false, true
                    )[index]
                )
            }
        }
    }
}
@Preview(showBackground = true) @Composable
fun PreviewHomeScreen(){
    HomeScreen(
        innerPadding = PaddingValues(0.dp),
        onSearchBarClick = { /*TODO*/ },
        onInboxClick = { /*TODO*/ },
        onCartClick = { /*TODO*/ },
        onCoinClick = { /*TODO*/ }) {
    }
}