package com.dalakoti07.android.coding_math.examples

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun EllipticalPath() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                all = 30.dp,
            )
    ) {
        val infiniteTransition = rememberInfiniteTransition("circular round effect")

        val width = maxWidth.dpToPx()
        val height = maxHeight.dpToPx()
        val minAxis = width / 2
        val maxAxis = height / 2


        val currentAngle by infiniteTransition.animateFloat(
            targetValue = (2 * Math.PI).toFloat(),
            initialValue = 0f,
            label = "animate-angle",
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5_000,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            )
        )
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawCircle(
                color = Color.Black,
                radius = 30f,
                center = Offset(
                    // extra + radius because initial offset
                    x = minAxis + (minAxis) * cos(currentAngle),
                    y = maxAxis + (maxAxis) * sin(currentAngle),
                ),
            )
        })

        Text(
            text = "Rotating in eliptical path",
            modifier = Modifier.align(
                Alignment.Center,
            )
        )
    }
}
