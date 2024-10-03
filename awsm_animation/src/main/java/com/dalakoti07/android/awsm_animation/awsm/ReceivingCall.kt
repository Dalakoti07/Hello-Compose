package com.dalakoti07.android.awsm_animation.awsm

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dalakoti07.android.awsm_animation.R

@Preview
@Composable
fun ReceivingCall() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black,
            )
    ) {
        CallPicker(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    bottom = 20.dp,
                    end = 20.dp,
                )
                .align(
                    Alignment.BottomCenter,
                ),
        )
    }
}

@Composable
fun CallPicker(
    modifier: Modifier = Modifier,
) {
    var isAnimating by remember {
        mutableStateOf(false)
    }
    val iconOffset by animateDpAsState(
        targetValue = if (isAnimating) (-40).dp else 0.dp,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing,
        ),
        label = "iconOffset",
    )
    val iconScaling by animateFloatAsState(
        targetValue = if (isAnimating) 0f else 1f,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing,
        ),
        label = "iconSize",
    )

    LaunchedEffect(key1 = Unit) {
        isAnimating = true
    }
    val characters = remember {
        val allChars = "Slide to cancel".split("")
        mutableStateListOf<String>().apply {
            addAll(allChars)
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF3a3a3c),
                shape = RoundedCornerShape(20.dp),
            )
            .padding(
                vertical = 15.dp,
                horizontal = 20.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_mic),
            contentDescription = "",
            tint = Color(0xFF90443a),
            modifier = Modifier
                .size(36.dp)
        )
        Spacer(
            modifier = Modifier.weight(1f),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            characters.forEachIndexed { idx, it ->
                val letterSizing by animateFloatAsState(
                    targetValue = if (isAnimating) 0f else 1f,
                    animationSpec = tween(
                        durationMillis = 50,
                        easing = LinearEasing,
                        delayMillis = 50 * (characters.size - (idx + 1)),
                    ),
                    label = "iconSize",
                    finishedListener = {
                        if(idx == 0){
                            isAnimating = !isAnimating
                        }
                    },
                )
                val letterColoring by animateColorAsState(
                    targetValue = if(isAnimating) Color(0xFF90443a) else Color(0xFF266fc4),
                    animationSpec = tween(
                        durationMillis = 30,
                        easing = LinearEasing,
                        delayMillis = 30 * (characters.size - (idx + 1)),
                    ),
                    label = "letterColoring",
                )
                Text(
                    it,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = letterColoring,
                    ),
                    modifier = Modifier.graphicsLayer {
                        scaleX = letterSizing
                        scaleY = letterSizing
                    },
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .padding(
                    end = 20.dp,
                    start = 10.dp,
                )
                .offset(
                    x = if(isAnimating) iconOffset else 0.dp,
                )
                .size(
                    24.dp,
                ).graphicsLayer {
                    if(isAnimating){
                        scaleX = iconScaling
                        scaleY = iconScaling
                    }
                },
        )
        Icon(
            painter = painterResource(R.drawable.ic_mic),
            contentDescription = "",
            tint = Color(0xFF266fc4),
            modifier = Modifier
                .size(36.dp)
        )
    }
}
