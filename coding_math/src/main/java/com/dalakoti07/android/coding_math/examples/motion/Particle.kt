package com.dalakoti07.android.coding_math.examples.motion

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class Particle(
    val position: Offset,
    val velocity: Offset,
    val color: Color = Color.Black,
    val size: Float = 10f,
)

private const val maxSpeed = 200

fun initializeParticles(centerX: Float, centerY: Float, count: Int): List<Particle> {
    return List(count) {
        val angle = Random.nextFloat() * 2 * Math.PI // Random angle in radians
        val speed = 100 + Random.nextFloat() * maxSpeed    // Random speed
        val velocity = Offset(
            x = cos(angle).toFloat() * speed,
            y = sin(angle).toFloat() * speed
        )
        Particle(
            position = Offset(centerX, centerY),
            velocity = velocity
        )
    }
}
