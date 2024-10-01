package com.dalakoti07.android.awsm_animation.effects

import android.util.Log
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val TAG = "Demo"

@Composable
fun AllEasingEffects() {
    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp
    Log.d(TAG, "AllEasingEffects: $screenWidthPx")

    val duration = 2000
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        // Display all four balls stacked vertically with different easing animations
        BallWithEasing("Linear", duration = duration, maxWidth = screenWidthPx.dp - 50.dp)
        Spacer(modifier = Modifier.height(10.dp))
        BallWithEasing("Ease", duration = duration, maxWidth = screenWidthPx.dp - 50.dp)
        Spacer(modifier = Modifier.height(10.dp))
        BallWithEasing("EaseIn", duration = duration, maxWidth = screenWidthPx.dp - 50.dp)
        Spacer(modifier = Modifier.height(10.dp))
        BallWithEasing("EaseOut", duration = duration, maxWidth = screenWidthPx.dp - 50.dp)
        Spacer(modifier = Modifier.height(10.dp))
        BallWithEasing("EaseInOut", duration = duration, maxWidth = screenWidthPx.dp - 50.dp)
    }
}

@Composable
fun BallWithEasing(easingType: String, duration: Int, maxWidth: Dp) {
    var startAnimation by remember { mutableStateOf(false) }
    // Set easing based on the selected animation type
    val easing = when (easingType) {
        "Linear" -> tween<Dp>(durationMillis = duration, easing = LinearEasing)
        "EaseIn" -> tween<Dp>(durationMillis = duration, easing = EaseIn)
        "Ease" -> tween<Dp>(durationMillis = duration, easing = Ease)
        "EaseOut" -> tween<Dp>(durationMillis = duration, easing = EaseOut)
        else -> tween<Dp>(durationMillis = duration, easing = EaseInOut)
    }

    LaunchedEffect(
        key1 = Unit,
        block = {
            while (true) {
                startAnimation = true
                delay(duration.toLong())
                delay(1000)
                startAnimation = false
                delay(duration.toLong())
                delay(1000)
            }
        },
    )

    val offsetX by animateDpAsState(
        targetValue = if (startAnimation) maxWidth else 0.dp,
        animationSpec = easing,
        label = "offsetX",
    )

    // Ball UI with label for the easing type
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = easingType, style = MaterialTheme.typography.bodyLarge)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offsetX)
                    .size(50.dp)
                    .background(Color.Blue, shape = CircleShape)
            )
        }
    }
}