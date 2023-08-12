package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TwitterSplashAnimation() {
    val width = with(LocalDensity.current) {
        LocalWindow.current.screenWidthDp.toPx()
    }
    val height = with(LocalDensity.current) {
        LocalWindow.current.screenHeightDp.toPx()
    }

    val scaleFactor = remember {
        Animatable(1.5f)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block = {
        runAnimation(coroutineScope, scaleFactor)
    })

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xff03A9F4))
    ) {

        Image(
            painter = painterResource(id = R.drawable.twitter_icon),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .offset {
                    IntOffset(
                        width
                            .div(2)
                            .toInt()
                            .minus(100),
                        height
                            .div(2)
                            .toInt()
                    )
                }
                .scale(scaleFactor.value)
        )

    }


}

private fun runAnimation(
    coroutineScope: CoroutineScope,
    scaleFactor: Animatable<Float, AnimationVector1D>
) {
    coroutineScope.launch {
        repeat(2) {
            scaleFactor.animateTo(
                1f,
                tween(easing = FastOutSlowInEasing, durationMillis = 1000.div(it.plus(1)))
            )
            scaleFactor.animateTo(
                1.5f,
                tween(easing = FastOutSlowInEasing, durationMillis = 1500.div(it.plus(1)))
            )
        }
        scaleFactor.animateTo(160f, tween(easing = FastOutSlowInEasing))
        delay(1000)
        scaleFactor.animateTo(1f)
        // todo uncomment for recursive animation
        // runAnimation(coroutineScope, scaleFactor)
    }
}