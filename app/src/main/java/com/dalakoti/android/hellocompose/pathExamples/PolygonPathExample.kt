package com.dalakoti.android.hellocompose.pathExamples

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.AndroidPathMeasure
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.awesome.LocalWindow
import com.dalakoti.android.hellocompose.basics.dpToPx

private const val TAG = "PolygonPathExample"

fun createPath(sides: Int, radius: Float, cx: Float, cy: Float): Path {
    val path = Path()
    val angle = 2.0 * Math.PI / sides
    path.moveTo(
        cx + (radius * Math.cos(0.0)).toFloat(),
        cy + (radius * Math.sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        path.lineTo(
            cx + (radius * Math.cos(angle * i)).toFloat(),
            cy + (radius * Math.sin(angle * i)).toFloat()
        )
    }
    path.close()
    return path
}


data class Polygon(
    val sides: Int,
    val radius: Float,
    val color: Color,
)

@Preview
@Composable
fun PolygonPathExamples() {
    val height = LocalWindow.current.screenHeightDp.dpToPx()
    val width = LocalWindow.current.screenWidthDp.dpToPx()

    val polygons = listOf(
        Polygon(sides = 3, radius = 45f, color = Color(0xffe84c65)),
        Polygon(sides = 4, radius = 53f, color = Color(0xffe79442)),
        Polygon(sides = 5, radius = 64f, color = Color(0xffefefbb)),
        Polygon(sides = 6, radius = 64f, color = Color(0xFF245AE0)),
        Polygon(sides = 7, radius = 64f, color = Color(0xFFFF5722)),
        Polygon(sides = 8, radius = 64f, color = Color(0xFF17EBD7)),
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Log.d(TAG, "full screen width $width and height $height")
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            Log.d(TAG, "canvas centerX $centerX and centerY $centerY")
            polygons.forEach {
                drawPath(
                    createPath(it.sides, size.width / 2, centerX, centerY),
                    color = it.color,
                    style = Stroke(
                        width = 5f,
                        pathEffect = PathEffect.cornerPathEffect(3f),
                    ),
                )
            }
        }
    }

}
