package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.Category

@Composable
fun CategoryRow(
    imageId: Array<Int>,
    names: Array<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(contentPadding = PaddingValues(16.dp)) {
        val itemCount = imageId.size

        items(itemCount) {
            RowItem(
                modifier,
                painter = imageId,
                title = names,
                itemIndex = it
            )
        }
    }
}

@Composable
fun RowItem(
    modifier: Modifier,
    painter: Array<Int>,
    title: Array<String>,
    itemIndex: Int
) {
    Card(
        modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {

    }
}

@Preview
@Composable
fun CategoryRowPreview() {
    CategoryRow(
        imageId = arrayOf(
            R.drawable.p1,
            R.drawable.p1,
            R.drawable.p1,
            R.drawable.p1,
            R.drawable.p1,
            R.drawable.p1
        ),
        names = arrayOf(
            "Peperoni",
            "Vegan",
            "FourCheese",
            "Margaritta",
            "American",
            "Mexican"
        )
    )
}
