package com.dalakoti07.android.awsm_animation.paths

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Make a dash moving along Triangular path, square path, heart path
 */
@Preview
@Composable
fun MoveAlongPathExample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RevolvingShapesAroundSquare(
            modifier = Modifier.padding(
                top = 20.dp,
            ),
            title = "Square",
            shape = {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = Color.Red,
                        )
                )
            }
        )
        RevolvingShapesAroundTriangle(
            modifier = Modifier.padding(
                top = 20.dp,
            ),
            title = "Triangle",
        )
    }
}
