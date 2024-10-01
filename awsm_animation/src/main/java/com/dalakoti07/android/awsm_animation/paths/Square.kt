package com.dalakoti07.android.awsm_animation.paths

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun RevolvingShapesAroundSquare(
    modifier: Modifier,
    title: String = "No title",
    shape: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = Color.Red,
                    shape = CircleShape,
                )
        )
    },
) {
    val canvasSize = 200
    // Infinite animation to drive the shapes around the square
    val progress by rememberInfiniteTransition(label = "progress").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "linearRestart",
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = title)
        var currentOffset by remember {
            mutableStateOf(Offset(x = 0f, y = 0f))
        }
        Canvas(
            modifier = Modifier
                .size(canvasSize.dp)
        ) {
            // Square size
            val squareSize = min(size.width, size.height) * 0.9f
            val halfCanvas = squareSize / 2
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Calculate the positions of the shapes (circles) that revolve around the square
            currentOffset = drawRevolvingShape(progress, squareSize, Offset(centerX, centerY))
        }
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    (currentOffset.x - (canvasSize.dp.toPx() / 2)).toInt(),
                    (currentOffset.y - (canvasSize.dp.toPx() / 2)).toInt()
                )
            }
        ) {
            shape()
        }
    }
}

/**
 * This is the crux of the solution, defining x,y depending upon progress
 */
private fun drawRevolvingShape(progress: Float, squareSize: Float, center: Offset): Offset {
    // Total perimeter of the square (4 sides)
    val perimeter = squareSize * 4
    // Calculate current position along the square's perimeter based on progress
    val currentDistance = perimeter * progress

    // Find which side of the square the shape is on and its exact position
    val shapeOffset = when {
        // Top side
        currentDistance <= squareSize -> Offset(
            center.x - squareSize / 2 + currentDistance,
            center.y - squareSize / 2
        )
        // Right side
        currentDistance <= squareSize * 2 -> Offset(
            center.x + squareSize / 2,
            center.y - squareSize / 2 + (currentDistance - squareSize)
        )
        // Bottom side
        currentDistance <= squareSize * 3 -> Offset(
            center.x + squareSize / 2 - (currentDistance - squareSize * 2),
            center.y + squareSize / 2
        )
        // Left side
        else -> Offset(
            center.x - squareSize / 2,
            center.y + squareSize / 2 - (currentDistance - squareSize * 3)
        )
    }

    return shapeOffset
}
