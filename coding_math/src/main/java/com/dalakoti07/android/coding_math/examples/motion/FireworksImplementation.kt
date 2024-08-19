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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.dalakoti07.android.coding_math.examples.dpToPx

private const val TAG = "FireworksImplementation"

@Composable
fun FireWorks(
    modifier: Modifier,
) {
    val particles = remember { mutableStateListOf<Particle>() }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp.dpToPx()
    val screenWidth = configuration.screenWidthDp.dp.dpToPx()

    val centerX = screenWidth / 2
    // start at 1/3rd
    val centerY = screenHeight / 3

    Log.d(TAG, "ParticleSystem centerX $centerX and centerY $centerY")
    FireWorksSimulator(
        particles,
        centerX.toInt(),
        centerY.toInt(),
        modifier,
        screenWidth,
        screenHeight,
    )
}

@Composable
fun FireWorksSimulator(
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
                    particle.newPositionUnderGravity()
                }
                val filtered = newList.filter {
                    it.isWithInBounds(screenWidth, screenHeight)
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
