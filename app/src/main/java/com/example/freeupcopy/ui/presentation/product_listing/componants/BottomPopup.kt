@file:OptIn(ExperimentalLayoutApi::class)

package com.example.freeupcopy.ui.presentation.product_listing.componants

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.FilterOption
import com.example.freeupcopy.domain.model.FilterSection
import com.example.freeupcopy.ui.presentation.product_listing.FilterClassOptions
import com.example.freeupcopy.ui.presentation.product_listing.SortOption
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.CoinColor2
import com.example.freeupcopy.ui.theme.OfferColor2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomPopup(

    isFilterSelected : Boolean,
    isSortSelected : Boolean,

    sortOption: SortOption,
    changeSortToRec : () -> Unit,
    changeSortToPriceLoToHi : () -> Unit,
    changeSortToPriceHiToLo: () -> Unit,

    isAvailablitySelected: Boolean,
    onAvailablitySelected: () -> Unit,

    isAvailableSelected: Boolean,
    onAvailableSelected: () -> Unit,

    isSoldOutSelected: Boolean,
    onSoldOutSelected: () -> Unit,

    isConditionSelected: Boolean,
    onConditionSelected: () -> Unit,

    isNewWithTagsSelected: Boolean,
    onNewWithTagsSelected: () -> Unit,

    isLikeNewSelected: Boolean,
    onLikeNewSelected: () -> Unit,

    isGoodSelected: Boolean,
    onGoodSelected: () -> Unit,

    isUsedSelected: Boolean,
    onUsedSelected: () -> Unit,

    isSellerRatingSelected: Boolean,
    onSellerRatingSelected: () -> Unit,

    isRating4_0Selected: Boolean,
    onRating4_0Selected: () -> Unit,

    isRating4_5Selected: Boolean,
    onRating4_5Selected: () -> Unit,

    isRating4_7Selected: Boolean,
    onRating4_7Selected: () -> Unit,

    isPriceSelected: Boolean,
    onPriceSelected: () -> Unit,

    isSellerActiveSelected: Boolean,
    onSellerActiveSelected: () -> Unit,

    isSellerActiveThisWeekSelected: Boolean,
    onSellerActiveThisWeekSelected: () -> Unit,

    isSellerActiveThisMonthSelected: Boolean,
    onSellerActiveThisMonthSelected: () -> Unit,

    isCategorySelected: Boolean,
    onCategorySelected: () -> Unit,

    isSizeSelected: Boolean,
    onSizeSelected: () -> Unit,

    isCashSelected : Boolean,
    onClickCash : () -> Unit,
    cashSelected: Float,
    onCashChange: (Float) -> Unit,
    isCoinSelected : Boolean,
    onClickCoin : () -> Unit,
    coinsSelected: Float,
    onCoinsChange: (Float) -> Unit,
    isOfferSelected : Boolean,
    onOfferSelect : (Boolean) -> Unit,
//    bottomSheetState : SheetState,
    onDismissListener : () -> Unit,

    filterSectionSelected : FilterClassOptions?,

    filterSections: List<FilterSection> = listOf(
        FilterSection(
            name = "Availablity",
            onCLick = onAvailablitySelected,
            isSelected = isAvailablitySelected,
            options = listOf(
                FilterOption(
                    name = "Available",
                    onCLick = onAvailableSelected,
                    isSelected = isAvailableSelected
                ),
                FilterOption(
                    name = "Sold Out",
                    onCLick = onSoldOutSelected,
                    isSelected = isSoldOutSelected,
                )
            )
        ),
        FilterSection(
            name = "Condition",
            onCLick = onConditionSelected,
            isSelected = isConditionSelected,
            options = listOf(
                FilterOption(
                    name = "New with Tag",
                    onCLick = onNewWithTagsSelected,
                    isSelected = isNewWithTagsSelected
                ),
                FilterOption(
                    name = "Like New",
                    onCLick = onLikeNewSelected,
                    isSelected = isLikeNewSelected,
                ),
                FilterOption(
                    name = "Good",
                    onCLick = onGoodSelected,
                    isSelected = isGoodSelected,
                ),
                FilterOption(
                    name = "Used",
                    onCLick = onUsedSelected,
                    isSelected = isUsedSelected,
                )
            )
        ),
        FilterSection(
            name = "Seller Rating",
            onCLick = onSellerRatingSelected,
            isSelected = isSellerRatingSelected,
            options = listOf(
                FilterOption(
                    name = "4.7 and above",
                    onCLick = onRating4_7Selected,
                    isSelected = isRating4_7Selected
                ),
                FilterOption(
                    name = "4.5 and above",
                    onCLick = onRating4_5Selected,
                    isSelected = isRating4_5Selected,
                ),
                FilterOption(
                    name = "4 and above",
                    onCLick = onRating4_0Selected,
                    isSelected = isRating4_0Selected,
                )
            )
        ),
        FilterSection(
            name = "Price",
            onCLick = onPriceSelected,
            isSelected = isPriceSelected,
            options = listOf()
        ),
        FilterSection(
            name = "Seller Activity",
            onCLick = onSellerActiveSelected,
            isSelected = isSellerActiveSelected,
            options = listOf(
                FilterOption(
                    name = "Seller Active this Week",
                    onCLick = onSellerActiveThisWeekSelected,
                    isSelected = isSellerActiveThisWeekSelected
                ),
                FilterOption(
                    name = "Seller Active this Month",
                    onCLick = onSellerActiveThisMonthSelected,
                    isSelected = isSellerActiveThisMonthSelected,
                ),
            )
        ),
        FilterSection(
            name = "Category",
            onCLick = onCategorySelected,
            isSelected = isCategorySelected,
            options = listOf()
        ),
        FilterSection(
            name = "Size",
            onCLick = onSizeSelected,
            isSelected = isSizeSelected,
            options = listOf()
        ),
    )
) {
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
        onDismissRequest = {
            onDismissListener()
        }
    ) {
        if(isFilterSelected){
            Row(
                modifier = Modifier
                    .padding(32.dp, 0.dp, 32.dp, 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Filters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(32.dp))
                Icon(imageVector = Icons.Rounded.List, contentDescription = "filter icon")
            }
            Row {
                LazyColumn(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color.Black
                        )
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                        .fillMaxHeight(),
                ) {
                    items(filterSections){
                        FilterSectionUI(
                            title = it.name,
                            isSelected = it.isSelected,
                            onSelected = {
                                it.onCLick()
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color.Black
                        )
                        .fillMaxWidth()
                        .padding(16.dp)
                        .fillMaxHeight(),
                ) {
                    item {
                        when(filterSectionSelected){
                            FilterClassOptions.Availiblity -> {
                                filterSections[0].options.forEach {
                                    FlowRow {
                                        FilterOptionUI(
                                            label = it.name,
                                            isSelected = it.isSelected,
                                            onClick = it.onCLick
                                        )
                                    }
                                }
                            }
                            FilterClassOptions.Condition -> {
                                FlowRow {
                                    filterSections[1].options.forEach {
                                        FilterOptionUI(
                                            label = it.name,
                                            isSelected = it.isSelected,
                                            onClick = it.onCLick
                                        )
                                    }
                                }
                            }
                            FilterClassOptions.SellerRating -> {
                                FlowRow {
                                    filterSections[2].options.forEach {
                                        FilterOptionUI(
                                            label = it.name,
                                            isSelected = it.isSelected,
                                            onClick = it.onCLick
                                        )
                                    }
                                }
                            }
                            FilterClassOptions.Price -> {
                                Column(modifier = Modifier.padding(16.dp)) {
//                            // Cash Slider
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_cash),
                                            contentDescription = "cash icon",
                                            tint = CashColor2
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = cashSelected.toInt().toString(),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Checkbox(
                                            checked = isCashSelected,
                                            onCheckedChange = { onClickCash() }
                                        )
                                    }
                                    if(isCashSelected){
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .rotate(180f) // Rotate the slider 180 degrees
                                        ) {
                                            val priceInverted = 50000 - cashSelected
                                            Slider(
                                                value = priceInverted,
                                                onValueChange = {
                                                    onCashChange(50000 - it)
                                                },
                                                valueRange = 0f..50000f,
                                                steps = 4999,
                                                modifier = Modifier.fillMaxWidth(),
                                                colors = SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.primary,
                                                    activeTrackColor = MaterialTheme.colorScheme.primary.copy(
                                                        alpha = 0.5f
                                                    ), // Left side color
                                                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                                        alpha = 0.3f
                                                    ) // Right side color
                                                )
                                            )
                                        }
                                    }

//
                                    Spacer(modifier = Modifier.height(16.dp))
//
//                            // Coins Slider
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_coin),
                                            contentDescription = "coin icon",
                                            tint = CoinColor2
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = coinsSelected.toInt().toString(),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Checkbox(
                                            checked = isCoinSelected,
                                            onCheckedChange = { onClickCoin() }
                                        )
                                    }
                                    if(isCoinSelected){
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .rotate(180f) // Rotate the slider 180 degrees
                                        ) {
                                            val coinsInverted = 50000 - coinsSelected
                                            Slider(
                                                value = coinsInverted,
                                                onValueChange = { onCoinsChange(50000 - it) },
                                                valueRange = 0f..50000f,
                                                steps = 4999,
                                                modifier = Modifier.fillMaxWidth(),
                                                colors = SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.primary,
                                                    activeTrackColor = MaterialTheme.colorScheme.primary.copy(
                                                        alpha = 0.5f
                                                    ), // Left side color
                                                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                                        alpha = 0.3f
                                                    ) // Right side color
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_cash),
                                            contentDescription = "cash icon",
                                            tint = CashColor2
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Add,
                                            contentDescription = "plus icon",
                                            tint = OfferColor2
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_coin),
                                            contentDescription = "coin icon",
                                            tint = CoinColor2
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Checkbox(
                                            checked = isOfferSelected,
                                            onCheckedChange = { onOfferSelect(it) }
                                        )
                                    }
                                }
                            }
                            FilterClassOptions.SellerActive -> {
                                FlowRow {
                                    filterSections[4].options.forEach {
                                        FilterOptionUI(
                                            label = it.name,
                                            isSelected = it.isSelected,
                                            onClick = it.onCLick
                                        )
                                    }
                                }
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        }
        else if (isSortSelected) {
            Row(
                modifier = Modifier
                    .padding(32.dp, 0.dp, 32.dp, 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Sort",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(32.dp))
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Sort icon")
            }
            Divider(modifier = Modifier.fillMaxWidth())
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                FilterSectionUI(
                    title = "Recommended",
                    isSelected = sortOption == SortOption.Recommended,
                    onSelected = {
                        changeSortToRec()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                FilterSectionUI(
                    title = "Price (Low To High)",
                    isSelected = sortOption == SortOption.PriceLowToHigh,
                    onSelected = {
                        changeSortToPriceLoToHi()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                FilterSectionUI(
                    title = "Price (High To Low)",
                    isSelected = sortOption == SortOption.PriceHighToLow,
                    onSelected = {
                        changeSortToPriceHiToLo()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }



//        LazyColumn(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Availability Section
//            item {
//                FilterSection(
//                    title = "Availability",
//                    isSelected = isAvailablitySelected,
//                    onSelected = onAvailablitySelected
//                ) {
//                    FilterOption(
//                        label = "Available",
//                        isSelected = isAvailableSelected,
//                        onClick = onAvailableSelected
//                    )
//                    FilterOption(
//                        label = "Sold Out",
//                        isSelected = isSoldOutSelected,
//                        onClick = onSoldOutSelected
//                    )
//                }
//            }
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Condition Section
//            item {
//                FilterSection(
//                    title = "Condition",
//                    isSelected = isConditionSelected,
//                    onSelected = onConditionSelected
//                ) {
//                    FilterOption(
//                        label = "New With Tags",
//                        isSelected = isNewWithTagsSelected,
//                        onClick = onNewWithTagsSelected
//                    )
//                    FilterOption(
//                        label = "Like New",
//                        isSelected = isLikeNewSelected,
//                        onClick = onLikeNewSelected
//                    )
//                    FilterOption(
//                        label = "Good",
//                        isSelected = isGoodSelected,
//                        onClick = onGoodSelected
//                    )
//                    FilterOption(
//                        label = "Used",
//                        isSelected = isUsedSelected,
//                        onClick = onUsedSelected
//                    )
//                }
//            }
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Seller Rating Section
//            item {
//                FilterSection(
//                    title = "Seller Rating",
//                    isSelected = isSellerRatingSelected,
//                    onSelected = onSellerRatingSelected
//                ) {
//                    FilterOption(
//                        label = "4.0+",
//                        isSelected = isRating4_0Selected,
//                        onClick = onRating4_0Selected
//                    )
//                    FilterOption(
//                        label = "4.5+",
//                        isSelected = isRating4_5Selected,
//                        onClick = onRating4_5Selected
//                    )
//                    FilterOption(
//                        label = "4.7+",
//                        isSelected = isRating4_7Selected,
//                        onClick = onRating4_7Selected
//                    )
//                }
//            }
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Price Section
//            item {
//                FilterSection(
//                    title = "Price",
//                    isSelected = isPriceSelected,
//                    onSelected = onPriceSelected
//                ) {
//
//                    if (isPriceSelected) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            // Cash Slider
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_cash),
//                                    contentDescription = "cash icon",
//                                    tint = CashColor2
//                                )
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text(
//                                    text = cashSelected.toInt().toString(),
//                                    fontWeight = FontWeight.SemiBold,
//                                    fontSize = 16.sp
//                                )
//                            }
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .rotate(180f) // Rotate the slider 180 degrees
//                            ) {
//                                val priceInverted = 50000 - cashSelected
//                                Slider(
//                                    value = priceInverted,
//                                    onValueChange = {
//                                        onCashChange(50000 - it)
//                                    },
//                                    valueRange = 0f..50000f,
//                                    steps = 4999,
//                                    modifier = Modifier.fillMaxWidth(),
//                                    colors = SliderDefaults.colors(
//                                        thumbColor = MaterialTheme.colorScheme.primary,
//                                        activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // Left side color
//                                        inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) // Right side color
//                                    )
//                                )
//                            }
//
////
//                            Spacer(modifier = Modifier.height(16.dp))
////
////                            // Coins Slider
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_coin),
//                                    contentDescription = "coin icon",
//                                    tint = CoinColor2
//                                )
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text(
//                                    text = coinsSelected.toInt().toString(),
//                                    fontWeight = FontWeight.SemiBold,
//                                    fontSize = 16.sp
//                                )
//                            }
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .rotate(180f) // Rotate the slider 180 degrees
//                            ) {
//                                val coinsInverted = 50000 - coinsSelected
//                                Slider(
//                                    value = coinsInverted,
//                                    onValueChange = { onCoinsChange(50000 - it) },
//                                    valueRange = 0f..50000f,
//                                    steps = 4999,
//                                    modifier = Modifier.fillMaxWidth(),
//                                    colors = SliderDefaults.colors(
//                                        thumbColor = MaterialTheme.colorScheme.primary,
//                                        activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // Left side color
//                                        inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) // Right side color
//                                    )
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(16.dp))
//
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.Start,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_cash),
//                                    contentDescription = "cash icon",
//                                    tint = CashColor2
//                                )
//                                Spacer(modifier = Modifier.width(6.dp))
//                                Icon(
//                                    imageVector = Icons.Rounded.Add,
//                                    contentDescription = "plus icon",
//                                    tint = OfferColor2
//                                )
//                                Spacer(modifier = Modifier.width(6.dp))
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_coin),
//                                    contentDescription = "coin icon",
//                                    tint = CoinColor2
//                                )
//                                Spacer(modifier = Modifier.width(16.dp))
//                                Checkbox(
//                                    checked = isOfferSelected,
//                                    onCheckedChange = { onOfferSelect(it) }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Seller Active Section
//            item {
//                FilterSection(
//                    title = "Seller Active",
//                    isSelected = isSellerActiveSelected,
//                    onSelected = onSellerActiveSelected
//                ) {
//                    FilterOption(
//                        label = "This Week",
//                        isSelected = isSellerActiveThisWeekSelected,
//                        onClick = onSellerActiveThisWeekSelected
//                    )
//                    FilterOption(
//                        label = "This Month",
//                        isSelected = isSellerActiveThisMonthSelected,
//                        onClick = onSellerActiveThisMonthSelected
//                    )
//                }
//            }
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Category Section
//            item {
//                FilterSection(
//                    title = "Category",
//                    isSelected = isCategorySelected,
//                    onSelected = onCategorySelected
//                )
//            }
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Size Section
//            item {
//                FilterSection(
//                    title = "Size",
//                    isSelected = isSizeSelected,
//                    onSelected = onSizeSelected
//                )
//            }
//        }

    }
}

@Composable
fun FilterSectionUI(
    title: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                // This disables the ripple effect
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Disable the ripple effect
            ) { onSelected() }
            .border(
                1.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                RoundedCornerShape(16.dp)
            )
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(ButtonShape),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            color = if(isSelected) Color.White else Color.Black
        )
    }
}


@Composable
fun FilterOptionUI(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textSize = listOf(16.dp, 12.dp)
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(
                if (isSelected) 1.5.dp else 1.dp,
                Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .pointerInput(Unit) {
                // This disables the ripple effect
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Disable the ripple effect
            ) { onClick() }
    ) {
        val sizeDp by animateDpAsState(targetValue = if (isSelected) textSize[0] else textSize[1])
        val density = LocalDensity.current
        val size = with(density) { sizeDp.toSp() }
        Text(
            text = label,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = size
        )
    }
}

@Preview(showBackground = true) @Composable
fun PreviewFilterSideBar(){
    BottomPopup(
        isAvailablitySelected = false,
        onAvailablitySelected = { /*TODO*/ },
        isAvailableSelected = false,
        onAvailableSelected = { /*TODO*/ },
        isSoldOutSelected = false,
        onSoldOutSelected = { /*TODO*/ },
        isConditionSelected = false,
        onConditionSelected = { /*TODO*/ },
        isNewWithTagsSelected = false,
        onNewWithTagsSelected = { /*TODO*/ },
        isLikeNewSelected = false,
        onLikeNewSelected = { /*TODO*/ },
        isGoodSelected = false,
        onGoodSelected = { /*TODO*/ },
        isUsedSelected = false,
        onUsedSelected = { /*TODO*/ },
        isSellerRatingSelected = false,
        onSellerRatingSelected = { /*TODO*/ },
        isRating4_0Selected = false,
        onRating4_0Selected = { /*TODO*/ },
        isRating4_5Selected = false,
        onRating4_5Selected = { /*TODO*/ },
        isRating4_7Selected = false,
        onRating4_7Selected = { /*TODO*/ },
        isPriceSelected = false,
        onPriceSelected = { /*TODO*/ },
        isSellerActiveSelected = false,
        onSellerActiveSelected = { /*TODO*/ },
        isSellerActiveThisWeekSelected = false,
        onSellerActiveThisWeekSelected = { /*TODO*/ },
        isSellerActiveThisMonthSelected = false,
        onSellerActiveThisMonthSelected = { /*TODO*/ },
        isCategorySelected = false,
        onCategorySelected = { /*TODO*/ },
        isSizeSelected = false,
        onSizeSelected = { /*TODO*/ },
        cashSelected = 500f,
        onCashChange = {},
        coinsSelected = 500f,
        onCoinsChange = {},
        isOfferSelected = false,
        onOfferSelect = {},
        onDismissListener = {},
        filterSectionSelected = null,
        onClickCash = {},
        onClickCoin = {},
        isCashSelected = true,
        isCoinSelected = true,
        isFilterSelected = true,
        isSortSelected = false,
        sortOption = SortOption.Recommended,
        changeSortToRec = {},
        changeSortToPriceHiToLo = {},
        changeSortToPriceLoToHi = {}
    )
}



