@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.sell_screen.brand_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldShape

@Composable
fun BrandScreen(
    modifier: Modifier = Modifier,
    navigatedBrand: String,
    onBrandClick: (String) -> Unit,
    onClose: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var searchQuery by remember { mutableStateOf("") }
    var showAddBrandDialog by remember { mutableStateOf(false) }
    var customBrands by remember { mutableStateOf(listOf<String>()) }

    // 50 Most Popular Brands
    val popularBrands = remember {
        listOf(
            "Zara", "H&M", "Nike", "Adidas", "Puma", "Reebok", "Levi's", "Tommy Hilfiger",
            "Calvin Klein", "Ralph Lauren", "Gap", "Forever 21", "Uniqlo", "Mango", "Bershka",
            "Pull & Bear", "Stradivarius", "Massimo Dutti", "COS", "& Other Stories",
            "Vero Moda", "Only", "Jack & Jones", "Selected", "Pieces",
            "Raymond", "Peter England", "Van Heusen", "Allen Solly", "Louis Philippe",
            "Arrow", "Blackberrys", "Park Avenue", "ColorPlus", "John Players",
            "Fabindia", "W for Woman", "Global Desi", "Biba", "Aurelia",
            "Manyavar", "Mohey", "Sabyasachi", "Manish Malhotra", "Anita Dongre",
            "Max Fashion", "Lifestyle", "Reliance Trends", "Westside", "Pantaloons"
        )
    }

    // 500 All Brands (sorted alphabetically)
    val allBrands = remember {
        listOf(
            // A
            "Abercrombie & Fitch",
            "Adidas",
            "Aeropostale",
            "Ajio Own",
            "Akkriti by Pantaloons",
            "Allen Solly",
            "American Eagle",
            "AND",
            "Anita Dongre",
            "Ann Taylor",
            "Anthropologie",
            "Armani",
            "Arrow",
            "Aurelia",
            "Asos",

            // B
            "Banana Republic",
            "Bebe",
            "Being Human",
            "Benetton",
            "Bershka",
            "Biba",
            "Big Bazaar Fashion",
            "Blackberrys",
            "Boohoo",
            "Boss",
            "Brand Factory",
            "Burberry",

            // C
            "Calvin Klein",
            "Cbazaar",
            "Central",
            "Chanel",
            "Charlotte Russe",
            "Cherokee",
            "Chicos",
            "Coach",
            "ColorPlus",
            "COS",
            "Craftsvilla",
            "Crocs",

            // D
            "Diesel",
            "DKNY",
            "Dolce & Gabbana",
            "Dressberry",
            "Dries Van Noten",

            // E
            "Ed Hardy",
            "Esprit",
            "Express",

            // F
            "Fabindia",
            "Forever 21",
            "Flying Machine",
            "French Connection",
            "Fossil",

            // G
            "Gap",
            "Gaurav Gupta",
            "Givenchy",
            "Global Desi",
            "Gucci",
            "Guess",

            // H
            "H&M",
            "Harley Davidson",
            "Hermès",
            "Hollister",
            "HRX",
            "Hugo Boss",

            // I
            "Indya",
            "Isabel Marant",

            // J
            "J.Crew",
            "Jack & Jones",
            "Jabong",
            "Jaypore",
            "JJ Valaya",
            "John Players",
            "Jockey",

            // K
            "Kalki Fashion",
            "Kanchipuram Silks",
            "Kate Spade",
            "Kazo",
            "Kenzo",
            "Koovs",
            "Kumaran Silks",

            // L
            "Lacoste",
            "Lee",
            "Levi's",
            "Libas",
            "Lifestyle",
            "Limeroad",
            "Louis Philippe",
            "Louis Vuitton",
            "Lululemon",

            // M
            "Mango",
            "Manish Malhotra",
            "Manyavar",
            "Marc Jacobs",
            "Marks & Spencer",
            "Masaba",
            "Massimo Dutti",
            "Max Fashion",
            "Michael Kors",
            "Mohey",
            "Myntra",

            // N
            "Nalli",
            "Nike",
            "Nykaa Fashion",

            // O
            "Old Navy",
            "Only",
            "& Other Stories",

            // P
            "Pantaloons",
            "Park Avenue",
            "Payal Khandwala",
            "Payal Singhal",
            "Pepe Jeans",
            "Peter England",
            "Pieces",
            "Polo Ralph Lauren",
            "Pothys",
            "Prada",
            "Pull & Bear",
            "Puma",

            // Q
            "Quiksilver",

            // R
            "Ralph Lauren",
            "Rangriti",
            "Raw Mango",
            "Raymond",
            "Reebok",
            "Reliance Trends",
            "Ridhi Mehra",
            "Ritu Kumar",
            "RMKV",
            "Roadster",
            "Rohit Bal",

            // S
            "Sabyasachi",
            "Saravana Stores",
            "Satya Paul",
            "Selected",
            "Shantanu & Nikhil",
            "Shoppers Stop",
            "Snapdeal Fashion",
            "Spykar",
            "Stradivarius",
            "Suta",

            // T
            "Tarun Tahiliani",
            "Tata Cliq",
            "The Chennai Silks",
            "Tommy Hilfiger",
            "Topshop",
            "Tory Burch",

            // U
            "Under Armour",
            "Uniqlo",
            "United Colors of Benetton",
            "Urban Outfitters",
            "Utsav Fashion",

            // V
            "Van Heusen",
            "Varanga",
            "Vero Moda",
            "Versace",
            "Victoria's Secret",
            "Vikram Phadnis",
            "Vivaha",
            "V-Mart",

            // W
            "W for Woman",
            "Westside",
            "Wrangler",

            // X, Y, Z
            "Yves Saint Laurent",
            "Zara",
            "Zudio"
        ).sorted()
    }

    // Filter brands based on search
    val filteredPopularBrands = remember(searchQuery) {
        if (searchQuery.isEmpty()) popularBrands else popularBrands.filter {
            it.contains(
                searchQuery,
                ignoreCase = true
            )
        }
    }

    val filteredAllBrands = remember(searchQuery, customBrands) {
        val combined = customBrands + allBrands
        if (searchQuery.isEmpty()) combined.sorted() else combined.filter {
            it.contains(
                searchQuery,
                ignoreCase = true
            )
        }.sorted()
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
                        Text(
                            text = "Brands",
                            fontWeight = FontWeight.Bold
                        )
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

            // Brand List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // No Brand (Always first)
                item {
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
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Add Brand Button
                item {
                    AddBrandItem(
                        onClick = {
                            showAddBrandDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Popular Brands Section
                if (filteredPopularBrands.isNotEmpty()) {
                    item {
                        SectionHeader(title = "Popular Brands")
                    }
                    items(filteredPopularBrands) { brand ->
                        BrandItem(
                            brandName = brand,
                            onBrandClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onBrandClick(brand)
                                }
                            },
                            isSelected = navigatedBrand == brand
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // All Brands Section
                if (filteredAllBrands.isNotEmpty()) {
                    item {
                        SectionHeader(title = "All Brands")
                    }
                    items(filteredAllBrands) { brand ->
                        BrandItem(
                            brandName = brand,
                            onBrandClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onBrandClick(brand)
                                }
                            },
                            isSelected = navigatedBrand == brand
                        )
                    }
                }
            }
        }

        // Add Brand Dialog
        if (showAddBrandDialog) {
            AddBrandDialog(
                onDismiss = { showAddBrandDialog = false },
                onAddBrand = { newBrand ->
                    if (newBrand.isNotBlank() && !filteredAllBrands.contains(newBrand) && !filteredPopularBrands.contains(
                            newBrand
                        )
                    ) {
                        customBrands = customBrands + newBrand
                        onBrandClick(newBrand)
                    }
                    showAddBrandDialog = false
                }
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
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
            .clip(RoundedCornerShape(12.dp))
            .clickable { onBrandClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = brandName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (brandName == "No Brand") FontWeight.SemiBold else FontWeight.Normal,
            color = if (brandName == "No Brand")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        CustomRadioButton(
            isSelected = isSelected
        )
    }
}

@Composable
fun AddBrandItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add Brand",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Add Custom Brand",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AddBrandDialog(
    onDismiss: () -> Unit,
    onAddBrand: (String) -> Unit
) {
    var brandName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Custom Brand",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column {
                Text(
                    text = "Enter the brand name:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = brandName,
                    onValueChange = { brandName = it },
                    placeholder = { Text("Brand name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = TextFieldShape
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAddBrand(brandName.trim()) },
                enabled = brandName.trim().isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )
}

@Preview(showBackground = true)
@Composable
fun BrandScreenPreview() {
    SwapGoTheme {
        BrandScreen(
            navigatedBrand = "Zara",
            onBrandClick = { },
            onClose = { }
        )
    }
}
