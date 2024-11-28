package com.example.freeupcopy.ui.presentation.sell_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.Category
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->

        ChooseCategory(
            modifier = Modifier.padding(innerPadding),
            categories = Category.predefinedCategories,
            onCategoryClick = { s ->
                val currentState = lifeCycleOwner.lifecycle.currentState
                if (currentState.isAtLeast(androidx.lifecycle.Lifecycle.State.RESUMED)) {
                    onCategoryClick(s)
                }
            }
        )

    }
}


@Composable
fun ChooseCategory(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val interactionSource = remember { MutableInteractionSource() }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box {
            OutlinedTextField(
                value = mSelectedText,
                onValueChange = {},
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        mExpanded = !mExpanded
                    }
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Choose Category") },
                trailingIcon = {
                    Icon(
                        icon, icon.name
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = if (mExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface
                )
            )
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                categories.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            selectedCategory =
                                categories.find { category -> category.name == it.name }
                            mSelectedText = it.name
                            mExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        if (selectedCategory != null) {
            SubCategorySection(
                category = selectedCategory!!,
                onCategoryClick = { s ->
                    onCategoryClick(s)
                }
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "info",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Please select a category to view subcategories",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubCategorySection(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Recursive function to gather only the final subcategories (those with no subcategories inside them)
    fun gatherFinalSubcategories(category: Category): List<Category> {
        return category.subcategories.filter { it.subcategories.isEmpty() } +
                category.subcategories.flatMap { gatherFinalSubcategories(it) }
    }

    // Gather all final subcategories (last-level subcategories)
    val finalSubcategories = gatherFinalSubcategories(category)

    // Filtered list of subcategories based on the search query
    val filteredSubcategories = if (searchQuery.isEmpty()) {
        category.subcategories
    } else {
        finalSubcategories.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    val isSearchBarFocused = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        SearchBar(
            value = searchQuery,
            isFocused = isSearchBarFocused,
            onFocusChange = {
                isSearchBarFocused.value = it
            },
            onValueChange = {
                searchQuery = it
            },
            onSearch = {
                isSearchBarFocused.value = false
            },
            onCancel = {
                searchQuery = ""
            }
        )

        Spacer(modifier = Modifier.size(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (filteredSubcategories.firstOrNull()?.subcategories?.isNotEmpty() == true) {
                items(filteredSubcategories) { subCategory ->
                    if (subCategory != filteredSubcategories.first()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                    SubCategoryHeadingWithCategory(
                        category = subCategory,
                        onCategoryClick = { s ->
                            onCategoryClick(s)
                        }
                    )
                }
            } else {
                item {
                    if (filteredSubcategories.isEmpty()) {
                        Text(
                            text = "No matching subcategories found.",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            filteredSubcategories.forEach { category ->
                                FinalCategoryItems(
                                    category = category,
                                    onClick = {
                                        onCategoryClick(category.name)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubCategoryHeadingWithCategory(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = category.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            //modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            category.subcategories.forEach { category ->
                FinalCategoryItems(
                    category = category,
                    onClick = { s ->
                        onCategoryClick(category.name)
                    }
                )
            }
        }
    }
}

@Composable
fun FinalCategoryItems(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .clickable { onClick(category.name) },
                painter = painterResource(id = R.drawable.kurta_men),
                contentDescription = category.name
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.widthIn(max = 75.dp),
                fontSize = 11.5.sp,
                maxLines = 3,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                text = category.name
            )
        }
    }
}