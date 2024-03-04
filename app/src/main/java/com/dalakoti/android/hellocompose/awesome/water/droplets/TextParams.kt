package com.dalakoti.android.hellocompose.awesome.water.droplets

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle

@Stable
data class TextParams @OptIn(ExperimentalTextApi::class) constructor(
    val textStyle: TextStyle,
    val unitTextStyle: TextStyle,
    val textOffset: Offset,
    val unitTextOffset: Offset,
    val text: String,
    val textMeasurer: TextMeasurer,
)
