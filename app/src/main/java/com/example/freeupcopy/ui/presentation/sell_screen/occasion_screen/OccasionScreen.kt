@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.sell_screen.occasion_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
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
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun OccasionScreen(
    modifier: Modifier = Modifier,
    navigatedOccasion: String = "",
    onOccasionSelected: (String) -> Unit, // ✅ Changed callback name
    onBackClick: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    var selectedOccasion by remember { mutableStateOf(navigatedOccasion) }

    // All occasions from the image
    val allOccasions = remember {
        listOf(
            "Daily",
            "Formal",
            "Festive",
//            "Maternity",
            "Party",
            "Wedding"
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = "Occasion",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
        // ✅ Removed bottomBar completely
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            // Occasion List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(allOccasions) { occasion ->
                    OccasionItem(
                        occasionName = occasion,
                        isSelected = selectedOccasion == occasion,
                        onOccasionClick = {
                            selectedOccasion = occasion
                            // ✅ Immediately call the callback when selection changes
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                onOccasionSelected(occasion)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OccasionItem(
    modifier: Modifier = Modifier,
    occasionName: String,
    isSelected: Boolean,
    onOccasionClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onOccasionClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = occasionName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        CustomRadioButton(
            isSelected = isSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OccasionScreenPreview() {
    SwapGoTheme {
        OccasionScreen(
            navigatedOccasion = "Daily",
            onOccasionSelected = { },
            onBackClick = { }
        )
    }
}
