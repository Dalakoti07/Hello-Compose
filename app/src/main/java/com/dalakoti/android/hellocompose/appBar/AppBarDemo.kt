package com.dalakoti.android.hellocompose.appBar

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

enum class AppBarDemoScreen {
    HOME,
    LISTING,
}

private const val TAG = "AppBarDemo"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppBarDemo() {
    var contentScreen by remember {
        mutableStateOf(AppBarDemoScreen.HOME)
    }
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val appBarState = rememberAppBarState(
        navController,
    )
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
                AnimatedContent(
                    targetState = contentScreen,
                    label = "navigation",
                    transitionSpec = {
                        fadeIn() + slideInHorizontally(
                            animationSpec = tween(1000),
                            initialOffsetX = { fullWidth ->
                                fullWidth
                            }
                        ) with slideOutHorizontally(
                            animationSpec = tween(200),
                            targetOffsetX = {it},
                        )
                    },
                ) { screen ->
                    when (screen) {
                        AppBarDemoScreen.HOME -> {
                            HomeScreen(
                                onSettingsClick = {},
                                toNoAppBarScreen = {
                                    contentScreen = AppBarDemoScreen.LISTING
                                },
                                toManyOptionsScreen = {},
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                        AppBarDemoScreen.LISTING -> {
                            ListingScreen(
                                onStart = {
                                    Log.d(TAG, "AppBarDemo: onStart")
                                },
                                onStop = {
                                    Log.d(TAG, "AppBarDemo: onStop")
                                },
                                onOut = {
                                    contentScreen = AppBarDemoScreen.HOME
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListingScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit, // Send the 'stopped' analytics event
    onOut: () -> Unit // Send the 'stopped' analytics event
) {
    BackHandler {
        onOut()
    }
    var counter by remember {
        mutableIntStateOf(1)
    }
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    LaunchedEffect(key1 = Unit, block = {
        repeat(10){
            delay(1000)
            counter += 1
        }
    })
    DisposableEffect(key1 = lifecycleOwner){
        val observer = LifecycleEventObserver { _, event->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }
        Log.d(TAG, "ListingScreen: adding observer")
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            Log.d(TAG, "ListingScreen: removing observer")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Listing screen")
        Text(text = "Listing screen 2")
        Text(text = "Counter is $counter")
        Button(onClick = onOut) {
            Text(text = "Back")
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