package com.example.freeupcopy.ui.presentation.sell_screen.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.Category
import com.example.freeupcopy.domain.model.CategoryUiModel
import com.example.freeupcopy.domain.model.SubCategory
import com.example.freeupcopy.domain.model.TertiaryCategory
import com.example.freeupcopy.ui.presentation.home_screen.componants.SearchBar
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.NoteSection
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (CategoryUiModel) -> Unit,
    onClose: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .clearFocusOnKeyboardDismiss()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Category")
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
        }
    ) { innerPadding ->

        ChooseCategory(
            modifier = Modifier.padding(innerPadding),
            categories = Category.predefinedCategories,
            onCategoryClick = { s ->
                val currentState = lifeCycleOwner.lifecycle.currentState
                if (currentState.isAtLeast(androidx.lifecycle.Lifecycle.State.RESUMED)) {
                    onCategoryClick(
                        CategoryUiModel(
                            category = s.category,
                            subCategory = s.subCategory,
                            tertiaryCategory = s.tertiaryCategory
                        )
                    )
                }
            }
        )

    }
}


@Composable
fun ChooseCategory(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategoryClick: (CategoryUiModel) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    var searchQuery by remember { mutableStateOf("") }
    val isSearchBarFocused = remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (selectedCategory == null) {
            NoteSection(
                text = stringResource(id = R.string.category_announcement)
            )
        }

        Spacer(modifier = Modifier.size(6.dp))

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
                ),
                shape = RoundedCornerShape(16.dp)
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

        selectedCategory?.let {

            // Filter subcategories and their tertiary categories based on the search query
            val filteredSubcategories = selectedCategory!!.subcategories.mapNotNull { subCategory ->
                val matchingTertiaryCategories = subCategory.tertiaryCategories.filter { tertiaryCategory ->
                    tertiaryCategory.name.contains(searchQuery, ignoreCase = true)
                }

                // Include the subcategory only if there are matching tertiary categories
                if (matchingTertiaryCategories.isNotEmpty()) {
                    subCategory.copy(tertiaryCategories = matchingTertiaryCategories)
                } else {
                    null
                }
            }

            Column {
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

                if (filteredSubcategories.isEmpty()) {
                    // Show message when no subcategories match the query
                    Text(
                        text = "No matching subcategories found.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(filteredSubcategories) { subCategory ->
                            SubCategoryRow(
                                subCategory = subCategory,
                                onSubCategoryClick = { subCat, terCat ->
                                    onCategoryClick(
                                        CategoryUiModel(
                                            category = selectedCategory!!.name,
                                            subCategory = subCat,
                                            tertiaryCategory = terCat
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun SubCategorySection(
//    modifier: Modifier = Modifier,
//    category: Category,
//    onCategoryClick: (String) -> Unit
//) {
//    var searchQuery by remember { mutableStateOf("") }
//
//    // Recursive function to gather only the final subcategories (those with no subcategories inside them)
////    fun gatherFinalSubcategories(category: Category): List<Category> {
////        return category.subcategories.filter { it.subcategories.isEmpty() } +
////                category.subcategories.flatMap { gatherFinalSubcategories(it) }
////    }
////
////    // Gather all final subcategories (last-level subcategories)
////    val finalSubcategories = gatherFinalSubcategories(category)
////
////    // Filtered list of subcategories based on the search query
////    val filteredSubcategories = if (searchQuery.isEmpty()) {
////        category.subcategories
////    } else {
////        finalSubcategories.filter { it.name.contains(searchQuery, ignoreCase = true) }
////    }
//
//    val isSearchBarFocused = remember { mutableStateOf(false) }
//
//    Column(modifier = modifier) {
//        SearchBar(
//            value = searchQuery,
//            isFocused = isSearchBarFocused,
//            onFocusChange = {
//                isSearchBarFocused.value = it
//            },
//            onValueChange = {
//                searchQuery = it
//            },
//            onSearch = {
//                isSearchBarFocused.value = false
//            },
//            onCancel = {
//                searchQuery = ""
//            }
//        )
//
//        Spacer(modifier = Modifier.size(16.dp))
//
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            if (filteredSubcategories.firstOrNull()?.subcategories?.isNotEmpty() == true) {
//                items(filteredSubcategories) { subCategory ->
//                    if (subCategory != filteredSubcategories.first()) {
//                        CustomDivider()
//                        Spacer(modifier = Modifier.size(16.dp))
//                    }
//                    SubCategoryHeadingWithCategory(
//                        category = subCategory,
//                        onCategoryClick = { s ->
//                            onCategoryClick(s)
//                        }
//                    )
//                }
//            } else {
//                item {
//                    if (filteredSubcategories.isEmpty()) {
//                        Text(
//                            text = "No matching subcategories found.",
//                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
//                            modifier = Modifier.padding(16.dp),
//                            textAlign = TextAlign.Center
//                        )
//                    } else {
//                        FlowRow(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalArrangement = Arrangement.spacedBy(12.dp),
//                            horizontalArrangement = Arrangement.spacedBy(16.dp),
//                            maxLines = 4
//                        ) {
//                            filteredSubcategories.forEach { category ->
//                                FinalCategoryItems(
//                                    category = category,
//                                    onClick = {
//                                        onCategoryClick(category.name)
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubCategoryRow(
    modifier: Modifier = Modifier,
    subCategory: SubCategory,
    onSubCategoryClick: (String, String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        if (subCategory.name != "Primary") {
            Text(
                text = subCategory.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                //modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            maxLines = 4
        ) {
            subCategory.tertiaryCategories.forEach { tertiaryCategory ->
                FinalCategoryItems(
                    tertiaryCategory = tertiaryCategory,
                    onClick = { s ->
                        onSubCategoryClick(subCategory.name, tertiaryCategory.name)
                    }
                )
            }
        }
    }
}

@Composable
fun FinalCategoryItems(
    modifier: Modifier = Modifier,
    tertiaryCategory: TertiaryCategory,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.21f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    //.size(75.dp)
                    .clip(CircleShape)
                    .clickable { onClick(tertiaryCategory.name) },
                painter = painterResource(id = tertiaryCategory.imageUrl ?: R.drawable.kurta_men),
                contentDescription = tertiaryCategory.name
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.widthIn(max = 75.dp),
                fontSize = 11.5.sp,
                maxLines = 3,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                text = tertiaryCategory.name
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(
        onCategoryClick = {},
        onClose = {}
    )
}