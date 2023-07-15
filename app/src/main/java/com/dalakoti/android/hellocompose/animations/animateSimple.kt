package com.dalakoti.android.hellocompose.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun AnimationExamples(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var isRound by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            isRound = !isRound
        }) {
            Text(text = "Toogle")
        }
        val transition = updateTransition(targetState = isRound, label = null)
        val borderRadius by transition.animateInt(
            transitionSpec = { tween(2000) },
            label = "border radius",
            targetValueByState = { isRounded->
                if(isRounded) 100 else 0
            }
        )
        val boxColor by transition.animateColor(
            transitionSpec = { tween(2000) },
            label = "color",
            targetValueByState = {isRounded->
                if(isRounded) Color.Red else Color.Green
            }
        )
        Box(modifier = Modifier
            .padding(top = 20.dp)
            .width(100.dp)
            .height(100.dp)
            .background(
                color = boxColor,
                RoundedCornerShape(borderRadius)
            )
        )
    }
}
