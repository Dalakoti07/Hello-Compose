package com.dalakoti.android.hellocompose.basics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun SimpleSinWave() {
    Canvas(modifier = Modifier
        .fillMaxSize(),
        onDraw = {
            val canvasHeight = size.height * 0.3

            val sinStartY = canvasHeight / 2
            var startAngle = 0.0f
            val finalAngle = (2 * Math.PI).toFloat()
            while (startAngle < finalAngle) {
                val pointX = startAngle * 100
                // add vertical offset
                val pointY = sin(startAngle.toDouble()) * 100 + sinStartY
                drawCircle(
                    color = Color.Black,
                    radius = 5f,
                    center = Offset(
                        x = pointX,
                        y = pointY.toFloat(),
                    )
                )
                startAngle += 0.01f
            }
        })
}