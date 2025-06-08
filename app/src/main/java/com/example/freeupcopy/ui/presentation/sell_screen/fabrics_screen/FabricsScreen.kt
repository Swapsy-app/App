@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.sell_screen.fabrics_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun FabricScreen(
    modifier: Modifier = Modifier,
    navigatedFabric: String,
    onFabricClick: (String) -> Unit,
    onClose: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var searchQuery by remember { mutableStateOf("") }

    // All fabrics from the images in exact order
    val allFabrics = remember {
        listOf(
            "Acrylic",
            "Art Silk",
            "Bagru",
            "Banarasi",
            "Banarasi Silk",
            "Bandhej",
            "Chanderi",
            "Chanderi Cotton",
            "Chanderi Silk",
            "Chettinad",
            "Chiffon",
            "Cotton",
            "Cotton Blend",
            "Cotton Linen",
            "Cotton Silk",
            "Crepe",
            "Denim",
            "Georgette",
            "Handloom",
            "Ikat",
            "Jute",
            "Kanjeevaram",
            "Kantha",
            "Kota doria",
            "Leather",
            "Linen",
            "Linen Blend",
            "Modal",
            "Mysore Silk",
            "Net",
            "Organic Cotton",
            "Organza",
            "Pashmina",
            "Poly Cotton",
            "Poly Silk",
            "Polycotton",
            "Polyester",
            "Rayon",
            "Satin",
            "Silk",
            "Silk Blend",
            "Synthetic Chiffon",
            "Synthetic Crepe",
            "Synthetic Georgette",
            "Tissue",
            "Tussar Silk",
            "Velvet",
            "Viscose",
            "Viscose Rayon",
            "Wool"
        )
    }

    // Filter fabrics based on search
    val filteredFabrics = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            allFabrics
        } else {
            allFabrics.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = "Fabric", fontWeight = FontWeight.Bold)
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))

            // Search Bar
            SearchBar(
                value = searchQuery,
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

            // Fabric List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item{
                    Text(
                        text = "All Fabrics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(filteredFabrics) { fabric ->
                    FabricItem(
                        fabricName = fabric,
                        onFabricClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onFabricClick(fabric)
                            }
                        },
                        isSelected = navigatedFabric == fabric
                    )
                }
            }
        }
    }
}

@Composable
fun FabricItem(
    modifier: Modifier = Modifier,
    fabricName: String,
    onFabricClick: () -> Unit,
    isSelected: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onFabricClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = fabricName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (fabricName == "ALL FABRIC") FontWeight.SemiBold else FontWeight.Normal,
            color = if (fabricName == "ALL FABRIC")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        CustomRadioButton(
            isSelected = isSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FabricScreenPreview() {
    SwapGoTheme {
        FabricScreen(
            navigatedFabric = "Cotton",
            onFabricClick = { },
            onClose = { }
        )
    }
}
