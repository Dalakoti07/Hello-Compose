package com.dalakoti.android.hellocompose

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AnimateNumbers(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var targetState by remember {
            mutableStateOf(7)
        }
        CustomAnimatedNumber(
            modifier = Modifier.align(Alignment.Center),
            targetState = targetState
        )
        Button(
            modifier = Modifier.align(
                Alignment.BottomStart
            ),
            onClick = {
                targetState++
            }
        ) {
            Text(text = "Increase")
        }
        Button(
            modifier = Modifier.align(
                Alignment.BottomEnd
            ),
            onClick = {
                targetState--
            }
        ) {
            Text(text = "Decrease")
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomAnimatedNumber(
    modifier: Modifier = Modifier,
    targetState: Int
) {
    AnimatedContent(
        modifier = modifier,
        targetState = targetState,
        transitionSpec = {
            // Compare the incoming number with the previous number.
            if (targetState > initialState) {
                // If the target number is larger, it slides up and fades in
                // while the initial (smaller) number slides up and fades out.
                slideInVertically(
                    initialOffsetY = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ) + fadeIn() with
                        slideOutVertically(
                            targetOffsetY = {
                                -it
                            },animationSpec = tween(
                                durationMillis = 200,
                                easing = LinearEasing
                            )
                        ) + fadeOut()
            } else {
                // If the target number is smaller, it slides down and fades in
                // while the initial number slides down and fades out.
                slideInVertically(
                    initialOffsetY = {
                        -it
                    },animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                ) + fadeIn() with
                        slideOutVertically(
                            targetOffsetY = {
                                it
                            },animationSpec = tween(
                                durationMillis = 200,
                                easing = LinearEasing
                            )
                        ) + fadeOut()
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = false)
            )
        }
    ) { targetCount ->
        Text(text = "$targetCount")
    }
}