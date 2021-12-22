package com.dalakoti.android.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloContent()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HelloContent() {
    val offsetX = remember { androidx.compose.animation.core.Animatable(500f) }
    val offsetY = remember { androidx.compose.animation.core.Animatable(500f) }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(
                R.drawable.ic_launcher_background
            ),
            contentDescription = "s",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.toInt(),
                        offsetY.value.toInt()
                    )
                }
                .width(30.dp)
                .height(30.dp)
        )
        Button(onClick = {

        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Move")
        }
    }
    //Finally run the animation on the Click of your button or whenever you wants to start it...
    LaunchedEffect(key1 = true){
        offsetX.animateTo(
            targetValue = 100f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 0
            )
        )
    }
    LaunchedEffect(key1 = true, block = {
        offsetY.animateTo(
            targetValue = 100f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 0
            )
        )
    })
}

/*
        AnimatedContent(
            targetState = 10,
            transitionSpec = {
                // Compare the incoming number with the previous number.
                if (targetState > initialState) {
                    // If the target number is larger, it slides up and fades in
                    // while the initial (smaller) number slides up and fades out.
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    // If the target number is smaller, it slides down and fades in
                    // while the initial number slides down and fades out.
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
            }
        ) {targetCount ->
            Text(text = "$targetCount")
        }
 */