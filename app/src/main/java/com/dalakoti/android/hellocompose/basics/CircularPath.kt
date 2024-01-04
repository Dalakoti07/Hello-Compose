package com.dalakoti.android.hellocompose.basics

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CircularPathIllustration(){

    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(
        all = 30.dp,
    )){
        val infiniteTransition = rememberInfiniteTransition("circular round effect")

        val width = maxWidth.dpToPx()
        val height = maxHeight.dpToPx()

        // center is
        val originX = width/2
        val originY = height/2

        val currentAngle by infiniteTransition.animateFloat(
            targetValue = (2*Math.PI).toFloat(),
            initialValue = 0f,
            label = "animate-angle",
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5_000,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Reverse,
            )
        )
        Box(
            modifier = Modifier
                .size(10.dp)
                .offset(

                )
                .clip(
                    CircleShape
                )
                .background(
                    color = Color.Black,
                )
        )
    }

}