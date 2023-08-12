package com.dalakoti.android.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.awesome.AnimationPager
import com.dalakoti.android.hellocompose.awesome.WindowInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val config = LocalConfiguration.current
            val rememberedComposeWindow by remember {
                mutableStateOf(WindowInfo(
                    config.screenWidthDp.dp,
                    config.screenHeightDp.dp
                ))
            }
            AnimationPager(
                rememberedComposeWindow
            )
        }
    }
}

