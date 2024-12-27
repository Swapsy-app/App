package com.example.freeupcopy.ui.presentation.product_screen.componants

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.home_screen.componants.RowItem
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.NoteContainerLight
import com.example.freeupcopy.ui.theme.SwapsyTheme

@Composable
fun SellerDetail(
    modifier: Modifier = Modifier,
    sellerName: String = "Sk Sahil Islam (Sahil)",
    lastActive: String = "Last Active",
    followers: String = "696",
    sold: String = "12",
    rating: String = "4.5",
    numberOfReviews: String = "8",
    isFollowed: Boolean,
    onFollow: () -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier.weight(1f)  // Take available space
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Seller's Profile Pic",
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.weight(1f)  // Take remaining space in nested Row
                ) {
                    Text(
                        text = sellerName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        maxLines = 2,  // Allow up to 2 lines
                        overflow = TextOverflow.Ellipsis  // Add ellipsis if text is too long
                    )
                    Text(
                        text = lastActive,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.5f),
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(ButtonShape)
                    .background(if (isFollowed) Color.Transparent else MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable { onFollow() }
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.onTertiary),
                        ButtonShape
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 10.dp)
                        .widthIn(min = 90.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = if(isFollowed) Icons.Rounded.CheckCircle else Icons.Rounded.Add,
                        contentDescription = "Follow Icon",
                        tint = if (isFollowed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = if (isFollowed) "Following" else "Follow",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isFollowed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .clip(CardShape.medium)
                .background(
//                    NoteContainerLight.copy(0.5f)
                    brush = Brush.linearGradient(
                        colors = listOf(
                            NoteContainerLight.copy(0.25f),
                            MaterialTheme.colorScheme.primaryContainer
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(0.15f),
                    CardShape.medium
                )
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = followers,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Followers",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = sold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sold",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = rating,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = "reviews icon",
                        tint = Color.Unspecified
                    )
                }
                Text(
                    text = "$numberOfReviews Reviews",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewSellerDetail(
    showBackground: Boolean = true
) {
    SwapsyTheme {
        SellerDetail(
            isFollowed = true,
            onFollow = { }
        )
    }

}