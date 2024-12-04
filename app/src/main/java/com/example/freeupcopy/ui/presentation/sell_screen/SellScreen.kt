@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.freeupcopy.ui.presentation.sell_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss
import com.example.freeupcopy.utils.dashedBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: () -> String,
    selectedWeight: String,
    selectedCategory: String,
    selectedBrand: String,
    selectedCondition: String,
    selectedCountry: String,
    selectedLocation: String,
    //selectedGst: String,
    onBrandClick: () -> Unit,
    onConditionClick: () -> Unit,
    onWeightClick: () -> Unit,
    onManufacturingClick: () -> Unit,
    onPriceClick: () -> Unit,
    onLocationClick: () -> Unit,
    onAdvanceSettingClick: () -> Unit,
    onClose: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    //var chosenCategory by remember { mutableStateOf("") }
    //var selectedWeight by remember { mutableStateOf(selectedWeight1) }
    //var selectedBrand by remember { mutableStateOf(selectedBrand1) }
    //var selectedCondition by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Selling Details")
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
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.85f))
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp)

                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(0.70f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { }
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Save", color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { }
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sell", color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                CategorySection(
                    chosenCategory = selectedCategory,
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onCategoryClick()
                        }
                    }
                )
            }
            item {
                DetailsSection()
            }

            item {
                PickupLocationSection(
                    location = selectedLocation,
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onLocationClick()
                        }
                    }
                )
            }

            item {
                SpecificationSection(
                    selectedWeight = selectedWeight,
                    selectedBrand = selectedBrand,
                    selectedCondition = selectedCondition,
                    selectedCountry = selectedCountry,
                    onManufacturingClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onManufacturingClick()
                        }
                    },
                    onBrandClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onBrandClick()
                        }
                    },
                    onConditionClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onConditionClick()
                        }
                    },
                    onWeightClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onWeightClick()
                        }
                    },
                    selectedTertiaryCategory = selectedCategory
                )
            }

            item {
                KeyInfoSection(
                    price = Price("0.0", "INR"),
                    onPriceClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onPriceClick()
                        }
                    }
                )
            }
            item {
                AdvancedSellerSettingSection(
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onAdvanceSettingClick()
                        }
                    }
                )
            }
            item {

            }
        }
    }
}

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    chosenCategory: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isCategoryChosen = chosenCategory.isEmpty()
        Text(text = if (isCategoryChosen) "Choose a category" else "Category")
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = chosenCategory,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = "Choose a category",
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsSection(
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val items = listOf(7,)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Product Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            FlowRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items.forEachIndexed { index, i ->

                    AddPhotoBox()
                }
                if (items.size != 11) {
                    AddMoreBox(
                        onClick = {
                        }
                    )
                }
            }
            if (items.size == 1) {
                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "info",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        fontSize = 13.5.sp,
                        lineHeight = 16.sp,
                        text = "Add clear and attractive photos to showcase your product and attract more buyers.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.size(22.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        "Enter Title",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                },
                label = { Text("Title") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = description,
                minLines = 3,
                onValueChange = {
                    description = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        "Enter Title",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                },
                label = { Text("Description") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
fun AddMoreBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .dashedBorder(
                strokeWidth = 3.dp,
                gapLength = 6.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Rounded.Add,
            contentDescription = "add"
        )
    }
}

@Composable
fun AddPhotoBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .dashedBorder(
                strokeWidth = 3.dp,
                gapLength = 6.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                alpha = 0.3f,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                painter = painterResource(id = R.drawable.add_image),
                contentDescription = "add image"
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                text = "Add photo",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun SpecificationSection(
    modifier: Modifier = Modifier,
    selectedTertiaryCategory: String,
    selectedWeight: String,
    selectedBrand: String,
    selectedCondition: String,
    selectedCountry: String,
    onManufacturingClick: () -> Unit,
    onBrandClick: () -> Unit,
    onConditionClick: () -> Unit,
    onWeightClick: () -> Unit
) {
    var price by remember { mutableStateOf("") }
//    var selectedBrand by remember { mutableStateOf("") }
//    var selectedCondition by remember { mutableStateOf("") }
//    var selectedWeight by remember { mutableStateOf(Weight.predefinedWeight) }
    var size by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Specifications",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = modifier.fillMaxWidth()
            ) {

                CommonSpecification(
                    initialLabel = "Product Condition",
                    valueLabel = "Condition",
                    value = selectedCondition,
                    onClick = {
                        onConditionClick()
                    }
                )

                CustomDivider()

                CommonSpecification(
                    initialLabel = "Choose a Brand",
                    valueLabel = "Brand",
                    value = selectedBrand,
                    onClick = {
                        onBrandClick()
                    }
                )
                CustomDivider()

                CommonSpecification(
                    initialLabel = "Manufacturing Country",
                    valueLabel = "Manufacturing Country",
                    value = selectedCountry,
                    onClick = {
                        onManufacturingClick()
                    }
                )

                CustomDivider()

                CommonSpecification(
                    initialLabel = "Weight",
                    valueLabel = "Weight",
                    value = selectedWeight,
                    onClick = {
                        onWeightClick()
                    }
                )
            }
            if(selectedTertiaryCategory.isNotBlank()) {
                Spacer(modifier = Modifier.size(10.dp))

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f))
                        .padding(16.dp),

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val isCategoryChosen = selectedBrand.isEmpty()
                        Text(text = if (isCategoryChosen) "Choose a Brand" else "Brand")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = selectedBrand,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.6f)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "Choose a Brand",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                    CustomDivider(modifier = Modifier.padding(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "Condition")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = selectedBrand,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "condition",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                    CustomDivider(modifier = Modifier.padding(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "Size")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = selectedBrand,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "size",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Size (Optional)", modifier = Modifier.padding(bottom = 4.dp))
            OutlinedTextField(
                value = size,
                onValueChange = {
                    size = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        "Enter Size",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                shape = RoundedCornerShape(16.dp),
                supportingText = {
                    Text(
                        text = "Provide size details (e.g., S, M, L, or dimensions in cm).",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                }
            )

        }
    }
}


@Composable
fun CommonSpecification(
    modifier: Modifier = Modifier,
    initialLabel: String,
    valueLabel: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val isCategoryChosen = value.isNotEmpty()
        Text(text = if (!isCategoryChosen) initialLabel else valueLabel)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = initialLabel,
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun KeyInfoSection(
    modifier: Modifier = Modifier,
    price: Price,
    onPriceClick: () -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }

    Column(
        modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = "Information",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        CommonSpecification(
            initialLabel = "Price",
            valueLabel = "Price",
            value = "",
            onClick = {
                onPriceClick()
            }
        )
        CustomDivider()
        Spacer(modifier = Modifier.size(10.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Quantity", modifier = Modifier.padding(bottom = 4.dp))
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = quantity.toString(),
                    onValueChange = {
                        if (it.isNotEmpty() && it.matches(Regex("^[1-9]\\d*|0\$"))) {
                            quantity = it.toIntOrNull() ?: 1
                        }
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .widthIn(min = 200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "info",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    text = "Specify the number of sets or combos available for sale, not the number of items in each set or combo.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun PickupLocationSection(
    modifier: Modifier = Modifier,
    location: String,
    onClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Product pickup",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val isLocation = location.isNotEmpty()
            if(!isLocation) {
                Text(text = "Add address")
            } else {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location",
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text =location,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "location",
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun AdvancedSellerSettingSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Advanced seller setting")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = "Choose a category",
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
//
//    Column(
//        modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .background(MaterialTheme.colorScheme.primaryContainer)
//    ) {
//        CommonSpecification(
//            initialLabel = "Advanced seller setting",
//            valueLabel = "Advanced seller setting",
//            value = "",
//            onClick = {
//                onClick()
//            }
//        )
//    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewSellScreen() {
    SwapsyTheme {
        PickupLocationSection(location = "", onClick = {})
    }
}
