package com.example.freeupcopy.ui.presentation.profile_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.ui.theme.CashColor2
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.SwapsyTheme

@Composable
fun ProfileProductTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onClick: (Int) -> Unit,
    listedProductDetails: ListProductDetails,
    soldProductDetails: SoldProductDetails,
    deliveredProductDetails: DeliveredProductDetails,
    selectedSubCategoryIndex: Int = 0
) {
    val tabItems = listOf(
        TabItem(
            title = "Listed",
            count = listedProductDetails.totalCount,
            counterCircleColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
            counterTextColor = LinkColor,
//            subCategories = listOf(
//                TabItemSubCategory("Active", "8"),
//                TabItemSubCategory("Drafts", "0"),
//                TabItemSubCategory("Under Review", "1"),
//                TabItemSubCategory("Unavailable", "3")
//            )
            subCategories = listedProductDetails.subCategories
        ),
        TabItem(
            title = "Pending",
            count = soldProductDetails.totalCount,
            counterCircleColor = NoteContainerLight.copy(alpha = 0.5f),
            counterTextColor = CashColor2,
//            subCategories = listOf(
//                TabItemSubCategory("Order Received", "1"),
//                TabItemSubCategory("Shipped", "1"),
//                TabItemSubCategory("Issues", "3"),
//            )
            subCategories = soldProductDetails.subCategories
        ),
        TabItem(
            title = "Delivered",
            count = deliveredProductDetails.totalCount,
            counterCircleColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
            counterTextColor = Color(0xFFF38600),
//            subCategories = listOf(
//                TabItemSubCategory("Completed", "3"),
//                TabItemSubCategory("Cancelled", "0")
//            )
            subCategories = deliveredProductDetails.subCategories
        )
    )

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            edgePadding = 0.dp,
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        onClick(index)
                    },
                    text = {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(
                                        item.counterCircleColor
                                    )
                                    .padding(horizontal = 6.dp, vertical = 1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.count,
                                    color = item.counterTextColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(start = 16.dp, top = 10.dp, bottom = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabItems[selectedTabIndex].subCategories.forEach { subCategory ->
                item {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                                shape = CircleShape
                            )
                            .background(
                                if (
                                    tabItems[selectedTabIndex].subCategories.indexOf(subCategory)
                                    == selectedSubCategoryIndex
                                )
                                    MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.primaryContainer
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = subCategory.title,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val count: String,
    val counterCircleColor: Color,
    val counterTextColor: Color,
    val subCategories: List<TabItemSubCategory>
)

data class TabItemSubCategory(
    val title: String,
    val count: String
)

data class ListProductDetails(
    val totalCount: String,
    val subCategories: List<TabItemSubCategory>
)

data class SoldProductDetails(
    val totalCount: String,
    val subCategories: List<TabItemSubCategory>
)

data class DeliveredProductDetails(
    val totalCount: String,
    val subCategories: List<TabItemSubCategory>
)

@Preview
@Composable
fun ListedProductsPreview() {
    SwapsyTheme {
        ProfileProductTabRow(
            selectedTabIndex = 0,
            onClick = {},
            listedProductDetails = ListProductDetails(
                totalCount = "12",
                subCategories = listOf(
                    TabItemSubCategory("Active", "8"),
                    TabItemSubCategory("Drafts", "0"),
                    TabItemSubCategory("Under Review", "1"),
                    TabItemSubCategory("Unavailable", "3")
                )
            ),
            soldProductDetails = SoldProductDetails(
                totalCount = "5",
                subCategories = listOf(
                    TabItemSubCategory("Order Received", "1"),
                    TabItemSubCategory("Shipped", "1"),
                    TabItemSubCategory("Issues", "3"),
                )
            ),
            deliveredProductDetails = DeliveredProductDetails(
                totalCount = "3",
                subCategories = listOf(
                    TabItemSubCategory("Completed", "3"),
                    TabItemSubCategory("Cancelled", "0")
                )
            )
        )
    }
}