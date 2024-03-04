package com.dalakoti.android.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dalakoti.android.hellocompose.appBar.AppBarDemo
import com.dalakoti.android.hellocompose.examples.AnimationPager
import com.dalakoti.android.hellocompose.awesome.WindowInfo
import com.dalakoti.android.hellocompose.examples.BasicExamples

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomePage(
                        onBasicClick = {
                            navController.navigate("basics")
                        },
                        onAnimationClick = {
                            navController.navigate("animations")
                        },
                        appBarStuffClick = {
                            navController.navigate("appbar")
                        }
                    )
                }
                composable("basics") {
                    BasicExamples()
                }
                composable("animations") {
                    val config = LocalConfiguration.current
                    val rememberedComposeWindow by remember {
                        mutableStateOf(
                            WindowInfo(
                                config.screenWidthDp.dp,
                                config.screenHeightDp.dp
                            )
                        )
                    }
                    AnimationPager(
                        rememberedComposeWindow
                    )
                }
                composable("appbar") {
                    AppBarDemo()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomePage(
    onBasicClick: () -> Unit = {},
    onAnimationClick: () -> Unit = {},
    appBarStuffClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Mighty compose")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Card(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                onClick = {
                    onBasicClick()
                }
            ) {
                Text(
                    text = "Basics",
                    modifier = Modifier.padding(
                        6.dp,
                    ),
                    fontSize = 20.sp,
                )
            }
            Card(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                onClick = {
                    onAnimationClick()
                }
            ) {
                Text(
                    text = "Animations",
                    modifier = Modifier.padding(
                        6.dp,
                    ),
                    fontSize = 20.sp,
                )
            }
            Card(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                onClick = {
                    appBarStuffClick()
                }
            ) {
                Text(
                    text = "AppBar",
                    modifier = Modifier.padding(
                        6.dp,
                    ),
                    fontSize = 20.sp,
                )
            }
        }
    }
}
