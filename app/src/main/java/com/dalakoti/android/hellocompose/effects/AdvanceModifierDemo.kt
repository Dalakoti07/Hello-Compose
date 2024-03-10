package com.dalakoti.android.hellocompose.effects

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarChartExample(dataPoints: List<Float>) {
    Box(modifier = Modifier
        .padding(
            top = 10.dp,
        )
        .size(300.dp)
        .drawWithCache {
            onDrawWithContent {
                drawContent()

                // Draw axes
                drawLine(
                    start = Offset(50f, size.height),
                    end = Offset(size.width, size.height),
                    color = Color.Green,
                    strokeWidth = 10f
                )
                drawLine(
                    start = Offset(50f, 50f),
                    end = Offset(50f, size.height),
                    color = Color.Red,
                    strokeWidth = 10f
                )

                // Draw bars for each data point
                val barWidth = size.width / (dataPoints.size * 2)
                dataPoints.forEachIndexed { index, value ->
                    val left = barWidth * (index * 2 + 1)
                    val top = size.height - (value / dataPoints.max() * size.height)
                    val right = left + barWidth
                    val bottom = size.height
                    drawRect(
                        Color.Blue,
                        topLeft = Offset(left, top),
                        size = Size(right - left, bottom - top)
                    )
                }
            }
        }
    )
}

@Composable
fun FlipCardDemo() {
    var flipped by remember { mutableStateOf(false) }
    val rotationZ by animateFloatAsState(targetValue = if (flipped) 180f else 0f, label = "")

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .graphicsLayer {
                    this.rotationX = rotationZ
                    cameraDistance = 12f * density
                    shadowElevation = if (flipped) 0f else 30f
                    alpha = if (flipped) 0.3f else 0.8f
                }
                .clickable { flipped = !flipped }
                .width(350.dp)
                .height(200.dp),
            backgroundColor = Color.DarkGray,
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Hey, click me", color = Color.White,
                    fontSize = 32.sp
                )
            }
        }
    }
}

@Composable
fun AdvanceModifierDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                10.dp,
            )
            .verticalScroll(
                rememberScrollState()
            ),
    ) {
        FlipCardDemo()
        BarChartExample(dataPoints = listOf(10f,20f,30f))
    }
}
