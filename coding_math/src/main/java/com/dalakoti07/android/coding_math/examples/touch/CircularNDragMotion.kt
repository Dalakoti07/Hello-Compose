package com.dalakoti07.android.coding_math.examples.touch

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
    var touchPosition by remember { mutableStateOf(Offset.Unspecified) }
    var centerPosition by remember { mutableStateOf(Offset.Unspecified) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                all = 30.dp,
            ).pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    touchPosition = change.position
                    centerPosition = Offset(
                        (size.width / 2).toFloat(),
                        (size.height / 2).toFloat(),
                    )
                    rotationAngle = calculateRotationAngle(centerPosition, touchPosition)
                    // mark that it has been handled, and
                    // event should not propagate further,
                    // like if it were vertical scroll as well then
                    // it would propagate it to parent and hence
                    // both gesture would work
                    change.consume()
                }
            }
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
                .graphicsLayer {
                    this.rotationZ = -rotationAngle
                }
                .align(
                    Alignment.Center,
                ),
        )
    }

}