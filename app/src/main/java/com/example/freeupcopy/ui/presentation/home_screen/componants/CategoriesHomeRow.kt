package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun CategoriesHomeRow(
    modifier: Modifier = Modifier,
) {
    val categories = listOf(
        CategoriesHomeRowItem(
            name = "Categories",
            image = painterResource(R.drawable.im_categories),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Ethnic",
            image = painterResource(R.drawable.im_ethnic),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Western",
            image = painterResource(R.drawable.im_western),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Men",
            image = painterResource(R.drawable.im_men),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Kids",
            image = painterResource(R.drawable.im_kids),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Kitchen",
            image = painterResource(R.drawable.im_kitchen),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Books",
            image = painterResource(R.drawable.im_books),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Decor",
            image = painterResource(R.drawable.im_decor),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Gadgets",
            image = painterResource(R.drawable.im_gadgets),
            onClick = {}
        ),
        CategoriesHomeRowItem(
            name = "Toys",
            image = painterResource(R.drawable.im_toys),
            onClick = {}
        )
    )

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(categories) { item ->
            CategoryHomeItem(
                item = item
            )
        }
    }
}

@Composable
fun CategoryHomeItem(
    modifier: Modifier = Modifier,
    item: CategoriesHomeRowItem
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(CardShape.small)
                .clickable { item.onClick() },
            contentScale = ContentScale.Crop,
            painter = item.image,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.name,
            fontSize = 12.sp,
//            fontWeight = FontWeight.W500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoriesHomeRow() {
    SwapGoTheme {
//        CategoryHomeItem(
//            item = CategoriesHomeRowItem(
//                name = "Categories",
//                image = painterResource(R.drawable.im_men),
//                onClick = {}
//            )
//        )
        CategoriesHomeRow()
    }
}

data class CategoriesHomeRowItem(
    val name: String,
    val image: Painter,
    val onClick: () -> Unit
)