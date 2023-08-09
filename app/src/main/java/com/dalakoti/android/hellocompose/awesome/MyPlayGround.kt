package com.dalakoti.android.hellocompose.awesome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .size(
                    width = 20.dp,
                    height = 50.dp,
                )
                .graphicsLayer {
                    this.rotationY = 45f
                }
                .background(Color.Red)
        ) {

        }
    }
}