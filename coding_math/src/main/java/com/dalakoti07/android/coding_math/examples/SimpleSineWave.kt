package com.dalakoti07.android.coding_math.examples

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.sin

private const val TAG = "SimpleSineWave"

@Preview
@Composable
fun SimpleSinWave() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                all = 40.dp,
            ),
        onDraw = {
            val canvasHeight = size.height
            val canvasWidth = size.width

            Log.d(TAG, "SimpleSinWave: width -> $canvasWidth and height -> $canvasHeight")

            val path = Path()

            // first half
            path.moveTo(0f, (canvasHeight * 0.5f))
            path.quadraticBezierTo(
                canvasWidth * 0.25f,
                0f,
                canvasWidth * 0.5f,
                canvasHeight * 0.5f,
            )

            path.moveTo(canvasWidth * 0.5f, (canvasHeight * 0.5f))
            // second half
            path.quadraticBezierTo(
                canvasWidth * 0.75f,
                canvasHeight,
                canvasWidth,
                canvasHeight * 0.5f,
            )

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(
                    width = 1.dp.toPx(),
                )
            )
        },
    )
}

@Preview
@Composable
fun SimpleSinWaveUsingDots() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        val canvasHeight = size.height * 0.3

        val sinStartY = canvasHeight / 2
        var startAngle = Math.PI
        val finalAngle = (3 * Math.PI).toFloat()
        while (startAngle < finalAngle) {
            val pointX = startAngle * 100
            // add vertical offset
            val pointY = sin(startAngle) * 100 + sinStartY
            drawCircle(
                color = Color.Black,
                radius = 5f,
                center = Offset(
                    x = pointX.toFloat(),
                    y = pointY.toFloat(),
                )
            )
            startAngle += 0.01f
        }
    })
}