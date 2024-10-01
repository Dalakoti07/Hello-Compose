package com.dalakoti07.android.awsm_animation.paths

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

private const val TAG = "Heart"

@Composable
fun HeartPathAnimation() {
    var time by remember { mutableStateOf(0f) }

    // Animation loop to update time
    val infiniteTransition = rememberInfiniteTransition(label = "heartTransition")
    val animatedTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "heartTransition",
    )

    time = animatedTime

    Canvas(modifier = Modifier.size(200.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Parametric heart equation
        fun heartX(t: Float): Float {
            return 16 * sin(t).pow(3)
        }

        fun heartY(t: Float): Float {
            return 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
        }

        // Scale the heart to fit the canvas
        val scaleFactor = canvasWidth / 34  // Scaling based on width
        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2

        // Draw heart path
        val heartPath = Path().apply {
            moveTo(
                centerX + heartX(0f) * scaleFactor,
                centerY - heartY(0f) * scaleFactor
            )
            for (t in 0..360) {
                val angle = Math.toRadians(t.toDouble()).toFloat()
                lineTo(
                    centerX + heartX(angle) * scaleFactor,
                    centerY - heartY(angle) * scaleFactor
                )
            }
        }

        drawPath(
            path = heartPath,
            color = Color.Red,
            style = Stroke(width = 4f)
        )

        // Animate object around heart path
        val currentX = centerX + heartX(time) * scaleFactor
        val currentY = centerY - heartY(time) * scaleFactor
        val deltaT = 0.01f
        val dx = heartX(time + deltaT) - heartX(time)
        val dy = heartY(time + deltaT) - heartY(time)
        val angle = atan2(dy, dx) * (180f / PI.toFloat()) // Convert to degrees

        drawCircle(
            color = Color.Blue,
            center = Offset(currentX, currentY),
            //size = Size(width = 30f, height = 15f),
            radius = 10f,
        )
    }
}
