package com.example.freeupcopy.ui.presentation.sell_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun SellScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: () -> String,
    chosenCategory1: String
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var chosenCategory by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                SellCategory(
                    chosenCategory = chosenCategory1,
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            chosenCategory = onCategoryClick()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SellCategory(
    modifier: Modifier = Modifier,
    chosenCategory: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isCategoryChosen = chosenCategory.isEmpty()
        Text(text = if(isCategoryChosen) "Choose a category" else "Category")
        Spacer(modifier = Modifier.weight(1f))
        Text(text = chosenCategory, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f))
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = "Choose a category",
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}
//
//@Preview
//@Composable
//fun PreviewSellScreen() {
//    FreeUpCopyTheme {
//        SellScreen(
//            onCategoryClick = { "kurta" }
//        )
//    }
//}
