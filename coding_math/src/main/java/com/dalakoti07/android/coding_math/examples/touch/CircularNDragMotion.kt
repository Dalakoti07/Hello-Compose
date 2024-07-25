package com.dalakoti07.android.coding_math.examples.touch

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dalakoti07.android.coding_math.examples.dpToPx
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun CircularPathNDragIllustration() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                all = 30.dp,
            )
    ) {
        Text(
            text = "Rotating in circular path, drag your " +
                    "fingers to rotate it",
            modifier = Modifier.align(
                Alignment.TopCenter,
            ).padding(
                top = 20.dp,
            ),
            textAlign = TextAlign.Center,
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
        Icon(
            contentDescription = "",
            imageVector = Icons.Outlined.ArrowForward,
            modifier = Modifier
                .size(50.dp, 50.dp)
                .offset{
                    IntOffset(
                        x = ((radius) * cos(currentAngle)).toInt(),
                        y = ((radius) * sin(currentAngle)).toInt(),
                    )
                }
                .align(
                    Alignment.Center,
                ),
        )
    }

}