package com.dalakoti.android.hellocompose.ratingBar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween

sealed class RateChangeStrategy {
    object InstantChange : RateChangeStrategy()
    data class AnimatedChange(
        val animationSpec: AnimationSpec<Float> = tween(300, easing = LinearEasing)
    ) : RateChangeStrategy()
}
