package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
    add {
        RotatingCirclesPb(3)
    }
    add{
        NBallsBouncing(4)
    }
}

@Composable
fun NBallsBouncing(count: Int) {
    var current by remember {
        mutableStateOf(1)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        scope.launch {
            while (true){
                delay(400)
                current = if(current == count) 1 else current+1
            }
        }
    })
    Row(
        modifier = Modifier.padding(
            top = 100.dp,
        )
    ) {
        repeat(count){
            EachCircleBouncing(it+1,current,)
        }
    }
}

@Composable
fun EachCircleBouncing(current: Int, selected: Int, size: Dp = 10.dp){
    val offsetY by animateFloatAsState(
        targetValue = (if(current == selected) (-10/2) else (
            0
        )).toFloat(),
        label = "pbScale",
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing,
        )
    )
    Box(
        modifier = Modifier
            .size(size)
            .offset(y = offsetY.dp)
            .background(
                color = Color.Red,
                shape = CircleShape
            )
    )
}

@Composable
fun RotatingCirclesPb(count: Int) {
    val infiniteAnimation = rememberInfiniteTransition(label = "rotationRow")
    val animatingDegree by infiniteAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 0, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ), label = "",
    )
    Row(
        modifier = Modifier
            .padding(
                top = 100.dp,
            )
            .rotate(
                animatingDegree
            )
    ) {
        repeat(count){
            Box(modifier = Modifier
                .size(50.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape,
                ))
        }
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

