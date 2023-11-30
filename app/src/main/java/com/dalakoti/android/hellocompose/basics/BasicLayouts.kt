package com.dalakoti.android.hellocompose.basics

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.R

@Preview
@Composable
fun BasicLayouts() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .border(
                1.dp, Color.Black,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.nature),
            contentDescription = "airplane",
            modifier = Modifier
                .clip(CircleShape)
                .padding(10.dp)
                .size(60.dp)
                .align(
                    Alignment.Center
                )
        )
    }

}
