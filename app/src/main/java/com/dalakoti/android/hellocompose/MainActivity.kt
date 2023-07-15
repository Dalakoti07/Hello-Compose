package com.dalakoti.android.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationExamples()
        }
    }
}

@Preview
@Composable
fun AnimationExamples(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var isVisible by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            isVisible = !isVisible
        }) {
            Text(text = "Toogle")
        }
        AnimatedVisibility(visible = isVisible) {
            Box(modifier = Modifier
                .padding(top = 20.dp)
                .width(100.dp)
                .height(100.dp)
                .background(Color.Red))
        }
    }
}
