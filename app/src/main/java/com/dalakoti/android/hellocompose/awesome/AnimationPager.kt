package com.dalakoti.android.hellocompose.awesome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun AnimationPager(rememberedComposeWindow: WindowInfo) {

    var currentAnimation by remember {
        mutableStateOf(0)
    }

    CompositionLocalProvider(
        LocalWindow provides rememberedComposeWindow
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            allAnimations[currentAnimation].invoke()

            IconButton(
                {
                    if (currentAnimation > 0) {
                        currentAnimation = currentAnimation.dec()
                    }
                }, modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(Color.White.copy(alpha = 0.4f))
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            IconButton(
                {
                    if (currentAnimation < allAnimations.size.minus(1)) {
                        currentAnimation = currentAnimation.inc()
                    }
                }, modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(Color.White.copy(alpha = 0.4f))
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

private val allAnimations = mutableListOf<@Composable () -> Unit>().apply {
    add {
        YahooWeatherAndSun()
    }
    add {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            GlowingProgressBar()
        }
    }
    add {
        PullToRefresh()
    }
    add {
        SimpleCircles()
    }
    add {
        NetflixSplashAnim()
    }
    add {
        MyPlayGround()
    }
}
