package com.dalakoti07.android.coding_math.examples.solar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.dalakoti07.android.coding_math.examples.dpToPx
import com.dalakoti07.android.coding_math.examples.motion.Particle
import kotlin.math.cos
import kotlin.math.sin

private fun createEarth(
    color: Color,
    screenWidth: Float,
    screenHeight: Float,
): Particle {
    val angle = 0f
    val speed = 22 * 100 // 15k
    val velocity = Offset(
        x = cos(angle) * speed,
        y = sin(angle) * speed
    )
    return Particle(
        position = Offset(screenWidth * .5f, screenHeight * 0.4f),
        velocity = velocity,
        size = 40f,
        color = color,
    )
}

/**
 * According to physics if centripetal force is applied to
 * an object, it will move in a circular path,
 *
 * relation is a = (v^2)/r
 * let r = 50
 * a = 10
 * v => 22.36
 *
 * // todo we need to create a mechanism of angle between
 *     force and velocity then only it would revolve around SUN
 *     if don't do so then earth would just fall, won't revolve
 *     around SUN
 */
@Composable
fun EarthAroundSun(
    modifier: Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.dpToPx()
    val screenHeight = configuration.screenHeightDp.dp.dpToPx()

    var earth by remember {
        mutableStateOf<Particle?>(null)
    }
    LaunchedEffect(key1 = Unit, block = {
        earth = createEarth(
            color = Color.Blue,
            screenWidth,
            screenHeight,
        )

        while (true) {
            withFrameMillis { _ ->
                earth = earth?.newPositionUnderAcceleration(
                    Offset(
                        x = 0f,
                        y = 100f,
                    )
                )
            }
        }
    })
    Box(modifier = modifier) {
        // sun
        Box(
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    color = Color.Yellow,
                    shape = CircleShape,
                )
        ) {}
        // earth
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            earth?.let { particle ->
                drawCircle(
                    color = particle.color,
                    center = particle.position,
                    radius = particle.size
                )
            }
        })
    }
}