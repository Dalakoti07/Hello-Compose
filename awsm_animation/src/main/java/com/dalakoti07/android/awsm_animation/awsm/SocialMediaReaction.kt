package com.dalakoti07.android.awsm_animation.awsm

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateSize
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalakoti07.android.awsm_animation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class AnimationState {
    INITIAL, FINAL
}



@Preview
@Composable
fun LikeReaction() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LikeReactionTween(
            modifier = Modifier.padding(bottom = 20.dp)
        )
        LikeReactionSpring()
    }
}

@Composable
fun LikeReactionTween(
    modifier: Modifier = Modifier,
){
    val scope = rememberCoroutineScope()
    var currentState by remember {
        mutableStateOf(AnimationState.INITIAL)
    }
    val transition = updateTransition(
        targetState = currentState,
        label = "updateTransition",
    )
    val scaleSize by transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 1000,
            )
        }, label = "scaleSize"
    ) {
        when (it) {
            AnimationState.INITIAL -> 50.dp
            AnimationState.FINAL -> 60.dp
        }
    }
    val rotation by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 1000,
            )
        }, label = "rotation"
    ) {
        when (it) {
            AnimationState.INITIAL -> 0f
            AnimationState.FINAL -> -45f
        }
    }
    Icon(
        painter = painterResource(id = R.drawable.ic_like),
        contentDescription = null,
        modifier = modifier
            .rotate(rotation)
            .size(scaleSize)
            .clickable {
                scope.launch {
                    currentState = AnimationState.FINAL
                    delay(1100)
                    currentState = AnimationState.INITIAL
                }
            },
        tint = Color(0xFFf7b12d),
    )
}

@Composable
fun LikeReactionSpring(
    modifier: Modifier = Modifier,
){
    var currentState by remember {
        mutableStateOf(AnimationState.INITIAL)
    }
    val transition = updateTransition(
        targetState = currentState,
        label = "updateTransition",
    )
    val scaleSize by transition.animateDp(
        transitionSpec = {
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioHighBouncy,
            )
        }, label = "scaleSize"
    ) {
        when (it) {
            AnimationState.INITIAL -> 50.dp
            AnimationState.FINAL -> 60.dp
        }
    }
    val rotation by transition.animateFloat(
        transitionSpec = {
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioHighBouncy,
            )
        }, label = "rotation"
    ) {
        when (it) {
            AnimationState.INITIAL -> 0f
            AnimationState.FINAL -> -45f
        }
    }
    Icon(
        painter = painterResource(id = R.drawable.ic_like),
        contentDescription = null,
        modifier = modifier
            .rotate(rotation)
            .size(scaleSize)
            .clickable {
                if(currentState == AnimationState.INITIAL){
                    currentState = AnimationState.FINAL
                }else{
                    currentState = AnimationState.INITIAL
                }
            },
        tint = Color(0xFFf7b12d),
    )
}