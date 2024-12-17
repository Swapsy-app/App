@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.sell_screen.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.SwapsyTheme

@Composable
fun BrandScreen(
    modifier: Modifier = Modifier,
    navigatedBrand: String,
    onBrandClick: (String) -> Unit,
    onClose: () -> Unit

) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Brand")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onClose()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "close"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val isSearchBarFocused = remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        //val navigatedBrand by remember { mutableStateOf("") }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            SearchBar(
                value = "",
                isFocused = isSearchBarFocused,
                onFocusChange = {
                    isSearchBarFocused.value = it
                },
                onValueChange = {
                    searchQuery = it
                },
                onSearch = { },
                onCancel = {
                    searchQuery = ""
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            if (navigatedBrand.isNotEmpty() && navigatedBrand != "No Brand"){
                BrandItem(
                    brandName = navigatedBrand,
                    onBrandClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onBrandClick(navigatedBrand)
                        }
                    },
                    isSelected = true
                )
            }
//            BrandItem(
//                brandName = "navigatedBrand",
//                onBrandClick = {  },
//                isSelected = true
//            )
            BrandItem(
                brandName = "No Brand",
                onBrandClick = {
                    val currentState = lifeCycleOwner.lifecycle.currentState
                    if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        onBrandClick("No Brand")
                    }
                },
                isSelected = navigatedBrand == "No Brand"
            )
        }
    }
}

@Composable
fun BrandItem(
    modifier: Modifier = Modifier,
    brandName: String,
    onBrandClick: () -> Unit,
    isSelected: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onBrandClick() }
            //.background(Color.Red)
            .padding(16.dp)
    ) {
        Text(text = brandName)
        Spacer(modifier = Modifier.weight(1f))
        CustomRadioButton(
            isSelected = isSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BrandScreenPreview() {
    SwapsyTheme {
        BrandScreen (
            navigatedBrand = "Apple",
            onBrandClick = {  },
            onClose = {  }
        )
    }
}