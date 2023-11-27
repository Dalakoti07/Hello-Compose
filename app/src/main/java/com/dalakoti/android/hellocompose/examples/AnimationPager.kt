package com.dalakoti.android.hellocompose.examples

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
import com.dalakoti.android.hellocompose.awesome.ChatMessageReaction
import com.dalakoti.android.hellocompose.awesome.Github404
import com.dalakoti.android.hellocompose.awesome.GlowingProgressBar
import com.dalakoti.android.hellocompose.awesome.LikeAnimation
import com.dalakoti.android.hellocompose.awesome.LocalWindow
import com.dalakoti.android.hellocompose.awesome.MenuToClose
import com.dalakoti.android.hellocompose.awesome.MyPlayGround
import com.dalakoti.android.hellocompose.awesome.NetflixSplashAnim
import com.dalakoti.android.hellocompose.awesome.PullToRefresh
import com.dalakoti.android.hellocompose.awesome.SimpleCircles
import com.dalakoti.android.hellocompose.awesome.StarDisplay
import com.dalakoti.android.hellocompose.awesome.TwitterSplashAnimation
import com.dalakoti.android.hellocompose.awesome.WindowInfo
import com.dalakoti.android.hellocompose.awesome.YahooWeatherAndSun


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
        Box(Modifier.fillMaxSize()) {
            MenuToClose(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black,
                )) {
            ChatMessageReaction(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black,
            ), contentAlignment = Alignment.Center,){
            LikeAnimation()
        }
    }
    add {
        Github404()
    }
    add {
        TwitterSplashAnimation()
    }
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
