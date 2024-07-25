package com.dalakoti07.android.coding_math.examples.touch

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import kotlin.math.atan2

private const val TAG = "DragGestureExample"

@Composable
fun DragGestureExample() {
    var touchPosition by remember { mutableStateOf(Offset.Unspecified) }
    var centerPosition by remember { mutableStateOf(Offset.Unspecified) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
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
            text = "move your finger around and see pointer moving in your direction",
            modifier = Modifier.padding(
                top = 100.dp,
            ),
            textAlign = TextAlign.Center,
        )
        Pointer(rotation = rotationAngle)
    }
}

fun calculateRotationAngle(center: Offset, touch: Offset): Float {
    return if (touch != Offset.Unspecified) {
        val deltaX = touch.x - center.x
        val deltaY = touch.y - center.y
        // to convert from radian to degree, doing 180f/PI
        -atan2(deltaY, deltaX) * (180f / Math.PI.toFloat())
    } else {
        0f
    }
}

@Composable
fun BoxScope.Pointer(rotation: Float) {
    Icon(
        contentDescription = "",
        imageVector = Icons.Outlined.ArrowForward,
        modifier = Modifier
            .size(50.dp, 50.dp)
            .graphicsLayer {
                this.rotationZ = -rotation
            }
            .align(
                Alignment.Center,
            ),
    )
}
