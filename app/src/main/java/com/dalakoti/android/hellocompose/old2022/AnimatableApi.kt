package com.dalakoti.android.hellocompose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun AnimatableExample(){
    val offsetX = remember { Animatable(500f) }
    val offsetY = remember { Animatable(500f) }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(
                R.drawable.ic_launcher_background
            ),
            contentDescription = "s",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.toInt(),
                        offsetY.value.toInt()
                    )
                }
                .width(30.dp)
                .height(30.dp)
        )
        Button(onClick = {

        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Move")
        }
    }
    //Finally run the animation on the Click of your button or whenever you wants to start it...
    LaunchedEffect(key1 = true){
        offsetX.animateTo(
            targetValue = 100f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 0
            )
        )
    }
    LaunchedEffect(key1 = true, block = {
        offsetY.animateTo(
            targetValue = 100f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 0
            )
        )
    })
}