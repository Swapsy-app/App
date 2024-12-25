package com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.freeupcopy.R
import com.example.freeupcopy.data.local.Address
import com.example.freeupcopy.ui.presentation.sell_screen.SellUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.SellViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.AnnouncementComposable
import com.example.freeupcopy.ui.presentation.sell_screen.weight_screen.CustomRadioButton
import com.example.freeupcopy.ui.theme.AnnouncementIconColor
import com.example.freeupcopy.ui.theme.AnnouncementTextColor
import com.example.freeupcopy.ui.theme.ButtonShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    onNewLocationClick: () -> Unit,
    selectedLocation: Int,
    onClose: () -> Unit,
    sellViewModel: SellViewModel,
    onLocationClick: () -> Unit,
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val state by locationViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
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
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
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
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_location),
                                contentDescription = "add location",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.size(13.dp))

                            Text(
                                text = "Add New Location", color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }

//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { onNewLocationClick() },
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp, vertical = 12.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_add_location),
//                        contentDescription = "add location"
//                    )
//                    Spacer(modifier = Modifier.size(12.dp))
//                    Text(
//                        text = "New location",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp
//                    )
//                }
//            }
//        }
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
                items(state.addresses) { location ->
                    AddressItem(
                        address = location,
                        isSelected = location.id == selectedLocation,
                        isDefault = location.id == state.defaultAddressId,
                        onClick = {
                            val currentState = lifeCycleOwner.lifecycle.currentState
                            if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                                sellViewModel.onEvent(SellUiEvent.AddressChange(location))
                                onLocationClick()
                            }
                        },
                        onMenuClick = { addressId ->
                            locationViewModel.onEvent(LocationUiEvent.OnExpandMenuClick(addressId))
                        },
                        onSetDefault = {
                            locationViewModel.onEvent(LocationUiEvent.OnSetDefault(location.id))
                        },
                        onDelete = {
                            locationViewModel.onEvent(LocationUiEvent.OnDelete(location))
                            if (state.addresses.size == 1) {
                                sellViewModel.onEvent(SellUiEvent.AddressChange(null))
                            }
                        },
                        isMenuExpanded = state.isMenuExpandedAddressId == location.id,
                    )
                }
            }
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
    onMenuClick: (Int) -> Unit
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
        horizontalArrangement = Arrangement.Center
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
                        .padding(horizontal = 10.dp, vertical = 4.dp)
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
                lineHeight = 16.sp,
                fontSize = 17.sp,
                text = address.address,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomRadioButton(
                isSelected = isSelected
            )

            Box {
                IconButton(onClick = {
                    onMenuClick(address.id)
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = {
                        onMenuClick(address.id)
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


//@Preview(showBackground = true)
//@Composable
//fun LocationScreenPreview() {
//    SwapsyTheme {
//        LocationScreen(
//            onNewLocationClick = { },
//            selectedLocation = "St 1234, Los Angeles, CA 90001, USA",
//            onClose = { },
//            sellViewModel = SellViewModel(),
//            onLocationClick = { }
//        )
//    }
//}
