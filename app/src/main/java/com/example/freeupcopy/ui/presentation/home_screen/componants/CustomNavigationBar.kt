package com.example.freeupcopy.ui.presentation.home_screen.componants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.freeupcopy.R

@Composable
fun CustomNavigationBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    onHomeClick: () -> Unit,
    onCommunityClick: () -> Unit,
    onWishListClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSellClick: () -> Unit,
    navController: NavController
) {
    Box(modifier = modifier.fillMaxWidth()) {

        CustomBottomBar(
            windowInsets = windowInsets,
            onHomeClick = onHomeClick,
            onWishListClick = onWishListClick,
            onCommunityClick = onCommunityClick,
            onProfileClick = onProfileClick,
            navController = navController
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .offset(y = (-24).dp)
                .shadow(6.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(
                    onClick = onSellClick
                )
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sell",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

}

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    onHomeClick: () -> Unit,
    onCommunityClick: () -> Unit,
    onWishListClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: NavController
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            contentDescription = "Home",
            selectedIcon = painterResource(id = R.drawable.ic_home_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_home),
            onClick = onHomeClick
        ),
        BottomNavigationItem(
            contentDescription = "Community",
            selectedIcon = painterResource(id = R.drawable.ic_community_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_community),
            onClick = onCommunityClick
        ),
        BottomNavigationItem(
            contentDescription = "WishList",
            selectedIcon = painterResource(id = R.drawable.ic_favorite_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_favorite),
            onClick = onWishListClick
        ),
        BottomNavigationItem(
            contentDescription = "Profile",
            selectedIcon = painterResource(id = R.drawable.ic_person_selected),
            unselectedIcon = painterResource(id = R.drawable.ic_person),
            onClick = onProfileClick
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .windowInsetsPadding(windowInsets)
            .defaultMinSize(minHeight = 70.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        items.forEachIndexed { index, item ->
            CustomNavigationBarItem(
                selected = index == currentScreenIndex(navController.currentDestination?.route.toString()),
                onClick = {
                    selectedItem = index
                    item.onClick()
                },
                icon = item.unselectedIcon,
                selectedIcon = item.selectedIcon,
                contentDescription = item.contentDescription
            )
            if (index == 1) Spacer(modifier = Modifier.size(80.dp)) // Leave space for the FAB
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    selectedIcon: Painter,
    contentDescription: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(50.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                painter = if (selected) selectedIcon else icon,
                contentDescription = contentDescription
            )
        }

        Text(
            modifier = Modifier.offset(y=(-3).dp),
            text = contentDescription,
            fontSize = 13.sp,
            fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun CustomNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    selectedIcon: ImageVector,
    contentDescription: String
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(60.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            imageVector = if (selected) selectedIcon else icon,
            contentDescription = contentDescription
        )
    }
}

data class BottomNavigationItem(
    val contentDescription: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val onClick: () -> Unit
)

private val routeIndexMap = mapOf(
    "HomeScreen" to 0,
    "CommunityScreen" to 1,
    "WishListScreen" to 2,
    "ProfileScreen" to 3
)

private fun currentScreenIndex(currentRoute: String?): Int {
    val transformedRoute = currentRoute?.substringAfterLast('.')?.substringBefore('@') ?: return -1
    return routeIndexMap[transformedRoute] ?: -1
}
