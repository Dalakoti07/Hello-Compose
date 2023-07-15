package com.dalakoti.android.hellocompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ShadowContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Green
            )
    ) {
        Column(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .shadow(elevation = 20.dp)
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "Hello",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Surface(
            elevation = 10.dp,
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 40.dp)
                .align(Alignment.BottomCenter)
                .background(color = Color.Red)
        ) {
            Text(text = "Hi there")
        }
    }

}