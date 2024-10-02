package com.dalakoti07.android.awsm_animation.effects

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ConcentricProgressBar() {
    var isAnimating by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            AConcentricCircle(
                canvasSize = 200.dp,
                color = Color(0xFF3D99FF),
                targetAngle = 270f,
                isAnimating = isAnimating,
            )
            AConcentricCircle(
                canvasSize = 150.dp,
                color = Color(0xFFfec76f),
                targetAngle = 180f,
                isAnimating = isAnimating,
            )
            AConcentricCircle(
                canvasSize = 100.dp,
                color = Color(0xFF8b0000),
                targetAngle = 90f,
                isAnimating = isAnimating,
            )
        }
        Button(
            modifier = Modifier.padding(
                top = 20.dp,
            ),
            onClick = {
                isAnimating = true
            },
        ) {
            Text(text = "Start Button")
        }
    }
}

@Composable
fun AConcentricCircle(
    canvasSize: Dp,
    color: Color,
    targetAngle: Float,
    isAnimating: Boolean,
) {
    val strokeSize = 25
    val animatedSweepAngle by animateFloatAsState(
        targetValue = if (isAnimating) targetAngle else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing,
        ),
        label = "animatedSweepAngle",
    )
    Canvas(
        modifier = Modifier
            .size(canvasSize),
    ) {
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = animatedSweepAngle,
            useCenter = false,
            style = Stroke(
                width = strokeSize.dp.toPx(),
                cap = StrokeCap.Round,
            ),
        )
        drawArc(
            color = color.copy(
                alpha = .2f,
            ),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = strokeSize.dp.toPx(),
                cap = StrokeCap.Round,
            ),
        )
    }
}
