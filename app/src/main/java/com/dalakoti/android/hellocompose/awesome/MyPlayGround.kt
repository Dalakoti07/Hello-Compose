package com.dalakoti.android.hellocompose.awesome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun MyPlayGround(){
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ){
        Box(
            Modifier
                .align(Alignment.Center)
                .size(
                    width = 10.dp,
                    height = 50.dp,
                )
                .offset(0.dp, (-110).dp )
                .rotate(30f)
                .background(Color.Red)
        ) {

        }
        Box(
            Modifier
                .align(Alignment.Center)
                .size(
                    width = 10.dp,
                    height = 50.dp,
                )
                .offset(0.dp, (-110).dp )
                .rotate(120f)
                .background(Color.Red)
        ) {

        }
    }
}