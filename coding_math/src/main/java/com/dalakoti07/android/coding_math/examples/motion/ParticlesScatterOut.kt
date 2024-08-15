package com.dalakoti07.android.coding_math.examples.motion

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.dalakoti07.android.coding_math.examples.dpToPx

private const val TAG = "ParticlesScatterOut"

@Composable
fun ParticleSystem(modifier: Modifier = Modifier) {
    val particles = remember { mutableStateListOf<Particle>() }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp.dpToPx()
    val screenWidth = configuration.screenWidthDp.dp.dpToPx()

    val centerX = screenWidth / 2
    val centerY = screenHeight / 2

    Log.d(TAG, "ParticleSystem centerX $centerX and centerY $centerY")
    ParticleMotionSimulator(
        particles,
        centerX.toInt(),
        centerY.toInt(),
        modifier,
        screenWidth,
        screenHeight,
    )
}

@Composable
fun ParticleMotionSimulator(
    particles: SnapshotStateList<Particle>,
    centerX: Int,
    centerY: Int,
    modifier: Modifier,
    screenWidth: Float,
    screenHeight: Float,
) {
    LaunchedEffect(key1 = true) {
        particles.addAll(
            initializeParticles(
                centerX.toFloat(),
                centerY.toFloat(),
                100,
            )
        )
        while (true) {
            if(particles.isEmpty()) return@LaunchedEffect
            withFrameMillis { _ ->
                val newList = particles.map { particle ->
                    particle.copy(
                        position = particle.position + Offset(
                            particle.velocity.x * (16f/1000),
                            particle.velocity.y * (16f/1000)
                        )
                    )
                }
                val filtered = newList.filter {
                    (it.position.x in 0f.. screenWidth) &&
                            (it.position.y in 0f .. screenHeight)
                }
                particles.clear()
                particles.addAll(filtered)
            }
        }
    }

    Box(modifier = modifier){
        Canvas(modifier = Modifier.fillMaxSize()) {
            particles.forEach { particle ->
                drawCircle(
                    color = particle.color,
                    center = particle.position,
                    radius = particle.size
                )
            }
        }
    }
}

