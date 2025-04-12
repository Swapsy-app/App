package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.freeupcopy.R
import com.example.freeupcopy.data.remote.dto.sell.address.Address
import com.example.freeupcopy.ui.presentation.sell_screen.SellUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.SellViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.AnnouncementComposable
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.AnnouncementIconColor
import com.example.freeupcopy.ui.theme.AnnouncementTextColor
import com.example.freeupcopy.ui.theme.ButtonShape
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapGoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    onNewLocationClick: () -> Unit,
    selectedLocationId: String,
    onClose: () -> Unit,
    sellViewModel: SellViewModel,
    onLocationClick: () -> Unit,
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val lifeCycleOwner = LocalLifecycleOwner.current
    val state by locationViewModel.state.collectAsState()
    val context = LocalContext.current

    val addresses = locationViewModel.addresses.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        addresses.refresh()
    }

    LaunchedEffect(state.error) {
        Log.e("LocationScreen", "Error: ${state.error}")
        state.error.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
                        Text(text = "Locations")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val currentState = lifeCycleOwner.lifecycle.currentState
                        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
//                            if (state.addresses.isEmpty()) {
//                                sellViewModel.onEvent(SellUiEvent.AddressChange(null))
//                            }
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
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .defaultMinSize(minHeight = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 50.dp)
                            .fillMaxWidth()
                            .clip(ButtonShape)
                            .clickable {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    onNewLocationClick()
                                }
                            }
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_location),
                                contentDescription = "add location",
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.size(13.dp))

                            Text(
                                text = "Add New Location", color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            if (state.addresses.isNotEmpty()) {
                AnnouncementComposable(
                    text = stringResource(
                        id = R.string.no_location_announcement
                    ),
                    imageVector = Icons.Outlined.LocationOn,
                    rotation = 0f,
                    iconColor = AnnouncementIconColor,
                    textColor = AnnouncementTextColor
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(addresses.itemCount) { index ->
                    addresses[index]?.let { address ->
                        AddressItem(
                            address = address,
                            isSelected = address._id == selectedLocationId,
                            isDefault = address.defaultAddress,
                            onClick = {
                                val currentState = lifeCycleOwner.lifecycle.currentState
                                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                    scope.launch {
                                        sellViewModel.onEvent(SellUiEvent.AddressChange(address))

                                        onLocationClick()
                                    }
                                }
                            },
                            onMenuClick = { addressId ->
                                locationViewModel.onEvent(
                                    LocationUiEvent.OnExpandMenuClick(
                                        addressId
                                    )
                                )
                            },
                            onSetDefault = {
                                scope.launch {
                                    locationViewModel.onEvent(LocationUiEvent.ChangeLoading(true))
                                    locationViewModel.onEvent(LocationUiEvent.OnSetDefault(address._id))
                                    delay(1000)
                                    addresses.refresh()
                                    delay(1000)
                                    locationViewModel.onEvent(LocationUiEvent.ChangeLoading(false))
                                }
                            },
                            onDelete = {
                                scope.launch {
                                    locationViewModel.onEvent(LocationUiEvent.ChangeLoading(true))
                                    locationViewModel.onEvent(LocationUiEvent.OnDelete(address._id))
                                    delay(1000)
                                    addresses.refresh()
                                    delay(1000)
                                    locationViewModel.onEvent(LocationUiEvent.ChangeLoading(false))
                                }
                            },
                            isMenuExpanded = state.isMenuExpandedAddressId == address._id,
                        )
                    }
                }

                addresses.apply {
                    if (loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 16.dp)
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    if (loadState.append == LoadState.NotLoading(endOfPaginationReached = true) &&
                        addresses.itemCount != 0 && addresses.itemCount > 15
                    ) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                text = "No more addresses to load",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    if (loadState.append is LoadState.Error) {
                        var message = ""
                        val e = (loadState.append as LoadState.Error).error
                        if (e is UnknownHostException) {
                            message = "No internet.\nCheck your connection"
                        } else if (e is Exception) {
                            message = e.message ?: "Unknown error occurred"
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_error),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Spacer(Modifier.size(16.dp))
                                Text(
                                    text = "Error: $message",
                                    modifier = Modifier.weight(1f),
                                    softWrap = true,  // Ensures text wraps to next line when needed
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Button(
                                    modifier = Modifier.padding(start = 16.dp),
                                    onClick = { addresses.retry() },
                                    shape = ButtonShape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = MaterialTheme.colorScheme.onTertiary
                                    )
                                ) {
                                    Text("Retry")
                                }
                            }
                        }

                    }

                }
            }
        }
        addresses.apply {

            var message = ""
            if (loadState.refresh is LoadState.Error) {
                val e = (loadState.refresh as LoadState.Error).error
                if (e is UnknownHostException) {
                    message = "No internet.\nCheck your connection"
                } else if (e is Exception) {
                    message = e.message ?: "Unknown error occurred"
                }
            }

            when (loadState.refresh) {

                is LoadState.Error -> {
                    if(addresses.itemCount == 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.im_error),
                                    contentDescription = null
                                )
                                Text(
                                    text = message.ifEmpty { "Unknown error occurred" },
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W500
                                )
                                Button(
                                    onClick = { addresses.retry() },
                                    shape = ButtonShape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = MaterialTheme.colorScheme.onTertiary
                                    )
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }

                LoadState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }

                else -> {}
            }
        }

    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.3f)),
            contentAlignment = Alignment.Center
        ) {
            PleaseWaitLoading()
        }
    }
}

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: Address,
    isSelected: Boolean,
    isDefault: Boolean,
    isMenuExpanded: Boolean,
    onClick: (String) -> Unit,
    onSetDefault: () -> Unit,
    onDelete: () -> Unit,
    onMenuClick: (String) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(address.address) }
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
        ) {
            if (isDefault) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 16.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Default",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = address.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 18.sp
            )
            Text(
                text = address.phoneNumber,
                fontSize = 15.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                lineHeight = 21.sp,
                text = "${address.houseNumber}, ${address.address}, ${address.landmark}, ${address.city}, ${address.state}, ${address.pincode}.",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            CustomRadioButton(
//                isSelected = isSelected
//            )

            Box {
                IconButton(onClick = {
                    onMenuClick(address._id)
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = {
                        onMenuClick(address._id)
                    }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            onSetDefault()
                        },
                        text = {
                            Text("Set as Default")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            onDelete()
                        },
                        text = {
                            Text("Delete")
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    SwapGoTheme {
        AddressItem(
            address = Address(
                address = "Vit Bhopal University",
                defaultAddress = true,
                _id = "1",
                userId = "1",
                createdAt = "2021-09-01T00:00:00.000Z",
                updatedAt = "2021-09-01T00:00:00.000Z",
                __v = 0,
                phoneNumber = "7648671823",
                pincode = "123456",
                state = "Madhya Pradesh",
                city = "Kotri Kalan",
                deliveryAvailable = true,
                pickupAvailable = true,
                name = "Sk Sahil Islam",
                landmark = "Chancellor Residence",
                codAvailable = true,
                houseNumber = "A-409"
            ),
            isSelected = true,
            isDefault = true,
            isMenuExpanded = false,
            onClick = {},
            onSetDefault = {},
            onDelete = {},
            onMenuClick = {}
        )
    }
}

@Composable
fun PleaseWaitLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
//            .height(90.dp)
            .shadow(8.dp, CardShape.medium)
            .clip(CardShape.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 20.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Please wait...",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.5.sp
            )
        }
    }
}
