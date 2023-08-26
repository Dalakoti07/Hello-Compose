package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatMessageReaction(modifier: Modifier) {
    var isShowReaction by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier
            .background(Color.Black)
    ) {
        val scaleEffect by animateFloatAsState(
            targetValue = if (isShowReaction) 1f else 0f,
            animationSpec = spring(0.35f, stiffness = 270f), label = "",
        )

        LaunchedEffect(true) {
            isShowReaction = !isShowReaction
        }

        Column(
            Modifier.background(
                color = Color.Black,
            ),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                isShowReaction = !isShowReaction
            }) {
                Text(text = "Repeat Animation!")
            }
            Spacer(modifier = Modifier.height(28.dp))
            Column(
                Modifier
                    .padding(4.dp)
                    .graphicsLayer(
                        scaleX = scaleEffect,
                        scaleY = scaleEffect,
                        transformOrigin = topTrailing()
                    )
                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(28.dp))
            ) {
                Row(
                    Modifier.padding(8.dp)
                ) {
                    Text(
                        "❤️",
                        Modifier
                            .graphicsLayer(
                                scaleX = scaleEffect, scaleY = scaleEffect,
                            ),
                        fontSize = 20.sp,
                    )
                    Text(
                        "\uD83D\uDC4D",
                        Modifier
                            .graphicsLayer(
                                scaleX = scaleEffect,
                                scaleY = scaleEffect,
                            ),
                        fontSize = 20.sp,
                    )
                    Text(
                        "\uD83D\uDC4E",
                        Modifier
                            .graphicsLayer(
                                scaleX = scaleEffect,
                                scaleY = scaleEffect,
                            ),
                        fontSize = 20.sp,
                    )
                    Text(
                        "\uD83D\uDE2D",
                        Modifier
                            .graphicsLayer(
                                scaleX = scaleEffect,
                                scaleY = scaleEffect,
                            ),
                        fontSize = 20.sp,
                    )
                    Text(
                        "\uD83D\uDE02",
                        Modifier
                            .graphicsLayer(
                                scaleX = scaleEffect,
                                scaleY = scaleEffect,
                            ),
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun topTrailing() = TransformOrigin(1f, 0f)
