package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CashColor1
import com.example.freeupcopy.ui.theme.CustomOrangeColor
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.utils.dashedBorder

@Composable
fun ProductScreenBottomBar(
    modifier: Modifier = Modifier,
    specialOffer: Pair<String, String>,
    coinsOffered: String,
    mrp: String,
    priceOffered: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .windowInsetsPadding(NavigationBarDefaults.windowInsets)
            .defaultMinSize(minHeight = 70.dp)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Row(
                modifier = Modifier
                    .heightIn(min = 45.dp)
                    .fillMaxWidth()
                    .clip(ButtonShape)
                    .clickable { }
                    .dashedBorder(
                        color = CashColor1,
                        shape = ButtonShape,
                        strokeWidth = 3.dp,
                        gapLength = 5.dp,
                        dashLength = 2.dp,
                        cap = StrokeCap.Square
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                                CustomOrangeColor.copy(alpha = 0.5f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        )
                    )
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "₹${specialOffer.first}  +  ${specialOffer.second}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.coin),
                    contentDescription = "coin",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                Row(
                    modifier = Modifier
                        .weight(0.70f)
                        .heightIn(min = 50.dp)
                        .fillMaxWidth()
                        .clip(ButtonShape)
                        .clickable { }
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f),
                            ButtonShape
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = coinsOffered,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "coin",
                        modifier = Modifier.size(24.dp)
                    )

                }
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier
                        .weight(0.70f)
                        .heightIn(min = 50.dp)
                        .fillMaxWidth()
                        .clip(ButtonShape)
                        .clickable { }
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(0.3f),
                            ButtonShape
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "₹$mrp",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "₹$priceOffered",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }

    }
}

@Preview (showBackground = true)
@Composable
fun ProductScreenBottomBarPreview() {
    SwapGoTheme {
        ProductScreenBottomBar(
            specialOffer = Pair("100", "50"),
            coinsOffered = "100",
            mrp = "1000",
            priceOffered = "500"
        )
    }
}