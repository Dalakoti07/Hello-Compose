package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun MenuToClose(modifier: Modifier) {
    Surface(modifier.background(MaterialTheme.colors.background)) {
        var isRotated by remember {
            mutableStateOf(false)
        }
        var isHidden by remember {
            mutableStateOf(false)
        }

        val rotateFirst by animateFloatAsState(
            targetValue = if (isRotated) 48f else 0f,
            animationSpec = springSpec(), label = "",
        )
        val rotateSecond by animateFloatAsState(
            targetValue = if (isRotated) -48f else 0f,
            animationSpec = springSpec(), label = "",
        )
        val hiddenFirst by animateFloatAsState(
            targetValue = if (isHidden) 0f else 1f,
            animationSpec = springSpec(), label = "",
        )

        Column(
            Modifier
                .padding(32.dp)
                .height(if (isHidden) 40.dp else 50.dp)
                .clickable {
                    isRotated = !isRotated
                    isHidden = !isHidden
                },
            verticalArrangement = if (isHidden) Arrangement.SpaceAround else Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .graphicsLayer(
                        shape = RoundedCornerShape(8.dp),
                        rotationZ = rotateFirst,
                        transformOrigin = TransformOrigin(0.3f, 0.3f)
                    )
                    .width(64.dp)
                    .background(MaterialTheme.colors.onBackground)
                    .height(10.dp)

            )

            if (!isHidden) {
                Box(
                    Modifier
                        .graphicsLayer(
                            shape = RoundedCornerShape(8.dp),
                            scaleX = hiddenFirst, scaleY = hiddenFirst, alpha = hiddenFirst
                        )
                        .width(64.dp)
                        .height(10.dp)
                        .background(MaterialTheme.colors.onBackground)

                )
            }

            Box(
                Modifier
                    .graphicsLayer(
                        shape = RoundedCornerShape(8.dp),
                        rotationZ = rotateSecond,
                        transformOrigin = TransformOrigin(0.3f, 0.3f)
                    )
                    .width(64.dp)
                    .height(10.dp)
                    .background(MaterialTheme.colors.onBackground)

            )
        }
    }
}

@Composable
private fun springSpec(): SpringSpec<Float> =
    spring(0.35f, stiffness = 300f)
