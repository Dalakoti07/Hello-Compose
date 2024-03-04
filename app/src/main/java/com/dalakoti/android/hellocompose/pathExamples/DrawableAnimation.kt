package com.dalakoti.android.hellocompose.pathExamples

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TicketWaveComposable(modifier: Modifier) {
    val deltaXAnim = rememberInfiniteTransition(label = "")
    val dx by deltaXAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation =
            tween(
                500, easing = LinearEasing
            )
        ), label = "",
    )

    AsyncImage(
        model = "john.jpeg",
        contentDescription = "Awesome Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(width = 300.dp, height = 200.dp)
            .graphicsLayer {
                val animDx = dx
                shadowElevation = 0.dp.toPx()
                shape = GenericShape { size: Size, _: LayoutDirection ->
                    wavePath(size, animDx, 50.dp.toPx())
                }
                clip = true
            }
    )
}

/**
 * Creates a wave-shaped path along the top edge of a rectangular shape.
 *
 * @param size The size of the shape.
 * @param animDx The horizontal offset for the wave animation.
 * @param waveHeight The height of the wave peaks.
 */
fun wavePath(size: Size, animDx: Float, waveHeight: Float): Path {
    val path = Path()

    // Define the wave frequency (number of waves)
    val waveFrequency = 4

    // Start from the bottom left corner
    path.moveTo(0f, size.height)

    // Draw the bottom edge
    path.lineTo(0f, size.height)

    // Draw the wave along the top edge
    for (i in 0..waveFrequency) {
        val x = i * size.width / waveFrequency
        val y = if (i % 2 == 0) -waveHeight else 0f
        path.lineTo(x + animDx, y)
    }

    // Draw the right edge
    path.lineTo(size.width, size.height)

    // Close the path by drawing the left edge
    path.close()

    return path
}

/*
class PolyShape(private val sides: Int, private val radius: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = Path().apply { polygon(sides, radius, size.center) })
    }
}

fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}
*/
