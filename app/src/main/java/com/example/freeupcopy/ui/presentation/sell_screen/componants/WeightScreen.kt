package com.example.freeupcopy.ui.presentation.sell_screen.componants

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.model.Weight
import com.example.freeupcopy.ui.theme.SwapsyTheme
import com.example.freeupcopy.ui.theme.NoteContainerLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(
    modifier: Modifier = Modifier,
    selectedWeight: String,
    onWeightClick: (String) -> Unit,
    onClose: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Weight")
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

        //Using courier bags can significantly reduce weight compared to boxes, saving on shipping costs.
        //var selectedWeight by remember { mutableStateOf(Weight.predefinedWeight) }
        val items = listOf(
            Weight(
                "Under 500g",
                "Ideal for lighter items like watch, phone, book, jewelry or notebook"
            ),
            Weight(
                "500g - 1kg",
                "Suitable for items like dress, shirt, tablet, shoes or hairdryer"
            ),
            Weight(
                "1 - 5kg",
                "Perfect for bulkier items such as blender, suit or cookware Set"
            ),
            Weight(
                "5 - 10kg",
                "Recommended for heavier products like chair, small microwave or bridal lehengas"
            )
        )
//        LazyColumn(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(innerPadding)
//        ) {

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NoteSection(
                        text = stringResource(id = R.string.weight_announcement)
                    )
                    Spacer(modifier = Modifier.size(8.dp))

                    items.forEach {
                        WeightComposable(
                            weight = it,
                            isSelected = it.range == selectedWeight,
                            onClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(androidx.lifecycle.Lifecycle.State.RESUMED)) {
                                    //selectedWeight = it
                                    onWeightClick(it.range)
                                }
                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray.copy(alpha = 0.25f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Above 10kg",
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            fontSize = 13.5.sp,
                            lineHeight = 18.sp,
                            text = "Items over 10 kg are not eligible for shipping.",
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(NoteContainerLight.copy(alpha = 0.6f))
                            .padding(16.dp),
                    ) {
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("*")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                                )
                            ) {
                                append(stringResource(id = R.string.weight_note))
                            }
                        }
                        Text(text = annotatedString, lineHeight = 18.sp)
                    }
                }

//        }
    }
}

@Composable
fun WeightComposable(
    modifier: Modifier = Modifier,
    weight: Weight,
    isSelected: Boolean,
    onClick: (Weight) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(weight) }
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
        ) {
            Text(text = weight.range)
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                fontSize = 13.5.sp,
                lineHeight = 16.sp,
                text = weight.description,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomRadioButton(
            isSelected = isSelected
        )
    }
}

@Composable
fun CustomRadioButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 6.dp else 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = CircleShape
            )
    )
}

@Composable
fun NoteSection(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.75f))
            .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
    ) {
        Icon(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = -36f
                }
                .padding(horizontal = 8.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
            painter = painterResource(id = R.drawable.ic_campaign),
            contentDescription = "notice"
        )
        Text(
            text = text,
            fontSize = 13.5.sp,
            lineHeight = 18.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeightScreenPreview() {
    SwapsyTheme {
        WeightScreen(
            selectedWeight = "Under 500g",
            onWeightClick = {},
            onClose = {}
        )
    }
}