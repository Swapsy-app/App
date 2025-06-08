@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.sell_screen.colors_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun ColorScreen(
    modifier: Modifier = Modifier,
    navigatedColor: String,
    onColorClick: (String) -> Unit,
    onClose: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var searchQuery by remember { mutableStateOf("") }

    val allColors = remember {
        listOf(
            ColorItem("Multi (if no colour is dominant)", Color(0xFF808080)), // Neutral gray
            ColorItem("Lavender", Color(0xFFE6E6FA)),
            ColorItem("Mauve", Color(0xFF993366)),
            ColorItem("Magenta", Color(0xFFFF00FF)),
            ColorItem("Purple", Color(0xFF800080)),
            ColorItem("Navy Blue", Color(0xFF000080)),
            ColorItem("Blue", Color(0xFF0000FF)),
            ColorItem("Cyan", Color(0xFF00FFFF)),
            ColorItem("Fluorescent Green", Color(0xFF39FF14)),
            ColorItem("Sea Green", Color(0xFF2E8B57)),
            ColorItem("Green", Color(0xFF008000)),
            ColorItem("Olive", Color(0xFF808000)),
            ColorItem("Lime Green", Color(0xFF32CD32)),
            ColorItem("Yellow", Color(0xFFFFFF00)),
            ColorItem("Gold", Color(0xFFFFD700)),
            ColorItem("Mustard", Color(0xFFFFDB58)),
            ColorItem("Orange", Color(0xFFFFA500)),
            ColorItem("Nude", Color(0xFFF2D2BD)),
            ColorItem("Tan", Color(0xFFD2B48C)),
            ColorItem("Khaki", Color(0xFFF0E68C)),
            ColorItem("Brown", Color(0xFFA52A2A)),
            ColorItem("Coffee Brown", Color(0xFF4B3621)),
            ColorItem("Coral", Color(0xFFFF7F50)),
            ColorItem("Red", Color(0xFFFF0000)),
            ColorItem("Rust", Color(0xFFB7410E)),
            ColorItem("Peach", Color(0xFFFFE5B4)),
            ColorItem("Pink", Color(0xFFFFC0CB)),
            ColorItem("Rose", Color(0xFFFF007F)),
            ColorItem("Maroon", Color(0xFF800000)),
            ColorItem("Burgundy", Color(0xFF800020)),
            ColorItem("White", Color(0xFFFFFFFF)),
            ColorItem("Cream", Color(0xFFFFFDD0)),
            ColorItem("Off White", Color(0xFFFAF0E6)),
            ColorItem("Beige", Color(0xFFF5F5DC)),
            ColorItem("Silver", Color(0xFFC0C0C0)),
            ColorItem("Grey", Color(0xFF808080)),
            ColorItem("Charcoal", Color(0xFF36454F)),
            ColorItem("Black", Color(0xFF000000))
        )
    }


    // Filter colors based on search
    val filteredColors = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            allColors
        } else {
            allColors.filter { it.name.contains(searchQuery, ignoreCase = true) }
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
                        Text(text = "Colour", fontWeight = FontWeight.Bold)
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

            // Instruction text
            Text(
                text = "Select the dominant colour",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Color List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(filteredColors) { colorItem ->
                    ColorItemRow(
                        colorItem = colorItem,
                        onColorClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onColorClick(colorItem.name)
                            }
                        },
                        isSelected = navigatedColor == colorItem.name
                    )
                }
            }
        }
    }
}

data class ColorItem(
    val name: String,
    val color: Color
)

@Composable
fun ColorItemRow(
    modifier: Modifier = Modifier,
    colorItem: ColorItem,
    onColorClick: () -> Unit,
    isSelected: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onColorClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color preview circle
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(colorItem.color)
                .then(
                    // Add border for white/light colors for visibility
                    if (colorItem.color == Color.White || colorItem.name.contains("White") || colorItem.name.contains("Cream") || colorItem.name.contains("Beige")) {
                        Modifier.background(
                            Color.Gray.copy(alpha = 0.3f),
                            CircleShape
                        )
                    } else {
                        Modifier
                    }
                )
        ) {
            // Inner color circle for white/light colors
            if (colorItem.color == Color.White || colorItem.name.contains("White") || colorItem.name.contains("Cream") || colorItem.name.contains("Beige")) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(colorItem.color)
                        .align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = colorItem.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected)
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
fun ColorScreenPreview() {
    SwapGoTheme {
        ColorScreen(
            navigatedColor = "Blue",
            onColorClick = { },
            onClose = { }
        )
    }
}
