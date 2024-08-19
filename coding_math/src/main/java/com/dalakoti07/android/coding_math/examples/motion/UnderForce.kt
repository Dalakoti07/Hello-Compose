package com.dalakoti07.android.coding_math.examples.motion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

private fun createParticleToThis(
    color: Color,
): Particle {
    val angle = 0f
    val speed = 1500
    val velocity = Offset(
        x = cos(angle) * speed,
        y = sin(angle) * speed
    )
    return Particle(
        position = Offset(20f, 20f),
        velocity = velocity,
        size = 10f,
        color = color,
    )
}

@Composable
fun UnderForceDemonstration(
    modifier: Modifier,
) {
    var particleUnderDemo by remember {
        mutableStateOf<Particle?>(null)
    }
    var particleUnderDemo2 by remember {
        mutableStateOf<Particle?>(null)
    }
    LaunchedEffect(key1 = Unit, block = {
        particleUnderDemo = createParticleToThis(
            color = Color.Gray
        )
        particleUnderDemo2 = createParticleToThis(
            color = Color.Black
        )

        while (true) {
            withFrameMillis { _ ->
                particleUnderDemo = particleUnderDemo?.newPositionUnderAcceleration(
                    Offset(
                        x = 0f,
                        y = 0f,
                    )
                )
                particleUnderDemo2 = particleUnderDemo2?.newPositionUnderAcceleration(
                    Offset(
                        x = 1.5f,
                        y = 8f,
                    )
                )
            }
        }
    })
    Box(modifier = modifier) {
        Text(
            text = "It was supposed to go straight linearly but under the " +
                    "influence of force it went parabolic",
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                )
                .align(
                    Alignment.Center,
                ),
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            particleUnderDemo?.let { particle ->
                drawCircle(
                    color = particle.color,
                    center = particle.position,
                    radius = particle.size
                )
            }
            particleUnderDemo2?.let { particle ->
                drawCircle(
                    color = particle.color,
                    center = particle.position,
                    radius = particle.size
                )
            }
        }
    }
}
