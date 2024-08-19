package com.dalakoti07.android.coding_math.examples.motion

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

data class Particle(
    val position: Offset,
    val velocity: Offset,
    val color: Color = Color.Black,
    val size: Float = 10f,
) {
    /**
     * returns boolean to tell if screen within bound
     */
    fun isWithInBounds(screenWidth: Float, screenHeight: Float): Boolean{
        return (position.x in 0f.. screenWidth) &&
                (position.y in 0f .. screenHeight)
    }

    /**
     * Returns new position when every new 1ms is passed,
     * no gravity influence
     */
    fun newPosition(): Particle {
        return this.copy(
            position = position + Offset(
                velocity.x * (1f/1000),
                velocity.y * (1f/1000)
            )
        )
    }

    /**
     * Same as [newPosition] but under the influence of gravity,
     * given that gravity is 9.8 m/sec^2
     */
    fun newPositionUnderGravity(): Particle {
        val newVelocity = velocity + Offset(
            x = 0f,
            y = 9.8f,
        )
        return this.copy(
            velocity = newVelocity,
            position = position + newVelocity* (1f/1000)
        )
    }

    /**
     * What would the
     */
    fun newPositionUnderAcceleration(acceleration: Offset): Particle{
        val newVelocity = velocity + Offset(
            x = acceleration.x,
            y = acceleration.y,
        )
        return this.copy(
            velocity = newVelocity,
            position = position + newVelocity* (1f/1000)
        )
    }

}

private const val maxSpeed = 2000

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
