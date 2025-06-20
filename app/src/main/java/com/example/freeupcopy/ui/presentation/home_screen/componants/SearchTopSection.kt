package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.theme.BadgeDark
import com.example.freeupcopy.ui.theme.BadgeLight
import com.example.freeupcopy.ui.theme.CustomOrangeColor
import com.example.freeupcopy.ui.theme.LinkColor
import com.example.freeupcopy.ui.theme.RecommendedContainerColor
import com.example.freeupcopy.ui.theme.SwapGoTheme
import com.example.freeupcopy.ui.theme.TextFieldContainerColor

@Composable
fun SearchTopSection(
    modifier: Modifier = Modifier,
    onSearchBarClick: () -> Unit,
    onInboxClick: () -> Unit,
    onCartClick: () -> Unit,
    totalCombos: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        SearchRow(
            modifier = Modifier
                .fillMaxWidth(),
            onSearchBarClick = { onSearchBarClick() },
            onInboxClick = { onInboxClick() },
            onCartClick = { onCartClick() },
            totalCombos = totalCombos
        )
    }
}

@Composable
fun SearchRow(
    modifier: Modifier = Modifier,
    onSearchBarClick: () -> Unit,
    onInboxClick: () -> Unit,
    onCartClick: () -> Unit,
    totalCombos: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HomeSearchBar(
            modifier = Modifier.weight(1f),
            onClick = { onSearchBarClick() }
        )

        CustomTopBarItem(
            onClick = { onInboxClick() },
            icon = painterResource(id = R.drawable.ic_inbox),
            contentDescription = "inbox",
            badgeNumber = 2
        )
        CustomTopBarItem(
            onClick = { onCartClick() },
            icon = painterResource(id = R.drawable.ic_shopping_cart),
            contentDescription = "cart",
            badgeNumber = totalCombos
        )
    }

}

@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        RecommendedContainerColor.copy(0.095f),
                        MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .clickable { onClick() }
//            .border(1.5.dp, Color(0xFF00AEFF).copy(0.6f), CircleShape)
            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp),
            imageVector = Icons.Outlined.Search,
            contentDescription = "search",
            //tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "Teddy bears",
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    singleLined: Boolean = true,
    isFocused: MutableState<Boolean>,
    onFocusChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onCancel: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused.value = it.hasFocus
                    onFocusChange(it.hasFocus)
                }
                .clip(CircleShape)
                .height(55.dp),
            interactionSource = interactionSource,
            enabled = enabled,
            singleLine = singleLined,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        ) {
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = it,
                enabled = enabled,
                singleLine = singleLined,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    Text(
                        text = "Search",
                        style = TextStyle.Default.copy(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(28.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    if (value.isNotBlank()) {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable { onCancel() }
                                .padding(8.dp),
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = containerColor,
                    focusedContainerColor = containerColor,
                    disabledIndicatorColor = Color.Transparent,
                    disabledContainerColor = containerColor,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                ),
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    top = 0.dp,
                    bottom = 0.dp,
                )
            )
        }
    }
}


@Composable
fun CustomTopBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String,
    badgeNumber: Int
) {
    Box(
        modifier = modifier
            .size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(28.dp),
                tint = MaterialTheme.colorScheme.onSurface,
                painter = icon,
                contentDescription = contentDescription
            )
        }
        if (badgeNumber > 0) {
            Text(
                text = if (badgeNumber < 1000) badgeNumber.toString() else "...",
                style = TextStyle.Default.copy(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (isSystemInDarkTheme())
                            BadgeDark
                        else
                            BadgeLight
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeSearchBar() {
    SwapGoTheme {
         SearchTopSection(
            onSearchBarClick = {},
            onInboxClick = {},
            onCartClick = {},
             totalCombos = 0
        )
    }
}