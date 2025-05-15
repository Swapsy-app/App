package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.SwapGoTheme

@Composable
fun HomeCarousel(
    modifier: Modifier = Modifier,
    images: List<Int> = listOf(
        R.drawable.b1,
        R.drawable.b2,
        R.drawable.b3,
        R.drawable.b4,
        R.drawable.b5
    ),
    onImagePreview: (Int) -> Unit
) {

    val pagerState = rememberPagerState(initialPage = 0) { images.size }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            HorizontalPager(
                state = pagerState,

            ) { currentPage ->

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.78f) // 9:16
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onImagePreview(pagerState.currentPage)
                            }
                        ),
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
            ) {
                HomeCarouselPageIndicator(
                    pageCount = images.size,
                    currentPage = pagerState.currentPage,
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

    }
}

@Composable
fun HomeCarouselPageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) {
            HomeCarouselIndicatorDots(
                isSelected = it == currentPage,
                modifier = modifier
            )
        }
    }
}

@Composable
fun HomeCarouselIndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 8.dp, label = "")
    Box(
        modifier = modifier
            .padding(3.dp)
            .size(size.value)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = CircleShape
            )
            .background(
                color = if (isSelected)
                    MaterialTheme.colorScheme.tertiary
                else Color.LightGray
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeCarousel() {
    SwapGoTheme {
        HomeCarousel(
            images = listOf(
                R.drawable.b1,
                R.drawable.b2,
                R.drawable.b3,
                R.drawable.b4,
                R.drawable.b5
            ),
            onImagePreview = {}
        )
    }
}