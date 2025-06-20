package com.example.freeupcopy.ui.presentation.product_listing.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

data class SortOption(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val description: String = "",
    val category: String = "General"
)
@Composable
fun SortBottomSheet(
    tempSortOption: String,
    onSortOptionSelected: (String) -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit,
    hasUserAddress: Boolean = true,
    onAddAddressClick: () -> Unit = {},
    selectedPriceTypes: List<String> = emptyList()
) {
    // Define categorized sort options with icons and descriptions
    val sortOptions = listOf(
        SortOption("Default", "default", Icons.Default.Person, "Random order", "General"),
        SortOption("Newest First", "newest", Icons.Default.Person, "Recently added items", "General"),
        SortOption("Oldest First", "oldest", Icons.Default.Person, "Oldest items first", "General"),
        SortOption("Fastest Delivery", "fastest-delivery", Icons.Default.Person,
            if (hasUserAddress) "Based on your location" else "Add address to enable", "Delivery"),

        // Cash price sorting
        SortOption("Cash: Low to High", "priceCashAsc", Icons.Default.Person, "Lowest cash price first", "Price"),
        SortOption("Cash: High to Low", "priceCashDesc", Icons.Default.Person, "Highest cash price first", "Price"),

        // Coin price sorting
        SortOption("Coin: Low to High", "priceCoinAsc", Icons.Default.Person, "Lowest coin price first", "Price"),
        SortOption("Coin: High to Low", "priceCoinDesc", Icons.Default.Person, "Highest coin price first", "Price")
    )

    // Filter options based on selected price types
    val filteredSortOptions = sortOptions.filter { option ->
        when {
            option.category != "Price" -> true
            selectedPriceTypes.isEmpty() -> false // Hide price options when no price types selected
            option.value.contains("Cash") -> selectedPriceTypes.contains("cash")
            option.value.contains("Coin") -> selectedPriceTypes.contains("coin")
            else -> true
        }
    }

    // Group options by category
    val groupedOptions = filteredSortOptions.groupBy { it.category }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sort Products",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (tempSortOption != "default") {
                    Text(
                        modifier = Modifier.clickable(
                            onClick = {
                                onSortOptionSelected("default")
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ),
                        text = "Reset",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sort options grouped by category
        LazyColumn {
            groupedOptions.forEach { (category, options) ->
                item {
                    // Category header
                    if (groupedOptions.size > 1) {
                        Text(
                            text = category,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }

                items(options) { option ->
                    SortOptionItem(
                        option = option,
                        isSelected = tempSortOption == option.value,
                        isEnabled = !(option.value == "fastest-delivery" && !hasUserAddress),
                        onClick = {
                            if (option.value == "fastest-delivery" && !hasUserAddress) {
                                onAddAddressClick()
                            } else {
                                onSortOptionSelected(option.value)
                            }
                        }
                    )
                }
            }

            // Show instructional text when no price types are selected
            if (selectedPriceTypes.isEmpty()) {
                item {
                    // Price category header
                    Text(
                        text = "Price",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                    )
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Price Sorting Unavailable",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Select price types in filters to enable price-based sorting options",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.5.dp
                )
            ) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = onApply,
                shape = ButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(
                    text = "Apply Sort",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
private fun SortOptionItem(
    option: SortOption,
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        !isEnabled -> MaterialTheme.colorScheme.surface
        else -> Color.Transparent
    }

    val contentColor = when {
        !isEnabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    // Use Box instead of Card to avoid unwanted borders
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)) // Use consistent rounded corners
            .background(backgroundColor)
            .clickable(enabled = isEnabled) { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = option.icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = option.label,
                    fontSize = 16.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                    color = contentColor
                )

                if (option.description.isNotEmpty()) {
                    Text(
                        text = option.description,
                        fontSize = 12.sp,
                        color = contentColor.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Selection indicator
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                // Remove CustomRadioButton to avoid extra visual elements
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SortBottomSheetPreview() {
    SwapGoTheme {
        SortBottomSheet(
            tempSortOption = "priceCashAsc",
            onSortOptionSelected = {},
            onApply = {},
            onDismiss = {},
            hasUserAddress = true,
            onAddAddressClick = {},
            selectedPriceTypes = listOf("cash", "coin")
        )
    }
}
