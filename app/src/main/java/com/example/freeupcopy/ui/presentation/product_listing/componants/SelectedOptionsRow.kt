@file:OptIn(ExperimentalLayoutApi::class)

package com.example.freeupcopy.ui.presentation.product_listing.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiState

@Composable
fun SelectedOptionsRow(
    uiState: ProductListingUiState,
    onOptionClicked: (String) -> Unit,
    isOptionSelected : (String) -> Boolean

) {
    val selectedOptions = listOfNotNull(
        "New Arrival",
        "Free in Coin",
        "New with Tags",
        "SwapGo Assured",
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        items(selectedOptions){option->
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if(isOptionSelected(option))MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .clickable { onOptionClicked(option) }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = option,
                            color = if (isOptionSelected(option)) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun Chip(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

