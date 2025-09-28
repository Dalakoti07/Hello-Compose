package com.dalakoti.android.hellocompose.customLayout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val TAG = "CustomLayoutExamples"

@Composable
fun CustomLayoutExamples() {
    CustomColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalSpacing = 12.dp,
    ) {
        Text(
            text = "One",
            modifier = Modifier
                .background(Color(0xFFEFEFEF))
                .padding(8.dp)
        )
        Text(
            text = "Two — a longer item",
            modifier = Modifier
                .background(Color(0xFFD0F0C0))
                .padding(8.dp)
        )
        Text(
            text = "Three",
            modifier = Modifier
                .background(Color(0xFFFFE0B2))
                .padding(8.dp)
        )
    }
}

@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        Log.d(TAG, "CustomColumn: $measurables $constraints")
        val spacingPx = verticalSpacing.roundToPx()

        // Measure children with flexible height
        val placeables = measurables.map { measurable ->
            Log.d(TAG, "CustomColumn: $measurable")
            measurable.measure(constraints.copy(minHeight = 0))
        }
        Log.d(TAG, "placecables $placeables")

        val contentWidth = placeables.maxOfOrNull { it.width } ?: 0

        val width = if (constraints.hasBoundedWidth) {
            constraints.maxWidth
        } else {
            contentWidth.coerceIn(constraints.minWidth, constraints.maxWidth)
        }

        val rawHeight = placeables.sumOf { it.height } +
            spacingPx * (placeables.size - 1).coerceAtLeast(0)

        val height = rawHeight.coerceIn(constraints.minHeight, constraints.maxHeight)

        layout(width, height) {
            var yPosition = 0

            placeables.forEachIndexed { index, placeable ->
                val x = when (horizontalAlignment) {
                    Alignment.Start -> 0
                    Alignment.CenterHorizontally -> (width - placeable.width) / 2
                    Alignment.End -> width - placeable.width
                    else -> 0
                }

                placeable.placeRelative(x, yPosition)

                yPosition += placeable.height
                if (index != placeables.lastIndex) yPosition += spacingPx
            }
        }
    }
}
