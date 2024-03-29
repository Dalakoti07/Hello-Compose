package com.dalakoti.android.hellocompose.awesome.water.droplets

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import com.dalakoti.android.hellocompose.awesome.water.Paths
import com.dalakoti.android.hellocompose.awesome.water.Water

fun DrawScope.drawWaves(
    paths: Paths,
) {
    drawIntoCanvas {
        it.drawPath(paths.pathList[1], paint.apply {
            color = Blue
        })
        it.drawPath(paths.pathList[0], paint.apply {
            color = androidx.compose.ui.graphics.Color.Black
            alpha = 0.9f
        })
    }
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawTextWithBlendMode(
    mask: Path,
    textParams: TextParams,
) {
    drawText(
        textMeasurer = textParams.textMeasurer,
        topLeft = textParams.textOffset,
        text = textParams.text,
        style = textParams.textStyle,
    )
    drawText(
        textMeasurer = textParams.textMeasurer,
        topLeft = textParams.unitTextOffset,
        text = "FT",
        style = textParams.unitTextStyle,
    )

    drawPath(
        path = mask,
        color = Water,
        blendMode = BlendMode.SrcIn
    )
}

val paint = Paint().apply {
    this.color = androidx.compose.ui.graphics.Color.Blue
    pathEffect = PathEffect.cornerPathEffect(100f)
}
