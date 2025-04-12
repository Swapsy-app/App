@file:OptIn(ExperimentalLayoutApi::class)

package com.example.freeupcopy.ui.presentation.product_listing.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.domain.enums.FilterSpecialOption
import com.example.freeupcopy.ui.presentation.home_screen.componants.settleAppBar
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiState
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.SwapGoTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedOptionsRow(
    modifier: Modifier = Modifier,
    onOptionClicked: (FilterSpecialOption) -> Unit,
    selectedFilterSpecialOptions: List<FilterSpecialOption>,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {

    var heightOffsetLimit by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(heightOffsetLimit) {
        if (scrollBehavior?.state?.heightOffsetLimit != heightOffsetLimit) {
            scrollBehavior?.state?.heightOffsetLimit = heightOffsetLimit
        }
    }

    // Get the overlap fraction for color transition
    val colorTransitionFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
    val fraction = if (colorTransitionFraction > 0.01f) 1f else 0f

    // Set up draggable modifier for the row
    val rowDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier.then(rowDragModifier)
    ) {
        Layout(
            content = {
                Column {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(FilterSpecialOption.entries) { option ->
                            val isOptionSelected = selectedFilterSpecialOptions.contains(option)
                            Row {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(
                                            width = 1.dp,
                                            color = if (isOptionSelected) LinkColor.copy(0.5f)
                                            else MaterialTheme.colorScheme.onPrimaryContainer.copy(0.12f),
                                            shape = CircleShape
                                        )
                                        .background(
                                            if (isOptionSelected) MaterialTheme.colorScheme.secondaryContainer
                                            else MaterialTheme.colorScheme.primaryContainer
                                        )
                                        .clickable { onOptionClicked(option) }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = option.displayValue,
                                        color = if (isOptionSelected) MaterialTheme.colorScheme.onPrimaryContainer
                                        else MaterialTheme.colorScheme.onPrimaryContainer.copy(0.6f),
                                        fontSize = 14.sp,
                                        fontWeight = if (isOptionSelected) FontWeight.W500 else FontWeight.Normal,
                                    )
                                }
                            }
                        }
                    }

//                    HorizontalDivider(
//                        thickness = 1.dp,
//                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.12f)
//                    )
                }
            },
            measurePolicy = { measurables, constraints ->
                val placeable = measurables.first().measure(constraints.copy(minWidth = 0))
                heightOffsetLimit = placeable.height.toFloat() * -1
                val scrollOffset = scrollBehavior?.state?.heightOffset ?: 0f
                val height = placeable.height.toFloat() + scrollOffset
                val layoutHeight = height.roundToInt()
                layout(constraints.maxWidth, layoutHeight) {
                    placeable.place(0, scrollOffset.toInt())
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewSelectedOptionsRow() {
    SwapGoTheme {
        SelectedOptionsRow(
            onOptionClicked = {},
            selectedFilterSpecialOptions = listOf(FilterSpecialOption.NEW_ARRIVAL),
        )
    }
}

