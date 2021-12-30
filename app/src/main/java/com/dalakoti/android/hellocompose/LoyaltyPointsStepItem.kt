package com.dalakoti.android.hellocompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun LoyaltyPointsStepItem(
    title: String = "Refer yours friends",
    subTitle: String = "For each successful refer, you will get 200 points"
) {
    var boxWidthPx by remember {
        mutableStateOf(0)
    }
    var boxHeightPx by remember {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(
                    with(LocalDensity.current) { boxWidthPx.toDp() }
                )
                .height(
                    with(LocalDensity.current) { boxHeightPx.toDp() }
                )
                .padding(top = 2.dp, start = 2.dp, end = 2.dp, bottom = 2.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White.copy(alpha = 0.3f),
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 50,
                        bottomStartPercent = 0,
                        bottomEndPercent = 50
                    )
                )
        )
        Card(
            modifier = Modifier
                .onSizeChanged {
                    boxHeightPx = it.height
                    boxWidthPx = it.width
                },
            shape = RoundedCornerShape(
                topStartPercent = 0,
                topEndPercent = 50,
                bottomStartPercent = 0,
                bottomEndPercent = 50
            ),
            elevation = 2.dp,
            backgroundColor = Color.Transparent,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "SD", fontSize = 30.sp, color = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = subTitle,
                        color = Color.White
                    )
                }
            }
        }
    }

}
