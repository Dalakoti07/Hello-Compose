package com.dalakoti07.android.awsm_animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


@Composable
fun Int.toDp(): Dp {
    val density = LocalDensity.current
    return remember(this) {
        with(density) { this@toDp.toDp() }
    }
}
