package com.dalakoti.android.hellocompose.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BasicLayouts() {
    Box(modifier = Modifier.size(100.dp)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Color.Red,
                )
        )
    }

}
