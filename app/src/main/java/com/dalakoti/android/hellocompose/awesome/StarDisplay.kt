package com.dalakoti.android.hellocompose.awesome

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.R
import com.dalakoti.android.hellocompose.ratingBar.OwnRatingBar

@Preview
@Composable
fun StarDisplay() {
    Box(modifier = Modifier.border(width = 1.dp, color = Color.Black)){
        OwnRatingBar(
            rating = 2.5f,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(id = R.drawable.ic_star),
            imageVectorFilled = ImageVector.vectorResource(id = R.drawable.ic_star_filled),
            tintEmpty = Color(0xFFdadada),
            tintFilled = Color(0xFFffcb5a),
            itemSize = 70.dp,
        ) {}
    }
}

