package com.dalakoti07.android.awsm_animation.awsm

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun TextAnimationExample() {
    val infiniteTransition = rememberInfiniteTransition(label = "TextAnimationExample")
    val textScaling by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = EaseIn,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "",
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black
            )
    ) {
        Text(
            "J-Compose",
            style = TextStyle(
                color = Color(0xFF666666),
                fontSize = 40.sp,
            ),
            modifier = Modifier.align(
                Alignment.Center,
            ).graphicsLayer {
                scaleX = textScaling
                scaleY = textScaling
            },
        )
    }
}