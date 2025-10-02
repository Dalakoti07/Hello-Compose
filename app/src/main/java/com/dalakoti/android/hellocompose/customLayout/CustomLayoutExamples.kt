package com.dalakoti.android.hellocompose.customLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
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


private val allAnimations = mutableListOf<@Composable () -> Unit>().apply {
    add {
        ThreeDCubesExample()
    }
    add {
        WeightedCustomColumnExample()
    }
    add {
        CustomColumnExample()
    }
}

@Composable
fun CustomLayoutExamples() {
    var currentAnimation by remember {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        allAnimations[currentAnimation].invoke()
        IconButton(
            onClick =
                {
                    if (currentAnimation > 0) {
                        currentAnimation = currentAnimation.dec()
                    }
                }, modifier = Modifier
                .align(Alignment.CenterStart)
                .background(Color.White.copy(alpha = 0.4f), shape = CircleShape)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        IconButton(
            onClick = {
                if (currentAnimation < allAnimations.size.minus(1)) {
                    currentAnimation = currentAnimation.inc()
                }
            }, modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(Color.White.copy(alpha = 0.4f), shape = CircleShape)
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

