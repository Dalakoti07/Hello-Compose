package com.dalakoti07.android.awsm_animation.awsm

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Making infinite animation with help of LaunchedEffect
 * and Animatable type
 */
@Preview
@Composable
fun IosCallAnimation() {
    var selectedDot by remember {
        mutableIntStateOf(0)
    }
    var isExpanding by remember {
        mutableStateOf(true)
    }
    val scaleFactor = remember {
        Animatable(0f)
    }
    val lightColor by remember {
        mutableStateOf(
            Color(0xFF0125aa).copy(
                alpha = 0.1f
            )
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            if (isExpanding) {
                scaleFactor.animateTo(
                    targetValue = 2.5f,
                    animationSpec = tween(durationMillis = 1000),
                )
            } else {
                scaleFactor.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 2000),
                )
            }
            isExpanding = !isExpanding
        }
    })
    LaunchedEffect(
        key1 = Unit,
        block = {
            while (true) {
                delay(1000)
                selectedDot = (selectedDot + 1) % 3
            }
        },
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
//                .size(200.dp) // instead of this use 0.5f and 1f ratio
                .fillMaxWidth(0.55f)
                .aspectRatio(1f)
                .background(
                    shape = CircleShape,
                    color = Color(0xFF002488),
                )
                .graphicsLayer {
                    scaleX = scaleFactor.value
                    scaleY = scaleFactor.value
                },
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
                    .shadow(
                        elevation = 1.dp,
                        shape = CircleShape,
                        spotColor = if (scaleFactor.value > 2f) lightColor
                            else Color(0xFF0125aa),
                        ambientColor = if (scaleFactor.value > 2f) lightColor
                            else Color(0xFF0125aa),
                    )
                    .background(
                        shape = CircleShape,
                        color = if (scaleFactor.value > 2f) lightColor
                        else Color(0xFF0125aa),
                    ),
                contentAlignment = Alignment.Center,
            ) {

            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Calling", color = Color.White, style = TextStyle(
                    fontSize = 20.sp,
                )
            )
            (0..2).forEach { idx ->
                Box(
                    modifier = Modifier
                        .padding(
                            start = 2.dp,
                            bottom = 4.dp,
                        )
                        .background(
                            color = if (selectedDot == idx) Color.White
                            else Color(0xFF007bfe),
                            shape = CircleShape,
                        )
                        .size(4.dp)
                )
            }
        }
    }
}