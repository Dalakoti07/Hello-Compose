package com.dalakoti.android.hellocompose.basics

import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp


@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Preview
@Composable
fun BouncingBall(){
    BoxWithConstraints(modifier = Modifier.fillMaxSize()){
        val infiniteTransition = rememberInfiniteTransition("bouncing effect")

        val ballRadius = 40.dp

        val width = maxWidth.dpToPx()
        val height = maxHeight.dpToPx() - (ballRadius*2).dpToPx()
        val ballRadiusInPx = ballRadius.dpToPx()

        val offset by infiniteTransition.animateFloat(
            targetValue = height,
            initialValue = 0f,
            label = "animate-offset",
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3_000,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Reverse,
            )
        )
        Box(
            modifier = Modifier
                .size(ballRadius * 2)
                .offset {
                    IntOffset(
                        x = ((width * 0.5f).toInt() - ballRadiusInPx).toInt(),
                        y = offset.toInt(),
                    )
                }
                .clip(
                    CircleShape
                )
                .background(
                    color = Color.Black,
                )
        )
    }

}