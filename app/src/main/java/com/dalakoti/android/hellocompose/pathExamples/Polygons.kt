package com.dalakoti.android.hellocompose.pathExamples

import android.graphics.ComposePathEffect
import android.graphics.CornerPathEffect
import android.graphics.DashPathEffect
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PathDashPathEffect
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.FloatProperty
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.awesome.PaintMode
import kotlin.math.PI


/*
class PolygonLapsDrawable : Drawable() {

    var progress = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            callback?.invalidateDrawable(this)
        }

    var dotProgress = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            callback?.invalidateDrawable(this)
        }

    private val polygons = listOf(
        Polygon(15, 0xffe84c65.toInt(), 362f, 2),
        Polygon(14, 0xffe84c65.toInt(), 338f, 3),
        Polygon(13, 0xffd554d9.toInt(), 314f, 4),
        Polygon(12, 0xffaf6eee.toInt(), 292f, 5),
        Polygon(11, 0xff4a4ae6.toInt(), 268f, 6),
        Polygon(10, 0xff4294e7.toInt(), 244f, 7),
        Polygon(9, 0xff6beeee.toInt(), 220f, 8),
        Polygon(8, 0xff42e794.toInt(), 196f, 9),
        Polygon(7, 0xff5ae75a.toInt(), 172f, 10),
        Polygon(6, 0xffade76b.toInt(), 148f, 11),
        Polygon(5, 0xffefefbb.toInt(), 128f, 12),
        Polygon(4, 0xffe79442.toInt(), 106f, 13),
        Polygon(3, 0xffe84c65.toInt(), 90f, 14)
    )

    private val cornerEffect = CornerPathEffect(8f)

    private val linePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = PaintMode.STROKE
        strokeWidth = 4f
        pathEffect = cornerEffect
    }

    private val dotPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = 0xff0e0d0e.toInt()
        style = PaintMode.FILL
    }

    private val pathDot = Path().apply {
        addCircle(0f, 0f, 8f, Path.Direction.CW)
    }

    override fun draw(canvas: Canvas) {
        polygons.forEach { polygon ->
            linePaint.color = polygon.color
            if (progress < 1f) {
                val progressEffect = DashPathEffect(
                    floatArrayOf(0f, (1f - progress) * polygon.length, progress * polygon.length, 0f),
                    polygon.initialPhase)
                linePaint.pathEffect = ComposePathEffect(progressEffect, cornerEffect)
            }
            canvas.drawPath(polygon.path, linePaint)
        }
        // loop separately to ensure the dots are on top
        polygons.forEach { polygon ->
            val phase = polygon.initialPhase + dotProgress * polygon.length * polygon.laps
            dotPaint.pathEffect = PathDashPathEffect(pathDot, polygon.length, phase, TRANSLATE)
            canvas.drawPath(polygon.path, dotPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        linePaint.alpha = alpha.toFloat()
        dotPaint.alpha = alpha.toFloat()
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    override fun setColorFilter(p0: android.graphics.ColorFilter?) {
        linePaint.colorFilter = colorFilter
        dotPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicWidth() = width

    override fun getIntrinsicHeight() = height

    private class Polygon(val sides: Int, val color: Int, radius: Float, val laps: Int) {
        val path = createPath(sides, radius)
        val length by lazy(LazyThreadSafetyMode.NONE) {
            pathMeasure.setPath(path, false)
            pathMeasure.length
        }
        val initialPhase by lazy(LazyThreadSafetyMode.NONE) {
            (1f - (1f / (2 * sides))) * length
        }

        private fun createPath(sides: Int, radius: Float): Path {
            val path = Path()
            val angle = 2.0 * PI / sides
            val startAngle = PI / 2.0 + Math.toRadians(360.0 / (2 * sides))
            path.moveTo(
                cx + (radius * Math.cos(startAngle)).toFloat(),
                cy + (radius * Math.sin(startAngle)).toFloat())
            for (i in 1 until sides) {
                path.lineTo(
                    cx + (radius * Math.cos(startAngle - angle * i)).toFloat(),
                    cy + (radius * Math.sin(startAngle - angle * i)).toFloat())
            }
            path.close()
            return path
        }
    }

    companion object {
        private const val width = 1080
        private const val height = 1080
        private const val cx = (width / 2).toFloat()
        private const val cy = (height / 2).toFloat()
        private val pathMeasure = PathMeasure()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    object PROGRESS : FloatProperty<PolygonLapsDrawable>("progress") {
        override fun setValue(drawable: PolygonLapsDrawable, progress: Float) {
            drawable.progress = progress
        }

        override fun get(drawable: PolygonLapsDrawable) = drawable.progress
    }

    @RequiresApi(Build.VERSION_CODES.N)
    object DOT_PROGRESS : FloatProperty<PolygonLapsDrawable>("dotProgress") {
        override fun setValue(drawable: PolygonLapsDrawable, dotProgress: Float) {
            drawable.dotProgress = dotProgress
        }

        override fun get(drawable: PolygonLapsDrawable) = drawable.dotProgress
    }

}*/
