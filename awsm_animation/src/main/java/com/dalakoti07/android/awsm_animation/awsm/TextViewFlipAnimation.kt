package com.dalakoti07.android.awsm_animation.awsm

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "TextViewFlipAnimation"

/**
 * Flip the text view around x axis, y axis, z axis
 * Using 0 to 1f, to go from 0 to 360 degrees
 */
@Preview
@Composable
fun TextViewFlipAnimation() {
    val animationTime = 3000
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val scope = rememberCoroutineScope()
        var isAnimationOn by remember {
            mutableStateOf(false)
        }
        val animationAngle: Float by animateFloatAsState(
            targetValue = if (isAnimationOn) 1f else 0f, label = "animationAngle",
            animationSpec = tween(durationMillis = animationTime)
        )
        Text(
            text = "Around Y Axis",
            modifier = Modifier
                .padding(
                    top = 10.dp,
                )
                .graphicsLayer {
                    rotationY = animationAngle * 360f
                },
        )
        Text(
            text = "Around X Axis",
            modifier = Modifier
                .padding(
                    top = 10.dp,
                )
                .graphicsLayer {
                    rotationX = animationAngle * 360f
                },
        )
        Text(
            text = "Around Z Axis",
            modifier = Modifier
                .padding(
                    top = 10.dp,
                )
                .graphicsLayer {
                    rotationZ = animationAngle * 360f
                },
        )
        Button(
            modifier = Modifier.padding(
                top = 10.dp,
            ),
            onClick = {
                isAnimationOn = true
                scope.launch {
                    delay(animationTime.toLong())
                    isAnimationOn = false
                }
            },
        ) {
            Text(text = "Rotate around axis")
        }

    }
}

// todo fix this, the problem is that I want to start animating value from 0 to 1,
//  on each index change, looks like update is good for this
/**
 * In Saurabh, firstly S would rotate, then A would rotate, then U,
 */
@Composable
fun OneWordFlipAnimation() {
    val animationTime = 1000
    val lettersArray by remember {
        mutableStateOf(listOf("S", "A", "U", "R", "A", "B", "H"))
    }
    var currentIndex by remember {
        mutableIntStateOf(1)
    }
    var isAnimationOn by remember {
        mutableStateOf(false)
    }
    val animationAngle: Float by animateFloatAsState(
        targetValue = if (isAnimationOn) 7f else 0f,
        label = "animationAngle",
        animationSpec = tween(durationMillis = animationTime * 7),
        finishedListener = {
            isAnimationOn = false
            currentIndex = 1
        }
    )
    Log.d(TAG, "currentIdx $currentIndex; isAnimationOn $isAnimationOn; angle: $animationAngle")
    LaunchedEffect(
        key1 = isAnimationOn,
        block = {
            if (isAnimationOn) {
                while (currentIndex != lettersArray.size) {
                    delay(1000)
                    currentIndex += 1
                }
            }
        },
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                lettersArray.forEachIndexed { idx, letter ->
                    Text(
                        text = letter,
                        modifier = Modifier.graphicsLayer {
                            if((idx + 1 == currentIndex) && isAnimationOn)
                                rotationX = animationAngle * 360f
                        },
                        style = TextStyle(
                            fontSize = 40.sp,
                        ),
                    )
                }
            }
            Button(
                onClick = {
                    isAnimationOn = true
                },
            ) {
                Text(text = "Start")
            }
        }

    }
}
