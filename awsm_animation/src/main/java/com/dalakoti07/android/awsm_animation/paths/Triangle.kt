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
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun RevolvingShapesAroundTriangle(
    modifier: Modifier,
    title: String = "No title",
    shape: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = Color.Red,
                    shape = CircleShape,
                )
        )
    },
){
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

    Box(modifier = modifier) {
        Text(text = title, modifier = Modifier.align(Alignment.Center))
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
            currentOffset = calculatePathForTriangle(progress, squareSize, Offset(centerX, centerY))
        }
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    (currentOffset.x).toInt(),
                    (currentOffset.y).toInt()
                )
            }
        ) {
            shape()
        }
    }
}

private fun calculatePathForTriangle(
    progress: Float, // Progress from 0 to 1, representing position along the triangle
    sideLength: Float,       // Size of the triangle (height from center to vertex)
    center: Offset, // Center X of the triangle
): Offset {
    // Define the three vertices of the triangle
    val halfSide = sideLength / 2
    val height = (sideLength * (Math.sqrt(3.0) / 2)).toFloat() // Height of the equilateral triangle

    val vertex1 = Offset(center.x, center.y - height / 2) // Top vertex
    val vertex2 = Offset(center.x - halfSide, center.y + height / 2) // Bottom left vertex
    val vertex3 = Offset(center.x + halfSide, center.y + height / 2) // Bottom right vertex

    // Determine the total progress and map it to one of the triangle sides
    val segmentProgress = progress * 3 // Multiply by 3 since there are 3 sides
    return when {
        segmentProgress <= 1f -> {
            // Move along the first side (vertex1 to vertex2)
            lerp(vertex1, vertex2, segmentProgress)
        }
        segmentProgress <= 2f -> {
            // Move along the second side (vertex2 to vertex3)
            lerp(vertex2, vertex3, segmentProgress - 1f)
        }
        else -> {
            // Move along the third side (vertex3 to vertex1)
            lerp(vertex3, vertex1, segmentProgress - 2f)
        }
    }
}

/**
 * To Lerp means to move from point A to point B by an amount t,
 * where t is greater than or equal to zero and less than or equal to one.
 * t is the portion of the distance between the two points you want to lerp.
 *
 * So for example, say I have point A at 0,0 and B at 5,10, with a t of 0.7.
 * The result would be 0.35, 0.7 which is 70% of the distance along the
 * linear path from A to B.
 */
private fun lerp(start: Offset, end: Offset, progress: Float): Offset {
    val x = start.x + (end.x - start.x) * progress
    val y = start.y + (end.y - start.y) * progress
    return Offset(x, y)
}
