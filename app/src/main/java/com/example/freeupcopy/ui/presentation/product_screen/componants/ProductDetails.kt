package com.example.freeupcopy.ui.presentation.product_page.componants

import android.media.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.viewmodel.ProductScreenViewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun ProductDetails(
    productTitle : String = "Product Title",
    productCondition : String = " Product Condition",
    productSize: String = "Product Size",
    productBrand: String = "Product Brand",
    productDescription : String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula eros ut libero dictum, a pretium ligula hendrerit. Curabitur auctor magna id felis interdum, vitae feugiat velit.",
    productCategory : String = "Product Category",
    productFabric : String = "Product Fabric",
    productOccasion : String = "Product Occasion",
    productPlaceOfOrigin : String = "Product Place of Origin",
    productColor : String = "Product Color",
    productShape : String = "Product Shape",
    productWeight: String = "Product Weight",
    innerPaddingValues: PaddingValues,
    imageSelected: Int,
    changeImage: (Int) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = productTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Text(
                text = "Condition : ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 18.sp
            )
            Text(
                text = productCondition,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Text(
                text = "Size : ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 18.sp
            )
            Text(
                text = productSize,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Text(
                text = "Brand : ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 18.sp
            )
            Text(
                text = productBrand,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        DynamicImage(
            imageSelected = imageSelected,
            changeImage = changeImage
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = productDescription,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        Box {
            Divider(
                Modifier
                    .fillMaxWidth()
                    .absoluteOffset(x = -(innerPaddingValues.calculateTopPadding())),
                thickness = 8.dp,
                color = Color.LightGray
            )
            Divider(
                Modifier
                    .fillMaxWidth()
                    .absoluteOffset(x = (innerPaddingValues.calculateTopPadding())),
                thickness = 8.dp,
                color = Color.LightGray
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Row(
                modifier = Modifier.width(500.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Category",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productCategory,
                        fontSize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Place of Origin",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productPlaceOfOrigin,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Fabric",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productFabric,
                        fontSize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Color",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productColor,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Occasion",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productOccasion,
                        fontSize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Shape",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productShape,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Column {
                    Text(
                        text = "Weight",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = productWeight,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Box {
            Divider(
                Modifier
                    .fillMaxWidth()
                    .absoluteOffset(x = -(innerPaddingValues.calculateTopPadding())),
                thickness = 8.dp,
                color = Color.LightGray
            )
            Divider(
                Modifier
                    .fillMaxWidth()
                    .absoluteOffset(x = (innerPaddingValues.calculateTopPadding())),
                thickness = 8.dp,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun DynamicImage(
    images : List<Painter> = listOf(
        painterResource(id = R.drawable.pictures),
        painterResource(id = R.drawable.pictures),
        painterResource(id = R.drawable.pictures),
        painterResource(id = R.drawable.pictures),
        painterResource(id = R.drawable.pictures),

    ),
    changeImage : (Int) -> Unit,
    imageSelected : Int,
) {
    val pagerState : PagerState = rememberPagerState(initialPage = 0){
        images.size
    }
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(10.dp)
        ) {i->
            Image(
                painter = images[i] ,
                contentDescription = "image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            changeImage(i)
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.4f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for( i in 1..images.size){
                if(i == imageSelected){
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawCircle(
                            color = Color.Black
                        )
                    }
                }
                else{
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawCircle(
                            color = Color.Black,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }
        }

    }

}


@Preview(
    showBackground = true
) @Composable
fun PreviewProductDetails(){
    ProductDetails(
        innerPaddingValues = PaddingValues(0.dp),
        imageSelected = 1,
        changeImage = {}
    )
}