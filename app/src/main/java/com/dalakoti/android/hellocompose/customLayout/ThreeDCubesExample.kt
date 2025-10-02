package com.dalakoti.android.hellocompose.customLayout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ThreeDCubeBarChart(
    modifier: Modifier = Modifier,
    values: List<Int>,
    cubeSize: Dp = 28.dp,
    depthFraction: Float = 0.35f,
    gap: Dp = 8.dp,
    labelGap: Dp = 5.dp,
    animate: Boolean = true,
    colorForValue: (value: Int, max: Int) -> Color = { v, m -> barGreenFor(v, m) },
) {
    val maxStack = (values.maxOrNull() ?: 0).coerceAtLeast(1)
    val depth = cubeSize * depthFraction
    // Total bar height to comfortably fit the tallest stack
    val barHeight = (cubeSize * maxStack) + (depth * (maxStack - 1))

    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.Bottom,
    ) {
        values.forEachIndexed { index, count ->
            val target = if (animate) 1f else 1f
            val progress by animateFloatAsState(
                targetValue = target,
                animationSpec = tween(durationMillis = 900, delayMillis = if (animate) index * 120 else 0)
            )
            // Effective bar height from bottom to top face of the top cube:
            val stackHeight = cubeSize + depth * count
            val barColor = colorForValue(count, maxStack)
            // Each bar: stack of cubes bottom-aligned. We offset each cube up by depth per layer
            Box(
                modifier = Modifier
                    .width(cubeSize + depth)
                    .height(barHeight),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Scale from bottom to animate height growth
                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            transformOrigin = TransformOrigin(0.5f, 1f),
                            scaleY = progress
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    repeat(count) { layer ->
                        Cube3D(
                            size = cubeSize,
                            depthFraction = depthFraction,
                            baseColor = barColor,
                            modifier = Modifier.offset(y = -depth * layer)
                        )
                    }
                }

                // Label hovering above current bar
                Text(
                    text = count.toString(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = -stackHeight - labelGap),
                    color = Color(0xFF1B5E20)
                )
            }
        }
    }
}

@Composable
fun ThreeDCubesExample(
    modifier: Modifier = Modifier,
    values: List<Int> = listOf(3, 5, 2, 6, 4),
    cubeSize: Dp = 28.dp,
    depthFraction: Float = 0.35f,
    gap: Dp = 1.dp,
) {
    ThreeDCubeBarChart(
        modifier = modifier,
        values = values,
        cubeSize = cubeSize,
        depthFraction = depthFraction,
        gap = gap,
        labelGap = 5.dp,
        animate = true,
        colorForValue = { v, m -> barGreenFor(v, m) }
    )
}

@Composable
private fun Cube3D(
    size: Dp,
    depthFraction: Float,
    baseColor: Color,
    modifier: Modifier = Modifier,
) {
    val depth = size * depthFraction
    androidx.compose.foundation.Canvas(
        modifier = modifier
            .width(size + depth)
            .height(size + depth)
    ) {
        val w = (size.toPx())
        val h = (size.toPx())
        val d = (depth.toPx())

        // Colors for faces: top lighter, side darker
        val front = baseColor
        val top = blendColors(baseColor, Color.White, 0.35f)
        val side = blendColors(baseColor, Color.Black, 0.25f)

        // Draw top face (parallelogram)
        val topPath = Path().apply {
            moveTo(0f, d)
            lineTo(d, 0f)
            lineTo(w + d, 0f)
            lineTo(w, d)
            close()
        }
        drawPath(path = topPath, color = top, style = Fill)

        // Draw side face (right)
        val sidePath = Path().apply {
            moveTo(w, d)
            lineTo(w + d, 0f)
            lineTo(w + d, h)
            lineTo(w, h + d)
            close()
        }
        drawPath(path = sidePath, color = side, style = Fill)

        // Draw front face (rectangle)
        val frontPath = Path().apply {
            moveTo(0f, d)
            lineTo(w, d)
            lineTo(w, h + d)
            lineTo(0f, h + d)
            close()
        }
        drawPath(path = frontPath, color = front, style = Fill)
    }
}

private fun blendColors(a: Color, b: Color, t: Float): Color {
    val clamped = t.coerceIn(0f, 1f)
    val r = a.red + (b.red - a.red) * clamped
    val g = a.green + (b.green - a.green) * clamped
    val bl = a.blue + (b.blue - a.blue) * clamped
    val al = a.alpha + (b.alpha - a.alpha) * clamped
    return Color(r, g, bl, al)
}

private fun barGreenFor(value: Int, max: Int): Color {
    val t = if (max <= 0) 0f else (value.toFloat() / max.toFloat()).coerceIn(0f, 1f)
    val base = Color(0xFF4CAF50)
    // Darken proportionally with height (taller = darker)
    return blendColors(base, Color.Black, 0.35f * t)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ThreeDCubesExamplePreview() {
    ThreeDCubesExample(
        values = listOf(2, 4, 6, 3, 5),
        cubeSize = 24.dp,
        depthFraction = 0.35f,
        gap = 1.dp
    )
}
