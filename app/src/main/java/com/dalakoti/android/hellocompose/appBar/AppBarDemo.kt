package com.dalakoti.android.hellocompose.appBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppBarDemo() {
    Scaffold(
        topBar = {
            HomeTopAppBar(
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "App content",
                modifier = Modifier.clickable {

                }
            )
        }
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
) {
    // 1
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                text = "My App"
            )
        },
        actions = {
            // 2
            ActionsMenu(
                // 3
                items = listOf(
                    ActionMenuItem.IconMenuItem.AlwaysShown(
                        title = "Search",
                        contentDescription = "Search",
                        onClick = {},
                        icon = Icons.Filled.Search,
                    ),
                    ActionMenuItem.IconMenuItem.AlwaysShown(
                        title = "Favorite",
                        contentDescription = "Favorite",
                        onClick = {},
                        icon = Icons.Filled.FavoriteBorder,
                    ),
                    ActionMenuItem.IconMenuItem.ShownIfRoom(
                        title = "Refresh",
                        contentDescription = "Refresh",
                        onClick = {},
                        icon = Icons.Filled.Refresh
                    ),
                    ActionMenuItem.NeverShown(
                        title = "Settings",
                        onClick = {},
                    ),
                    ActionMenuItem.NeverShown(
                        title = "About",
                        onClick = {},
                    ),
                ),
                // 4
                isOpen = menuExpanded,
                // 5
                onToggleOverflow = { menuExpanded = !menuExpanded },
                maxVisibleItems = 4,
            )
        },
        modifier = modifier
    )
}
