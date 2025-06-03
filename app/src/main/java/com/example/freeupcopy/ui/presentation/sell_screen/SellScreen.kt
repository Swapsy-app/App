package com.example.freeupcopy.ui.presentation.sell_screen

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.SubcomposeAsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.freeupcopy.R
import com.example.freeupcopy.domain.enums.SpecialOption
import com.example.freeupcopy.domain.model.Price
import com.example.freeupcopy.domain.model.PriceUiModel
import com.example.freeupcopy.domain.model.toUiModel
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.TextFieldShape
import com.example.freeupcopy.utils.clearFocusOnKeyboardDismiss
import com.example.freeupcopy.utils.dashedBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: () -> Unit,
    onConditionClick: (String) -> Unit,
    onWeightClick: (String) -> Unit,
    onManufacturingClick: (String) -> Unit,
    onPriceClick: (Price?) -> Unit,
    onLocationClick: (String?) -> Unit,
    onAdvanceSettingClick: (String) -> Unit,
    onClose: () -> Unit,
    onSpecificationClick: (SpecialOption) -> Unit,
    onAddImageVideoClick: (Int) -> Unit,
    sellViewModel: SellViewModel,
    uploadedImages: List<String>,
    uploadedVideo: String
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val state by sellViewModel.state.collectAsState()

    // When uploadedImages changes, dispatch the event to add them.
    LaunchedEffect(uploadedImages) {
        if (uploadedImages.isNotEmpty()) {
            sellViewModel.onEvent(SellUiEvent.AddUploadedImages(uploadedImages))
        }
    }
    LaunchedEffect(uploadedVideo) {
        if (uploadedVideo.isNotEmpty()) {
            sellViewModel.onEvent(SellUiEvent.AddUploadedVideo(uploadedVideo))
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Selling Details",
//                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.weight(1f))
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.size(16.dp))
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
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.85f))
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp)

                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(0.70f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { }
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Save", color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                sellViewModel.onEvent(SellUiEvent.AddProduct)
                            }
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sell", color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                CategorySection(
                    chosenCategory = state.tertiaryCategory ?: "",
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onCategoryClick()
                        }
                    }
                )
            }
            item {
                DetailsSection(
                    title = state.title,
                    description = state.description,
                    images = state.images,
                    video = state.video,
                    onTitleValueChange = { title ->
                        sellViewModel.onEvent(SellUiEvent.TitleChange(title))
                    },
                    onDescriptionValueChange = { description ->
                        sellViewModel.onEvent(SellUiEvent.DescriptionChange(description))
                    },
                    onAddImageVideoClick = { numberOfUploadedImages ->
                        onAddImageVideoClick(numberOfUploadedImages)
                                           },
                    onRemoveImageUrl = { imageUrl ->
                        sellViewModel.onEvent(SellUiEvent.RemoveImage(imageUrl))
                    },
                    onRemoveVideo = {
                        sellViewModel.onEvent(SellUiEvent.RemoveVideo)
                    }
                )
            }

            item {
                LocationSection(
                    location = state.address?.address ?: "",
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onLocationClick(state.address?._id ?: "")
                        }
                    }
                )
            }

            item {
                SpecificationSection(
                    onManufacturingClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onManufacturingClick(state.manufacturingCountry ?: "India")
                        }
                    },
//                    onBrandClick = {
//                        val currentState = lifeCycleOwner.lifecycle.currentState
//                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
//                            onBrandClick()
//                        }
//                    },
                    onConditionClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onConditionClick(state.condition ?: "")
                        }
                    },
                    onWeightClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onWeightClick(state.weight?.type ?: "")
                        }
                    },
                    state = state,
                    onSpecificationClick = { option ->
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onSpecificationClick(option)
                        }
                    },
                    onOptionalSizeChange = { size ->
                        sellViewModel.onEvent(SellUiEvent.SizeChange(size))
                    },
                    onOptionalBrandChange = { brand ->
                        sellViewModel.onEvent(SellUiEvent.BrandChange(brand))
                    }
                )
            }

            item {
                PriceAndQuantitySection(
                    priceUiModel = state.price?.toUiModel(),
                    onPriceClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onPriceClick(state.price)
                        }
                    }
                )
            }
            item {
                AdvancedSellerSettingSection(
                    onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            onAdvanceSettingClick(state.gst)
                        }
                    }
                )
            }
            item {

            }
        }
    }
}

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    chosenCategory: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isCategoryChosen = chosenCategory.isEmpty()
        Text(text = if (isCategoryChosen) "Choose a category" else "Category")
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = chosenCategory,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = "Choose a category",
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    images: List<String>,
    video: String?, // New parameter for the video URL.
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onAddImageVideoClick: (Int) -> Unit,
    onRemoveImageUrl: (String) -> Unit,
    onRemoveVideo: () -> Unit // Callback for deleting video.
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Product Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            FlowRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                images.forEachIndexed { index, image ->
                    PreviewImageBox(
                        imageUrl = image,
                        onRemoveClick = {
                            onRemoveImageUrl(image)
                        }
                    )
                }
                if (video != null) {
                    PreviewVideoBox(
                        videoUrl = video,
                        onRemoveClick = onRemoveVideo
                    )
                }
                if (images.size != 7) {
                    AddPhotoBox()
                    AddMoreBox(
                        onClick = { onAddImageVideoClick(images.size) }
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "info",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    fontSize = 13.5.sp,
                    lineHeight = 16.sp,
                    text = "Add clear and attractive photos to showcase your product and attract more buyers.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.size(22.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    onTitleValueChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        "Enter Title",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                },
                label = { Text("Title") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                ),
                shape = TextFieldShape
            )

            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = description,
                minLines = 3,
                onValueChange = {
                    onDescriptionValueChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        "Enter Description",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                },
                label = { Text("Description") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                ),
                shape = TextFieldShape
            )
        }
    }
}

@Composable
fun AddMoreBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .dashedBorder(
                strokeWidth = 3.dp,
                gapLength = 6.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Rounded.Add,
            contentDescription = "add"
        )
    }
}

@Composable
fun PreviewImageBox(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onRemoveClick: () -> Unit
) {
    Box(modifier = modifier.size(100.dp)) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        )

        IconButton(
            onClick = { onRemoveClick() },
            modifier = Modifier
                .offset(x = 6.dp, y = (-6).dp)
                .align(Alignment.TopEnd)
                .size(20.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Remove Image",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun PreviewVideoBox(
    modifier: Modifier = Modifier,
    videoUrl: String,
    onRemoveClick: () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = modifier.size(100.dp)) {
        // Load the video thumbnail using SubcomposeAsyncImage.
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(videoUrl)
                .videoFrameMillis(10)
                .decoderFactory{ result, options, _ ->
                    VideoFrameDecoder(
                        result.source,
                        options
                    )
                }
                .build(),
            contentDescription = "Video Preview",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )
        // Center overlay: video icon.
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Video",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.Center)
                .size(40.dp)
        )
        // Delete icon in the top-right corner.
        IconButton(
            onClick = { onRemoveClick() },
            modifier = Modifier
                .offset(x = 6.dp, y = (-6).dp)
                .align(Alignment.TopEnd)
                .size(20.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Remove Video",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}


@Composable
fun AddPhotoBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .dashedBorder(
                strokeWidth = 3.dp,
                gapLength = 6.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                alpha = 0.3f,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                painter = painterResource(id = R.drawable.add_image),
                contentDescription = "add image"
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                text = "Add photo",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun SpecificationSection(
    modifier: Modifier = Modifier,
    onManufacturingClick: () -> Unit,
    onConditionClick: () -> Unit,
    onWeightClick: () -> Unit,
    state: SellUiState,
    onSpecificationClick: (SpecialOption) -> Unit,
    onOptionalSizeChange: (String) -> Unit,
    onOptionalBrandChange: (String) -> Unit,
) {
    //var size by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Specifications",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(10.dp))


            CommonSpecification(
                initialLabel = "Product Condition",
                valueLabel = "Condition",
                value = state.condition ?: "",
                onClick = {
                    onConditionClick()
                }
            )
            SpecificationDivider()

            if (!state.specialOptions.contains(SpecialOption.BRAND) && !state.tertiaryCategory.isNullOrEmpty()) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                ) {
                    Text(text = "Brand (Optional)", modifier = Modifier.padding(bottom = 4.dp))

                    OutlinedTextField(
                        value = state.brand ?: "",
                        onValueChange = {
                            //sellViewModel.onEvent(SellUiEvent.BrandChange(it))
                            onOptionalBrandChange(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Enter Brand",
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.4f
                            ),
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        shape = TextFieldShape,
                        supportingText = {
                            Text(
                                text = "Provide brand details (e.g., Nike, Adidas, etc.).",
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        }
                    )
                }
                SpecificationDivider()
            }

            CommonSpecification(
                initialLabel = "Manufacturing Country",
                valueLabel = "Manufacturing Country",
                value = state.manufacturingCountry ?: "India",
                onClick = {
                    onManufacturingClick()
                }
            )

            SpecificationDivider()

            CommonSpecification(
                initialLabel = "Weight",
                valueLabel = "Weight",
                value = state.weight?.range ?: "",
                onClick = {
                    onWeightClick()
                }
            )


            state.specialOptions.forEachIndexed { i, option ->
                if (i == 0) {
                    SpecificationDivider()
                }

                CommonSpecification(
                    initialLabel = option.initialLabel,
                    valueLabel = option.valueLabel,
                    value = option.valueSelector(state) ?: "",
                    onClick = { onSpecificationClick(option) }
                )
                if (i != state.specialOptions.lastIndex) {
                    SpecificationDivider()
                }
            }

            if (!state.specialOptions.contains(SpecialOption.SIZE) && !state.tertiaryCategory.isNullOrEmpty()) {
                SpecificationDivider()

                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {

                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Size (Optional)", modifier = Modifier.padding(bottom = 4.dp))

                    OutlinedTextField(
                        value = state.size ?: "",
                        onValueChange = {
                            onOptionalSizeChange(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Enter Size",
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.4f
                            ),
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        shape = TextFieldShape,
                        supportingText = {
                            Text(
                                text = "Provide size details (e.g., S, M, L, or dimensions in cm).",
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        }
                    )
                }
            }

        }
    }
}


@Composable
fun CommonSpecification(
    modifier: Modifier = Modifier,
    initialLabel: String,
    valueLabel: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val isCategoryChosen = value.isNotEmpty()
        Text(text = if (!isCategoryChosen) initialLabel else valueLabel)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = initialLabel,
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun PriceAndQuantitySection(
    modifier: Modifier = Modifier,
    priceUiModel: PriceUiModel?,
    onPriceClick: () -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }

    Column(
        modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss()
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = "Information",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(10.dp))

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onPriceClick() }
                .padding(horizontal = 8.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = if (priceUiModel == null) "Price" else "You will be earning"
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "price",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }
            if (priceUiModel != null) {
                Spacer(modifier = Modifier.size(10.dp))
                PriceRow(priceUiModel = priceUiModel, modifier = Modifier.align(Alignment.End))
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
        )

        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Quantity",
                    //modifier = Modifier.padding(bottom = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = quantity.toString(),
                    onValueChange = {
                        if (it.isNotEmpty() && it.matches(Regex("^[1-9]\\d*|0\$"))) {
                            quantity = it.toIntOrNull() ?: 1
                        }
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .widthIn(min = 200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.4f
                        ),
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "info",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    text = "Specify the number of sets or combos available for sale, not the number of items in each set or combo.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun LocationSection(
    modifier: Modifier = Modifier,
    location: String,
    onClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Product pickup",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val isLocation = location.isNotEmpty()
            if (!isLocation) {
                Text(text = "Add address")
            } else {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location",
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = location,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "location",
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun AdvancedSellerSettingSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(CardShape.medium)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = "For businesses",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(16.dp))

        Row {
            Text(text = "Advanced seller setting")
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Choose a category",
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun PriceRow(
    modifier: Modifier = Modifier,
    priceUiModel: PriceUiModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var shouldShowDivider = false

        if (priceUiModel.earningCash?.isNotEmpty() == true) {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cash",
                    fontSize = 14.sp
                )
                Text(
                    text = "₹${priceUiModel.earningCash}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            shouldShowDivider = true
        }
        if (priceUiModel.earningCoin?.isNotEmpty() == true) {
            if (shouldShowDivider) {
                VerticalDivider(
                    thickness = 1.5.dp,
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = LocalContentColor.current.copy(alpha = 0.15f)
                )
            }
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Coin",
                    fontSize = 14.sp
                )
                Text(
                    text = "${priceUiModel.earningCoin} C(s)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            shouldShowDivider = true
        }

        if (priceUiModel.earningCashCoin?.first?.isNotEmpty() == true && priceUiModel.earningCashCoin.second?.isNotEmpty() == true) {
            if (shouldShowDivider) {
                VerticalDivider(
                    thickness = 1.5.dp,
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = LocalContentColor.current.copy(alpha = 0.15f)
                )
            }
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cash+Coin",
                    fontSize = 14.sp
                )

                Text(
                    text = "₹${priceUiModel.earningCashCoin.first} + ${priceUiModel.earningCashCoin.second} C(s)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            shouldShowDivider = true
        }
    }
}

@Composable
private fun SpecificationDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 10.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
    )
}

// Helper function: Extracts the file extension from a Uri.
fun getFileExtension(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val mime = contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)
}

