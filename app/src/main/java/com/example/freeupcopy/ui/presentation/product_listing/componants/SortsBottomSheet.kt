package com.example.freeupcopy.ui.presentation.product_listing.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun SortBottomSheet(
    tempSortOption: String,
    onSortOptionSelected: (String) -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    // Define a list of sort options.
    // The value is what you might pass as a query parameter (and the label is shown in the UI)
    val sortOptions = listOf(
        "Default" to "default",
        "Newest" to "newest",          // e.g., sort by creation date descending
        "Oldest" to "oldest",          // e.g., sort by creation date ascending
        "Price: Low to High" to "priceAsc",  // for price ascending
        "Price: High to Low" to "priceDesc"  // for price descending
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Sort By",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display sort options.
        sortOptions.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ButtonShape)
                    .clickable {
                        onSortOptionSelected(value)
                    }
                    .padding(vertical = 6.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomRadioButton(
                    isSelected = tempSortOption == value
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = label,
                    fontWeight = if (tempSortOption == value)
                        FontWeight.W500
                    else FontWeight.Normal,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onClick = onDismiss,
                shape = ButtonShape
            ) {
                Text(text = "Cancel")
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
                Text(text = "Apply")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SortBottomSheetPreview() {
    SwapGoTheme {
        SortBottomSheet(
            tempSortOption = "default",
            onSortOptionSelected = {},
            onApply = {},
            onDismiss = {}
        )
    }
}