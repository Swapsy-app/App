package com.example.freeupcopy.ui.presentation.product_listing.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.MAX_CASH_RANGE
import com.example.freeupcopy.common.Constants.MAX_COINS_RANGE
import com.example.freeupcopy.common.Constants.MIN_CASH_RANGE
import com.example.freeupcopy.common.Constants.MIN_COINS_RANGE
import com.example.freeupcopy.domain.enums.AvailabilityOption
import com.example.freeupcopy.domain.enums.ConditionOption
import com.example.freeupcopy.domain.enums.Filter
import com.example.freeupcopy.domain.enums.FilterCategoryUiModel
import com.example.freeupcopy.domain.enums.FilterTertiaryCategory
import com.example.freeupcopy.domain.enums.NewPricingModel
import com.example.freeupcopy.domain.enums.SellerBadge
import com.example.freeupcopy.domain.enums.SellerRatingOption
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.AnnouncementComposable
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor1
import com.example.freeupcopy.ui.theme.CoinColor2
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun FiltersBottomSheet(
    modifier: Modifier = Modifier,

    onClearFilters: () -> Unit,

    selectedFilter: Filter,
    onSelectedFilterClick: (Filter) -> Unit,

    onAvailabilityOptionClick: (AvailabilityOption) -> Unit,
    availabilityOptions: List<AvailabilityOption>,

    onConditionOptionClick: (ConditionOption) -> Unit,
    conditionOptions: List<ConditionOption>,

    onSellerRatingOptionClick: (SellerRatingOption) -> Unit,
    sellerRatingOptions: List<SellerRatingOption>,

    onSellerBadgeClick: (SellerBadge) -> Unit,
    sellerBadgeOptions: List<SellerBadge>,

    onPricingModelClick: (NewPricingModel) -> Unit,
    pricingModelOptions: List<NewPricingModel>,

    selectedCashRange: Pair<Float, Float>,
    selectedCoinRange: Pair<Float, Float>,
    onCashRangeChange: (Pair<Float, Float>) -> Unit,
    onCoinsRangeChange: (Pair<Float, Float>) -> Unit,

    selectedTertiaryCategories: List<FilterTertiaryCategory>,
    onTertiaryCategoryClick: (FilterTertiaryCategory) -> Unit,
    onRemoveSpecialOption: (FilterTertiaryCategory) -> Unit,
    onSelectAll: (FilterCategoryUiModel) -> Unit,

    availableFilters: List<Filter>,
    onApplyClick: () -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("") }
    var selectAll by remember { mutableStateOf(false) }

    val scrollStateFilterOptions = rememberScrollState()
    val scrollStateFilter = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .height(550.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Filters",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onClearFilters()
                        selectedCategory = ""
                        selectAll = false
                    },
                ),
                text = "Clear filters",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.35f)
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxHeight()
                    .verticalScroll(scrollStateFilter)
            ) {
                availableFilters.forEach { filter ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (filter == selectedFilter) MaterialTheme.colorScheme.primaryContainer
                                else Color.Transparent
                            )
                            .clickable { onSelectedFilterClick(filter) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                                text = filter.displayValue,
                                fontSize = 14.sp,
                                fontWeight = if (filter == selectedFilter) FontWeight.W500 else FontWeight.Normal,
                                color = if (filter == selectedFilter)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
                            val showIndicator = when (filter) {
                                Filter.AVAILABILITY -> availabilityOptions.isNotEmpty()
                                Filter.CONDITION -> conditionOptions.isNotEmpty()
                                Filter.SELLER_RATING -> sellerRatingOptions.isNotEmpty() || sellerBadgeOptions.isNotEmpty()
                                Filter.PRICE -> pricingModelOptions.isNotEmpty()
                                Filter.CATEGORY -> selectedTertiaryCategories.isNotEmpty()
                                Filter.SIZE -> false // Add your condition for SIZE if needed
                                Filter.BRAND -> false // Add your condition for BRAND if needed
                                Filter.FABRIC -> false // Add your condition for FABRIC if needed
                                Filter.COLOUR -> false // Add your condition for COLOUR if needed
                                Filter.OCCASION -> false // Add your condition for OCCASION if needed
                                Filter.SHAPE -> false // Add your condition for SHAPE if needed
                                Filter.LENGTH -> false // Add your condition for LENGTH if needed
                                else -> false
                            }

                            if (showIndicator) {
                                Spacer(Modifier.size(8.dp))
                                AppliedFilterIndicator()
                            }
                        }
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.08f),
                    )
                }

                if (!availableFilters.contains(Filter.SIZE)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (selectedFilter == Filter.SIZE) MaterialTheme.colorScheme.primaryContainer
                                else Color.Transparent
                            )
                            .clickable { onSelectedFilterClick(Filter.SIZE) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                text = "Size",
                                fontSize = 14.sp,
                                fontWeight = if (Filter.SIZE == selectedFilter) FontWeight.W500 else FontWeight.Normal,
                                color = if (Filter.SIZE == selectedFilter)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            )
//                            Box(
//                                modifier = Modifier
//                                    .size(10.dp)
//                                    .clip(CircleShape)
//                                    .background(MaterialTheme.colorScheme.primary)
//                            )
                        }
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.08f),
                    )
                }
            }

            // Available options
            Column(
                Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .verticalScroll(scrollStateFilterOptions)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                when (selectedFilter) {
                    Filter.AVAILABILITY -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AvailabilityOption.entries.forEach { availabilityOption ->
                                FilterOptionMultiSelectItem(
                                    filterOption = availabilityOption.displayValue,
                                    isChecked = availabilityOptions.contains(availabilityOption),
                                    onCheckedChange = {
                                        onAvailabilityOptionClick(availabilityOption)
                                    }
                                )
                            }
                        }
                    }

                    Filter.CONDITION -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ConditionOption.entries.forEach { conditionOption ->
                                FilterOptionMultiSelectItem(
                                    filterOption = conditionOption.displayValue,
                                    isChecked = conditionOptions.contains(conditionOption),
                                    onCheckedChange = {
                                        onConditionOptionClick(conditionOption)
                                    }
                                )
                            }
                        }
                    }

                    Filter.SELLER_RATING -> {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Seller Rating",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.size(8.dp))

                            SellerRatingOption.entries.forEach { sellerRatingOption ->
                                FilterOptionMultiSelectItem(
                                    filterOption = "${sellerRatingOption.displayValue} +",
                                    isChecked = sellerRatingOptions.contains(sellerRatingOption),
                                    onCheckedChange = {
                                        onSellerRatingOptionClick(sellerRatingOption)
                                    }
                                )
                                Spacer(Modifier.size(8.dp))
                            }

                            Spacer(Modifier.size(8.dp))

                            Text(
                                text = "Seller Badge",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.size(8.dp))

                            SellerBadge.entries.forEach { sellerBadge ->
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        modifier = Modifier.size(24.dp),
                                        checked = sellerBadgeOptions.contains(sellerBadge),
                                        onCheckedChange = {
                                            onSellerBadgeClick(sellerBadge)
                                        }
                                    )
                                    Spacer(Modifier.size(12.dp))
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(Modifier.size(8.dp))
                                    Text(
                                        text = sellerBadge.displayValue
                                    )
                                }
                                Spacer(Modifier.size(8.dp))
                            }
                        }
                    }

                    Filter.PRICE -> {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Select pricing model and Range",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 20.sp
                            )
                            Spacer(Modifier.size(12.dp))

                            NewPricingModel.entries.forEach { pricingModel ->
                                val isChecked = pricingModelOptions.contains(pricingModel)
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Checkbox(
                                        modifier = Modifier.size(24.dp),
                                        checked = isChecked,
                                        onCheckedChange = {
                                            onPricingModelClick(pricingModel)
                                        }
                                    )
                                    Text(text = pricingModel.displayValue)

//                                    if (isChecked) {
//                                        when (pricingModel) {
//                                            NewPricingModel.CASH -> {
//                                                Box(
//                                                    modifier = Modifier
//                                                        .clip(CircleShape)
//                                                        .border(1.dp, CashColor2, CircleShape)
//                                                        .background(CashColor1.copy(alpha = 0.3f))
//                                                        .padding(horizontal = 16.dp, vertical = 4.dp)
//                                                ) {
//                                                    Text(
//                                                        text = "₹${selectedCashRange.first.toInt()} - ₹${selectedCashRange.second.toInt()}",
//                                                        fontSize = 13.sp,
//                                                        color = MaterialTheme.colorScheme.primary,
//                                                        fontWeight = FontWeight.SemiBold
//                                                    )
//                                                }
//                                            }
//                                            NewPricingModel.COINS -> {
//                                                Box(
//                                                    modifier = Modifier
//                                                        .clip(CircleShape)
//                                                        .border(1.dp, CoinColor2, CircleShape)
//                                                        .background(CoinColor1.copy(alpha = 0.3f))
//                                                        .padding(horizontal = 10.dp, vertical = 4.dp)
//                                                ) {
//                                                    Row(verticalAlignment = Alignment.CenterVertically) {
//                                                        Icon(
//                                                            modifier = Modifier
//                                                                .padding(end = 2.dp)
//                                                                .size(16.dp),
//                                                            painter = painterResource(id = R.drawable.coin),
//                                                            contentDescription = null,
//                                                            tint = Color.Unspecified
//                                                        )
//                                                        Text(
//                                                            text = "${selectedCoinRange.first.toInt()} - ${selectedCoinRange.second.toInt()}",
//                                                            fontSize = 13.sp,
//                                                            color = MaterialTheme.colorScheme.primary,
//                                                            fontWeight = FontWeight.SemiBold
//                                                        )
//                                                    }
//                                                }
//                                            }
//                                            else -> {}
//                                        }
//                                    }
                                }

                                if (isChecked && pricingModel != NewPricingModel.CASH_AND_COINS) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 36.dp, top = 8.dp)
                                    ) {
                                        // Range labels
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = if (pricingModel == NewPricingModel.CASH)
                                                    "₹${if (pricingModel == NewPricingModel.CASH) selectedCashRange.first.toInt() else selectedCoinRange.first.toInt()}"
                                                else
                                                    "${if (pricingModel == NewPricingModel.CASH) selectedCashRange.first.toInt() else selectedCoinRange.first.toInt()}",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                            )
                                            Text(
                                                text = if (pricingModel == NewPricingModel.CASH)
                                                    "₹${if (pricingModel == NewPricingModel.CASH) selectedCashRange.second.toInt() else selectedCoinRange.second.toInt()}"
                                                else
                                                    "${if (pricingModel == NewPricingModel.CASH) selectedCashRange.second.toInt() else selectedCoinRange.second.toInt()}",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        // Double-sided range slider
                                        RangeSlider(
                                            modifier = Modifier.fillMaxWidth(),
                                            values = if (pricingModel == NewPricingModel.CASH)
                                                selectedCashRange.first..selectedCashRange.second
                                            else
                                                selectedCoinRange.first..selectedCoinRange.second,
                                            onValueChange = { range ->
                                                if (pricingModel == NewPricingModel.CASH) {
                                                    onCashRangeChange(Pair(range.start, range.endInclusive))
                                                } else {
                                                    onCoinsRangeChange(Pair(range.start, range.endInclusive))
                                                }
                                            },
                                            valueRange = if (pricingModel == NewPricingModel.CASH)
                                                MIN_CASH_RANGE..MAX_CASH_RANGE
                                            else
                                                MIN_COINS_RANGE..MAX_COINS_RANGE,
                                            colors = SliderDefaults.colors(
                                                thumbColor = if (pricingModel == NewPricingModel.CASH)
                                                    CashColor2 else CoinColor2,
                                                activeTrackColor = if (pricingModel == NewPricingModel.CASH)
                                                    CashColor1 else CoinColor1
                                            )
                                        )

                                        // Min/Max input fields (optional enhancement)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Min: ${if (pricingModel == NewPricingModel.CASH) "₹" else ""}${if (pricingModel == NewPricingModel.CASH) selectedCashRange.first.toInt() else selectedCoinRange.first.toInt()}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Max: ${if (pricingModel == NewPricingModel.CASH) "₹" else ""}${if (pricingModel == NewPricingModel.CASH) selectedCashRange.second.toInt() else selectedCoinRange.second.toInt()}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                } else if (isChecked && pricingModel == NewPricingModel.CASH_AND_COINS) {
                                    Text(
                                        modifier = Modifier.padding(start = 36.dp),
                                        text = "This option doesn't come with range",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                                        lineHeight = 18.sp
                                    )
                                }
                                Spacer(Modifier.size(12.dp))
                            }
                        }
                    }


                    Filter.CATEGORY -> {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (selectedCategory.isNotEmpty()) {
                                val currentCategory = FilterCategoryUiModel.predefinedCategories.find { it.name == selectedCategory }
                                val categoryTertiaryCategories = currentCategory?.subcategories
                                    ?.flatMap { it.tertiaryCategories }
                                    ?.filter { it.parentCategory == currentCategory.name } ?: emptyList()

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            modifier = Modifier
                                                .size(30.dp)
                                                .offset(x = (-4).dp),
                                            onClick = { selectedCategory = "" }
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                                contentDescription = "select category",
                                            )
                                        }
                                        Spacer(Modifier.size(4.dp))
                                        Text(
                                            text = selectedCategory,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    FilterOptionMultiSelectItem(
                                        modifier = Modifier.padding(top = 4.dp),
                                        filterOption = "Select All",
                                        isChecked = selectedTertiaryCategories.count { it.parentCategory == currentCategory?.name } ==
                                                categoryTertiaryCategories.size,
                                        onCheckedChange = {
                                            // Handle select all logic here using the filtered list for currentCategory
                                            onSelectAll(currentCategory!!)
                                        }
                                    )

                                    HorizontalDivider(
                                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
                                    )
                                }
                            } else {
                                Text(
                                    text = "Category",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(Modifier.size(12.dp))

                            if (selectedCategory.isEmpty()) {
                                FilterCategoryUiModel.predefinedCategories.forEach { category ->
                                    Row(
                                        modifier = modifier
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null,
                                                onClick = { selectedCategory = category.name }
                                            )
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Get tertiary categories for this category (filtered by parent)
                                        val categoryTertiaryCategories = category.subcategories
                                            .flatMap { it.tertiaryCategories }
                                            .filter { it.parentCategory == category.name }

                                        if (selectedTertiaryCategories.any { selected ->
                                                categoryTertiaryCategories.any { it.uniqueKey == selected.uniqueKey }
                                            }) {
                                            AppliedFilterIndicator()
                                            Spacer(Modifier.size(8.dp))
                                        }

                                        Text(
                                            text = category.name
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                            contentDescription = "select category",
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                                        )
                                    }
                                    Spacer(Modifier.size(8.dp))
                                }
                            } else {
                                val subCategories = FilterCategoryUiModel.predefinedCategories
                                    .find { it.name == selectedCategory }
                                    ?.subcategories.orEmpty()

                                subCategories.forEach { subCategory ->
                                    if (subCategory.name != "Primary") {
                                        Text(
                                            text = subCategory.name,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                                        )
                                        Spacer(Modifier.size(8.dp))
                                    }
                                    subCategory.tertiaryCategories.forEach { tertiaryCategory ->
                                        FilterOptionMultiSelectItem(
                                            filterOption = tertiaryCategory.name,
                                            isChecked = selectedTertiaryCategories.any { it.uniqueKey == tertiaryCategory.uniqueKey },
                                            onCheckedChange = {
                                                onTertiaryCategoryClick(tertiaryCategory)
                                                if (selectedTertiaryCategories.any { it.uniqueKey == tertiaryCategory.uniqueKey }) {
                                                    onRemoveSpecialOption(tertiaryCategory)
                                                }
                                            }
                                        )
                                        Spacer(Modifier.size(8.dp))
                                    }
                                    Spacer(Modifier.size(8.dp))
                                }
                            }
                        }
                    }


                    Filter.BRAND -> {

                    }
                    Filter.SIZE -> {
                        if (!availableFilters.contains(Filter.SIZE)) {
                            AnnouncementComposable(
                                text = stringResource(id = R.string.size_filter_text),
                                imageVector = Icons.Outlined.Info,
                                rotation = 0f
                            )
                        } else {

                        }
                    }
                    Filter.FABRIC -> {

                    }
                    Filter.COLOUR -> {

                    }
                    Filter.OCCASION -> {

                    }
                    Filter.SHAPE -> {

                    }
                    Filter.LENGTH -> {

                    }
                    else -> {

                    }
                }
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
        )

        Button(
            onClick = {
                onApplyClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .align(Alignment.CenterHorizontally),
            shape = ButtonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Apply Filters",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Composable
fun FilterOptionMultiSelectItem(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    filterOption: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange()
            },
            colors = CheckboxDefaults.colors(

            )
        )
        Text(
            text = filterOption,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun AppliedFilterIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(LinkColor)
    )
}

@Composable
fun RangeSlider(
    modifier: Modifier = Modifier,
    values: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    colors: SliderColors = SliderDefaults.colors()
) {
    androidx.compose.material3.RangeSlider(
        modifier = modifier,
        value = values,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,
        colors = colors
    )
}


@Preview(showBackground = true)
@Composable
private fun FiltersBottomSheetPreview() {
    SwapGoTheme {
        FiltersBottomSheet(
            selectedFilter = Filter.SIZE,
            onClearFilters = {},
            onSelectedFilterClick = {},
            onAvailabilityOptionClick = {},
            availabilityOptions = emptyList(),
            onConditionOptionClick = {},
            conditionOptions = emptyList(),
            onSellerRatingOptionClick = {},
            sellerRatingOptions = emptyList(),
            onSellerBadgeClick = {},
            sellerBadgeOptions = emptyList(),
            onPricingModelClick = {},
            pricingModelOptions = listOf(
                NewPricingModel.CASH,
                NewPricingModel.COINS,
                NewPricingModel.CASH_AND_COINS
            ),
            selectedCashRange = Pair(0f, 10000f),
            onCashRangeChange = {},
            selectedCoinRange = Pair(0f, 10000f),
            onCoinsRangeChange = {},
            selectedTertiaryCategories = emptyList(),
            onTertiaryCategoryClick = {},
            onRemoveSpecialOption = {},
            onSelectAll = {},
            availableFilters = listOf(
                Filter.AVAILABILITY,
                Filter.CONDITION,
                Filter.SELLER_RATING,
                Filter.PRICE,
                Filter.CATEGORY,
                Filter.BRAND
            ),
            onApplyClick = {}
        )
    }
}
