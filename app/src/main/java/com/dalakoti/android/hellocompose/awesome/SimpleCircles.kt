package com.dalakoti.android.hellocompose.awesome

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SimpleCircles"

@Preview
@Composable
fun SimpleCircles() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
            )
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        allAnimationsList.forEach {example->
            example()
        }
    }

}

private val allAnimationsList=  mutableListOf<@Composable () -> Unit>().apply {
    add {
        HorizontalLoadingPb(3)
    }
}

@Composable
fun HorizontalLoadingPb(counter: Int){
    var current by remember {
        mutableStateOf(1)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        scope.launch {
            while (true){
                delay(500)
                current = if(current == counter) 1 else current+1
            }
        }
    })
    Row {
        repeat(counter){
            EachCircle(it+1,current,)
        }
    }
}

@Composable
fun EachCircle(current: Int, counter: Int) {
    Log.d(TAG, "EachCircle .... $current and $counter")
    val scale by animateFloatAsState(
        targetValue = (if(current == counter) 1.0 else 0.5).toFloat(),
        label = "pbScale",
    )
    Box(
        modifier = Modifier
            .size(50.dp)
            .scale(scale)
            .background(
                color = Color.Red,
                shape = CircleShape
            )
    )
}

