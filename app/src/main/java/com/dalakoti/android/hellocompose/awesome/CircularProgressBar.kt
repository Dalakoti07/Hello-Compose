package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun GlowingProgressBar(
    size: Dp = 50.dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "circularRing")
    val angleAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ), label = ""
    )
    Box(
        modifier = Modifier
            .size(size)
    ) {
        // for outer circle, greyed part
        Canvas(modifier = Modifier.align(
            Alignment.Center
        ).fillMaxSize(), onDraw = {
            drawCircle(
                color = Color.DarkGray,
                style = Stroke(
                    width = 5f,
                )
            )
        })
        Canvas(modifier = Modifier.align(
            Alignment.Center
        ).fillMaxSize(), onDraw = {
            drawArc(
                color =
                Color(0xfff9d71c),
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round,
                    join =
                    StrokeJoin.Round,
                ),
                startAngle = angleAnimation,
                sweepAngle = 360 / 8f,
                useCenter = false
            )
        })
    }
}

/*
@Composable
fun MyCircularProgressBar() {
    val rotationState = rememberInfiniteTransition(label = "progressBar")

    val rotationValue by rotationState.animateFloat(
        initialValue = -90f,
        targetValue = 360f+90,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "",
    )

    Canvas(
        modifier = Modifier.size(30.dp)
    ) {
        val canvasSize = size.minDimension
        val strokeWidth = 10f
        val center = Offset(size.width / 2, size.height / 2)

        drawArc(
            color = Color.Blue,
            startAngle = -90f,
            sweepAngle = rotationValue,
            useCenter = false,
            topLeft = Offset(center.x - canvasSize / 2, center.y - canvasSize / 2),
            size = Size(canvasSize, canvasSize),
            style = Stroke(width = strokeWidth)
        )
    }
}*/
