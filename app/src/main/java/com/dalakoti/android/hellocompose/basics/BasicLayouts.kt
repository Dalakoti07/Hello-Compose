package com.dalakoti.android.hellocompose.basics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.random.nextUInt

@Preview
@Composable
fun BasicLineDraws() {
    val numberOfLines by remember {
        mutableStateOf(50)
    }
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        val canvasWidth = size.width
        val canvasHeight = size.height

        for(i in 1..numberOfLines){
            val startOffsetX = Random.nextFloat()*canvasWidth
            val startOffsetY = Random.nextFloat()*canvasHeight

            val endOffsetX = Random.nextFloat()*canvasWidth
            val endOffsetY = Random.nextFloat()*canvasHeight

            drawLine(
                start = Offset(
                    x = startOffsetX,
                    y = startOffsetY,
                ),
                end = Offset(
                    x = endOffsetX,
                    y = endOffsetY,
                ),
                color = Color.Black,
                strokeWidth = 5.dp.toPx(),
            )
        }
    })
}
