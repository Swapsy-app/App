package com.example.freeupcopy.ui.presentation.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        SearchBar(
            value = "",
            isFocused = remember { mutableStateOf(false) },
            onFocusChange = {},
            onValueChange = {},
            onSearch = { /*TODO*/ }) {

        }
    }
}