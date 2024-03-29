package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dalakoti.android.hellocompose.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun YahooWeatherAndSun(
    modifier: Modifier = Modifier,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angleRotation by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 145f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // yellow film which goes from left to right and vice versa
    val scaleRect by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val radian by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    var iconX by remember {
        mutableStateOf(0f)
    }

    var iconY by remember {
        mutableStateOf(0f)
    }


    Box(
        modifier
            .background(Color(0xff112937))
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .clickable {}
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sun Moon",
                color = Color.White,
                fontSize = 24.sp,
            )
            Box {
                PathArcCanvas(Modifier.align(Alignment.Center), scaleRect, radian) { x, y ->
                    iconX = x
                    iconY = y
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_sun),
                    contentDescription = null,
                    modifier = Modifier
                        .offset { IntOffset(iconX.toInt(), iconY.toInt()) }
                        .rotate(angleRotation),
                    tint = Color(0xfff9d71c)
                )

                Box(
                    Modifier
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .size(320.dp)
                ) {
                    ContainedCanvas(scaleRect)
                }
            }

        }
    }

}

@Composable
private fun ContainedCanvas(scaleRect: Float) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(160.dp)
            .graphicsLayer(
                transformOrigin = TransformOrigin(0f, 0f),
                scaleX = scaleRect, scaleY = 1f, alpha = 0.1f
            )
            .background(Color(0xfff9d71c)),
    )
}

@Composable
private fun PathArcCanvas(
    modifier: Modifier,
    scaleRect: Float,
    radian: Float,
    updatedPosition: (Float, Float) -> Unit,
) {
    val circleRadius = LocalDensity.current.run { 8.dp.toPx() }
    val widthOfCircle = LocalDensity.current.run { 320.dp.toPx() }

    var centerPoint by remember {
        mutableStateOf(Offset.Zero)
    }

    var x by remember {
        mutableStateOf(0f)
    }

    var y by remember {
        mutableStateOf(0f)
    }


    LaunchedEffect(key1 = scaleRect, block = {
        val orbitRadius = widthOfCircle.div(2)
        x = (centerPoint.x + cos(radian) * orbitRadius.plus(circleRadius.times(2)))
        y = (centerPoint.y + sin(radian) * orbitRadius.plus(circleRadius.times(2)))
        updatedPosition(x, y)
    })

    Canvas(modifier = modifier.size(320.dp), onDraw = {
        centerPoint = this.center
        val firstCircle = centerPoint.copy(x = centerPoint.x.minus(widthOfCircle / 2))
        val secondCircle = centerPoint.copy(x = centerPoint.x.plus(widthOfCircle / 2))
        drawArc(
            color = Color(0xfff9d71c),
            style = Stroke(
                width = 1f,
                pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(27f, 27f))
            ), useCenter = false,
            startAngle = 180f,
            sweepAngle = 180f
        )


        drawLine(
            Color.White.copy(0.5f),
            start = firstCircle.copy(x = firstCircle.x.minus(80f)),
            secondCircle.copy(x = secondCircle.x.plus(80f)),
            4f
        )

        drawCircle(
            Color(0xfff9d71c),
            radius = circleRadius,
            center = firstCircle
        )

        drawCircle(
            Color(0xfff9d71c),
            radius = circleRadius,
            center = secondCircle
        )
    })
}


