package com.dalakoti07.android.awsm_animation.awsm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalakoti07.android.awsm_animation.R
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * https://developer.android.com/develop/ui/compose/graphics/images/customize#color-matrix
 *
 * Using mutableStateListOf instead of mutableStateOf
 */
@Preview
@Composable
fun HueRotationExample() {
    val contrast = 1f // 0f..10f (1 should be default)
    val brightness = 0f // -255f..255f (0 should be default)
    val colorMatrix = remember {
        mutableStateListOf(
            contrast, 0f, 0f, 0f, brightness,
            0f, contrast, 0f, 0f, brightness,
            0f, 0f, contrast, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
        )
    }
    LaunchedEffect(
        key1 = Unit,
        block = {
            while (true) {
                delay(1000)
                // Randomly modify the values for RGB channels to create an effect
                colorMatrix[1] = Random.nextFloat() * 2f - 1f  // Adjust Red channel effect
                colorMatrix[6] = Random.nextFloat() * 2f - 1f  // Adjust Green channel effect
                colorMatrix[11] = Random.nextFloat() * 2f - 1f // Adjust Blue channel effect
            }
        },
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black,
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.firefox_bg_remove),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix(
                    colorMatrix.toFloatArray()
                )
            ),
        )
    }
}
