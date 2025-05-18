package com.example.freeupcopy.ui.presentation.search_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.data.local.RecentlyViewed
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    onRecentProductClick: (String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val state by searchViewModel.state.collectAsState()
    val isFocused = remember {
        mutableStateOf(false)
    }

    val lifeCycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        value = state.searchQuery,
                        isFocused = isFocused,
                        onFocusChange = {
                            isFocused.value = it
                        },
                        onValueChange = {
                            searchViewModel.onEvent(SearchUiEvent.SearchQueryChanged(it))
                        },
                        onSearch = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                scope.launch {
                                    searchViewModel.onEvent(SearchUiEvent.OnSearch)
                                    onSearch(state.searchQuery)
                                }
                            }
                        },
                        onCancel = {
                            searchViewModel.onEvent(SearchUiEvent.OnClearSearch)
                            isFocused.value = false
                        },
                        modifier = Modifier.padding(end = 16.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )

                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onBack()
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(innerPadding)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                if (state.suggestions.isNotEmpty()) {
                    Text(
                        text = "Suggestions",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.suggestions.take(4)) { suggestion ->
                            SuggestionItem(
                                text = suggestion,
                                onClick = {
                                    searchViewModel.onEvent(
                                        SearchUiEvent.SearchQueryChanged(
                                            suggestion
                                        )
                                    )
                                    searchViewModel.onEvent(SearchUiEvent.OnSearch)
                                    onSearch(suggestion)
                                }
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Recent Searches (${state.recentSearches.size})",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontWeight = FontWeight.W500
                    )
                    Spacer(Modifier.weight(1f))
                    if (state.recentSearches.isNotEmpty()) {
                        Text(
                            text = "Clear All",
                            fontSize = 14.sp,
                            color = Color(0xFF007AFF),
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    searchViewModel.onEvent(SearchUiEvent.ClearAllRecentSearches)
                                }
                            ),
                            fontWeight = FontWeight.W500
                        )
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(state.recentSearches) { recentSearch ->
                        RecentSearchItem(
                            text = recentSearch.recentSearch,
                            onClick = {
                                searchViewModel.onEvent(
                                    SearchUiEvent.SelectRecentSearch(
                                        recentSearch
                                    )
                                )
                                onSearch(recentSearch.recentSearch)
                            },
                            onDelete = {
                                searchViewModel.onEvent(
                                    SearchUiEvent.DeleteRecentSearch(
                                        recentSearch
                                    )
                                )
                            }
                        )
                    }
                }
            }

            if(state.recentlyViewed.isEmpty().not()) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_recent_searches),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.size(6.dp))
                        Text(
                            text = "Recently Viewed",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Clear All",
                            fontSize = 14.sp,
                            color = Color(0xFF007AFF),
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    searchViewModel.onEvent(SearchUiEvent.ClearAllRecentlyViewed)
                                }
                            ),
                            fontWeight = FontWeight.W500
                        )
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(state.recentlyViewed) { recentlyViewed ->
                            RecentlyViewedItem(
                                recentlyViewed = recentlyViewed,
                                onClick = {
                                    val currentState = lifeCycleOwner.lifecycle.currentState
                                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                        scope.launch {
                                            searchViewModel.onEvent(SearchUiEvent.IsLoading(true))

                                            searchViewModel.onEvent(
                                                SearchUiEvent.ProductClicked(
                                                    productId = recentlyViewed.productId
                                                )
                                            )
                                            delay(100)
                                            // Call onProductClick after onEvent has been processed.
                                            onRecentProductClick(recentlyViewed.productId)

                                            searchViewModel.onEvent(SearchUiEvent.IsLoading(false))
                                        }
                                    }
                                }
                            )
                        }
                    }
                    Spacer(Modifier.size(60.dp))
                }
            }

        }
    }

}


@Composable
fun RecentSearchItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(ButtonShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = null,
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        IconButton(
            onClick = { onDelete() }
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .alpha(0.5f),
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        }
    }
}

@Composable
fun RecentlyViewedItem(
    modifier: Modifier = Modifier,
    recentlyViewed: RecentlyViewed,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .width(90.dp)
            .width(IntrinsicSize.Min),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .clickable { onClick(recentlyViewed.productId) },
            model = recentlyViewed.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_logo_full),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            error = {
                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_logo_full),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        )
        Text(
            text = recentlyViewed.title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(0.65f),
            modifier = Modifier.padding(top = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Center,
            lineHeight = 17.sp,
        )
    }
}

@Composable
fun SuggestionItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ButtonShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .rotate(45f)
                .size(22.dp)
                .alpha(0.65f),
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview
@Composable
private fun SearchScreenPreview() {
    SwapGoTheme {
        SearchScreen(
            onBack = {},
            onSearch = {},
            onRecentProductClick = {},
        )
    }
}