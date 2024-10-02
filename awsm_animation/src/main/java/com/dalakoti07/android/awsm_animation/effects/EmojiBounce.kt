package com.dalakoti07.android.awsm_animation.effects

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * When clicked on emoji it will go up, and then halt there and
 * then come down
 *
 * So to give it a real effect, while going up it should have `EaseOut` effect
 * which means Curve that starts quickly and ends slowly
 *
 * and while coming down it should be EaseIn, which means
 * that starts slowly and ends quickly.
 */
@Preview
@Composable
fun EmojiBounce() {
    var isGoingUpAnimation by remember {
        mutableStateOf(false)
    }
    val yOffset by animateDpAsState(
        targetValue = if (isGoingUpAnimation) (-200).dp else 0.dp,
        label = "yOffset",
        animationSpec = tween(durationMillis = 2000, easing = if (isGoingUpAnimation) EaseOut else EaseIn),
        finishedListener = {
            if(isGoingUpAnimation){
                isGoingUpAnimation = !isGoingUpAnimation
            }
        },
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "\uD83D\uDE0D",
            style = TextStyle(
                fontSize = 60.sp,
            ),
            modifier = Modifier.offset(
                y = yOffset,
            ),
        )
        Button(onClick = {
            isGoingUpAnimation = true
        }) {
            Text(text = "Click me")
        }
    }
}
