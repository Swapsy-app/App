package com.example.freeupcopy.ui.presentation.sell_screen.size_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.sell.Size
import com.example.freeupcopy.data.remote.dto.sell.SizeAttribute
import com.example.freeupcopy.domain.enums.SizeType
import com.example.freeupcopy.ui.presentation.sell_screen.SellUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.SellViewModel
import com.example.freeupcopy.ui.theme.ButtonShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeScreen(
    modifier: Modifier = Modifier,
    sizeType: SizeType,
    sellViewModel: SellViewModel, // Add SellViewModel parameter
    sizeViewModel: SizeViewModel = hiltViewModel(),
    onSave: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by sizeViewModel.state.collectAsState()

    val context = LocalContext.current

    // Define which attributes to show based on sizeType
    val sizeAttributes = when (sizeType) {
        SizeType.BUST -> listOf("Bust")
        SizeType.BUST_WAIST_HIP -> listOf("Bust", "Waist", "Hip")
        SizeType.WAIST_HIP -> listOf("Waist", "Hip")
        SizeType.BRA -> listOf("Bra Size")
        SizeType.CHEST -> listOf("Chest")
        SizeType.CHEST_WAIST -> listOf("Chest", "Waist")
        SizeType.CHEST_WAIST_HIP -> listOf("Chest", "Waist", "Hip")
        SizeType.FOOTWEAR -> listOf("Footwear Size")
        SizeType.AGE -> listOf("Age")
    }

    // Sample values for each attribute (replace with your real data if needed)
    val attributeValues = mapOf(
        "Bust" to listOf(
            "30in / 76.2cm", "32in / 81.28cm", "34in / 86.36cm", "36in / 91.44cm",
            "38in / 96.52cm", "40in / 101.6cm", "42in / 106.68cm", "44in / 111.76cm",
            "46in / 116.84cm", "48in / 121.92cm", "50in / 127cm", "52in / 132.08cm",
            "54in / 137.16cm", "56in / 142.24cm", "58in / 147.32cm", "Free Size"
        ),
        "Waist" to listOf(
            "24in / 60.96cm", "26in / 66.04cm", "28in / 71.12cm", "30in / 76.2cm", "32in / 81.28cm", "34in / 86.36cm",
            "36in / 91.44cm", "38in / 96.52cm", "40in / 101.6cm", "42in / 106.68cm",
            "44in / 111.76cm", "46in / 116.84cm", "48in / 121.92cm", "50in / 127cm", "Free Size"
        ),
        "Hip" to listOf(
            "32in / 81.28cm", "34in / 86.36cm", "36in / 91.44cm", "38in / 96.52cm",
            "40in / 101.6cm", "42in / 106.68cm", "44in / 111.76cm", "48in / 121.92cm",
            "50in / 127cm", "52in / 132cm", "54in / 137.16cm", "56in / 142.24cm", "Free Size"
        ),
        "Bra Size" to listOf(
            "26AA", "26A", "26B", "28AA", "28A", "28B",
            "30A", "30B", "30C", "30D", "30DD","30E", "30F", "30G",
            "32A", "32B", "32C", "32D", "32DD", "32E", "32F", "32G",
            "34A", "34B", "34C", "34D", "34DD", "34E", "34F", "34G",
            "36A", "36B", "36C", "36D", "36DD", "36E", "36F", "36G",
            "38A", "38B", "38C", "38D", "38DD", "38E", "38F", "38G",
            "40A", "40B", "40C", "40D", "40DD", "40E", "40F", "40G",
            "42A", "42B", "42C", "42D", "42DD", "42E", "42F", "42G",
            "44A", "44B", "44C", "44D", "44DD", "44E", "44F", "44G",
            "46A", "46B", "46C", "46D", "46DD", "46E", "46F", "46G",
            "Free Size"
        ),
        "Chest" to listOf(
            "36in / 91.44cm", "38in / 96.52cm", "40in / 101.6cm", "42in / 106.68cm", "44in / 111.76cm",
            "46in / 116.84cm", "48in / 121.92cm", "Free Size"
        ),
        "Footwear Size" to listOf(
            "UK 4", "UK 4.5", "UK 5", "UK 5.5", "UK 6", "UK 6.5",
            "UK 7", "UK 7.5", "UK 8", "UK 8.5", "UK 9", "UK 9.5",
            "UK 10", "UK 10.5", "UK 11", "UK 11.5", "UK 12", "Free Size"
        ),
        "Age" to listOf(
            "Preemie", "Newborn", "0-3 Months", "3-6 Months", "6-9 Months",
            "9-12 Months", "12-18 Months", "18-24 Months", "2-4 Years", "4-6 Years",
            "6-8 Years", "8-10 Years", "10-12 Years", "12-14 Years", "14-16 Years",
            "Free Size"
        )
    )

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
                        text = "Select Size",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(ButtonShape)
                            .clickable {
                                // Validate and save size
                                val validation = sizeViewModel.validateSize()
                                if (validation.isValid) {
                                    // Create Size object from selected attributes
                                    val newSize = Size(
                                        attributes = if (state.selectedAttributes.isNotEmpty()) {
                                            state.selectedAttributes.map { (name, value) ->
                                                SizeAttribute(name, value)
                                            }
                                        } else null,
                                        freeSize = state.selectedAttributes.values.any { it == "Free Size" },
                                        sizeString = null // For dropdown selections, we use attributes
                                    )

                                    // Save to SellViewModel
                                    sellViewModel.onEvent(SellUiEvent.SizeChange(newSize))
                                    Log.e("SizeScreen", "Selected Size: $newSize")

                                    Toast.makeText(context, "Size saved successfully", Toast.LENGTH_SHORT).show()
                                    onSave()
                                } else {
                                    Toast.makeText(context, validation.errorMessage.orEmpty(), Toast.LENGTH_SHORT).show()
                                }
                            }
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Text(
                                text = "Save", color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        },
//        bottomBar = {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFFFFEB3B))
//                    .padding(vertical = 12.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Save",
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    modifier = Modifier.clickable {
//                        // Validate and save size
//                        val validation = sizeViewModel.validateSize()
//                        if (validation.isValid) {
//                            // Create Size object from selected attributes
//                            val newSize = Size(
//                                attributes = if (state.selectedAttributes.isNotEmpty()) {
//                                    state.selectedAttributes.map { (name, value) ->
//                                        SizeAttribute(name, value)
//                                    }
//                                } else null,
//                                freeSize = state.selectedAttributes.values.any { it == "Free Size" },
//                                sizeString = null // For dropdown selections, we use attributes
//                            )
//
//                            // Save to SellViewModel
//                            sellViewModel.onEvent(SellUiEvent.SizeChange(newSize))
//                            Log.e("SizeScreen", "Selected Size: $newSize")
//
//                            Toast.makeText(context, "Size saved successfully", Toast.LENGTH_SHORT).show()
//                            onSave()
//                        } else {
//                            Toast.makeText(context, validation.errorMessage.orEmpty(), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                )
//            }
//        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            sizeAttributes.forEach { attribute ->
                SizeAttributeDropdown(
                    attributeName = attribute,
                    values = attributeValues[attribute] ?: emptyList(),
                    selectedValue = state.selectedAttributes[attribute],
                    isExpanded = state.expandedAttribute == attribute,
                    onToggleExpansion = {
                        sizeViewModel.onEvent(SizeUiEvent.ToggleAttributeExpansion(attribute))
                    },
                    onValueSelected = { value ->
                        sizeViewModel.onEvent(SizeUiEvent.AttributeSelected(attribute, value))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun SizeAttributeDropdown(
    attributeName: String,
    values: List<String>,
    selectedValue: String?,
    isExpanded: Boolean,
    onToggleExpansion: () -> Unit,
    onValueSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F8F8))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
    ) {
        // Dropdown Header - Updated to show selected value
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpansion() }
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = attributeName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (selectedValue != null) {
                    Text(
                        text = selectedValue,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text(
                        text = "Select $attributeName",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }
        // Dropdown Content
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                values.forEach { value ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onValueSelected(value) }
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = selectedValue == value,
                            onClick = { onValueSelected(value) }
                        )
                    }
                }
            }
        }
    }
}
