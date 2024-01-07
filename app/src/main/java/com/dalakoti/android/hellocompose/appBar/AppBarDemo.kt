package com.dalakoti.android.hellocompose.appBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AppBarDemo() {
    // 1
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val appBarState = rememberAppBarState(
        navController,
    )
    // 2
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // 3
        topBar = {
            if (appBarState.isVisible) {
                PlaygroundTopAppBar(
                    appBarState = appBarState,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    ) { paddingValues ->
        // 4
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = HomeRoute,
            ) {
                // 5
                HomeScreen(
                    onSettingsClick = {  },
                    toNoAppBarScreen = {  },
                    toManyOptionsScreen = {  },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    toNoAppBarScreen: () -> Unit,
    toManyOptionsScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // 1
    LaunchedEffect(key1 = Unit) {
        // 2
        Home.buttons
            .onEach { button ->
                when (button) {
                    // 3
                    Home.AppBarIcons.Settings -> onSettingsClick()
                }
            }
            .launchIn(this)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = toManyOptionsScreen
            ) {
                Text(
                    text = "Many action bar items screen"
                )
            }
            Button(
                onClick = toNoAppBarScreen
            ) {
                Text(
                    text = "No app bar screen"
                )
            }
        }
    }
}



@Composable
fun PlaygroundTopAppBar(
    appBarState: AppBarState,
    modifier: Modifier = Modifier,
) {
    // 2
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        navigationIcon = {
            // 3
            val icon = appBarState.navigationIcon
            val callback = appBarState.onNavigationIconClick
            // 4
            if (icon != null) {
                IconButton(onClick = { callback?.invoke() }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = appBarState.navigationIconContentDescription
                    )
                }
            }
        },
        title = {
            //5
            val title = appBarState.title
            if (title.isNotEmpty()) {
                Text(
                    text = title
                )
            }
        },
        actions = {
            // 6
            val items = appBarState.actions
            if (items.isNotEmpty()) {
                ActionsMenu(
                    items = items,
                    isOpen = menuExpanded,
                    onToggleOverflow = { menuExpanded = !menuExpanded },
                    maxVisibleItems = 3,
                )
            }
        },
        modifier = modifier
    )
}