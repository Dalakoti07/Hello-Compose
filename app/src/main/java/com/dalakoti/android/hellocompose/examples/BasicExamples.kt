package com.dalakoti.android.hellocompose.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dalakoti.android.hellocompose.basics.*

private val allExamples = mutableListOf<@Composable () -> Unit>().apply {
    add {
        BouncingBall()
    }
    add {
        SimpleSinWaveUsingDots()
    }
    add {
        SimpleSinWave()
    }
    add{
        BasicLineDraws()
    }
}


@Composable
fun BasicExamples() {
    var currentExample by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        allExamples[currentExample].invoke()

        IconButton(
            {
                if (currentExample > 0) {
                    currentExample = currentExample.dec()
                }
            }, modifier = Modifier
                .align(Alignment.CenterStart)
                .background(Color.White.copy(alpha = 0.4f))
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        IconButton(
            {
                if (currentExample < allExamples.size.minus(1)) {
                    currentExample = currentExample.inc()
                }
            }, modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(Color.White.copy(alpha = 0.4f))
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

