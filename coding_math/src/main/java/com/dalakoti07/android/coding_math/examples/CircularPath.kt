package com.dalakoti07.android.coding_math.examples

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "CircularPath"

@Preview
@Composable
fun CircularPathIllustration() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                all = 30.dp,
            )
    ) {
        Text(
            text = "Rotating in circular path",
            modifier = Modifier.align(
                Alignment.Center,
            )
        )
        val infiniteTransition = rememberInfiniteTransition("circular round effect")

        val width = maxWidth.dpToPx()
        val radius = width / 2

        val currentAngle by infiniteTransition.animateFloat(
            targetValue = (2 * Math.PI).toFloat(),
            initialValue = 0f,
            label = "animate-angle",
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5_000,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            )
        )
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawCircle(
                color = Color.Black,
                radius = 30f,
                center = Offset(
                    // extra + radius because initial offset
                    x = radius + (radius) * cos(currentAngle),
                    y = radius + (radius) * sin(currentAngle),
                ),
            )
        })
    }

}

@Preview
@Composable
fun DrawCirclesInCircularPath() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black,
            )
            .padding(
                all = 30.dp,
            )
    ) {
        CircularShapesInArc(
            modifier = Modifier
                .align(
                    Alignment.TopStart,
                )
                .padding(
                    start = 10.dp,
                    top = 10.dp,
                ),
        )
        CircularShapesInArc(
            modifier = Modifier
                .align(
                    Alignment.TopEnd,
                )
                .padding(
                    top = 40.dp,
                    end = 40.dp,
                ),
        )
        CircularShapesInArc(
            modifier = Modifier
                .align(
                    Alignment.Center,
                )
                .padding(
                    top = 40.dp,
                    end = 40.dp,
                ),
            angleDifference = 10,
            size = 150.dp,
        )
        Text(
            text = "3 circles using trignometry", color = Color.White,
            modifier = Modifier
                .align(
                    Alignment.BottomCenter,
                ),
        )
    }

}


@Composable
fun CircularShapesInArc(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    angleDifference: Int = 20,
    smallCircleRadius: Float = 5f,
    outlined: Boolean = false
) {
    val circleStyle = if (outlined) Stroke(
        width = 2f,
    ) else Fill
    Canvas(
        modifier = modifier
            .padding(3.dp)
            .size(size)
    ) {
        val radius = size / 2f
        val centerX = size / 2f

        // circle for upper half

        //first circle
        drawCircle(
            color = Color.White,
            radius = smallCircleRadius,
            center = Offset(centerX.toPx(), 0f),
            style = if (outlined) Stroke(
                width = 2f,
            ) else Fill
        )
        // note that these circle would be symmetric along main vertical axis
        var currentAngle = 0
        currentAngle += angleDifference
        while (currentAngle <= 90) {
            val cosVal = cos(currentAngle * (PI) / 180)
            val sinVal = sin(currentAngle * (PI) / 180)
            val yOffset = radius.toPx() - (cosVal * radius.toPx())
            val xOffsetPositive = centerX.toPx() + sinVal * radius.toPx()
            val xOffsetNegative = centerX.toPx() - sinVal * radius.toPx()
            // +ve axis of x
            drawCircle(
                color = Color.White,
                radius = smallCircleRadius,
                center = Offset(xOffsetPositive.toFloat(), yOffset.toFloat()),
                style = circleStyle
            )
            // -ve axis of x
            drawCircle(
                color = Color.White,
                radius = smallCircleRadius,
                center = Offset(xOffsetNegative.toFloat(), yOffset.toFloat()),
                style = circleStyle
            )

            currentAngle += angleDifference
        }

        // second half of circle

        // circle at bottom
        drawCircle(
            color = Color.White,
            radius = smallCircleRadius,
            center = Offset(centerX.toPx(), radius.toPx() * 2),
            style = circleStyle
        )
        currentAngle = 0
        currentAngle += angleDifference
        while (currentAngle <= 90) {
            val cosVal = cos(currentAngle * (PI) / 180)
            val sinVal = sin(currentAngle * (PI) / 180)
            val yOffset = radius.toPx() + (cosVal * radius.toPx())
            val xOffsetPositive = centerX.toPx() + sinVal * radius.toPx()
            val xOffsetNegative = centerX.toPx() - sinVal * radius.toPx()
            // +ve axis of x
            drawCircle(
                color = Color.White,
                radius = smallCircleRadius,
                center = Offset(xOffsetPositive.toFloat(), yOffset.toFloat()),
                style = circleStyle
            )
            // -ve axis of x
            drawCircle(
                color = Color.White,
                radius = smallCircleRadius,
                center = Offset(xOffsetNegative.toFloat(), yOffset.toFloat()),
                style = circleStyle
            )

            currentAngle += angleDifference
        }

    }

}
