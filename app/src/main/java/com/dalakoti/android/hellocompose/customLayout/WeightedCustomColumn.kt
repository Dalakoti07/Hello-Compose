package com.dalakoti.android.hellocompose.customLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private data class CustomColumnParentData(
    val weight: Float? = null,
    val align: Alignment.Horizontal? = null
)

fun Modifier.customColumnWeight(weight: Float) = this.then(
    object : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any {
            return when (parentData) {
                is CustomColumnParentData -> parentData.copy(weight = weight)
                else -> CustomColumnParentData(weight = weight)
            }
        }
    }
)

fun Modifier.customColumnAlign(align: Alignment.Horizontal) = this.then(
    object : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any {
            return when (parentData) {
                is CustomColumnParentData -> parentData.copy(align = align)
                else -> CustomColumnParentData(align = align)
            }
        }
    }
)

@Composable
fun WeightedCustomColumnExample(){
    WeightedCustomColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F0F0))
            .height(240.dp),
        spacing = 8.dp
    ) {
        Text(
            text = "Header (fixed)",
            modifier = Modifier
                .background(Color(0xFFBBDEFB))
                .padding(8.dp)
                .customColumnAlign(Alignment.CenterHorizontally)
        )

        Text(
            text = "Weighted 1x",
            modifier = Modifier
                .customColumnWeight(1f)
                .background(Color(0xFFC8E6C9))
                .padding(8.dp)
        )

        Text(
            text = "Weighted 2x, end-aligned",
            modifier = Modifier
                .customColumnWeight(2f)
                .customColumnAlign(Alignment.End)
                .background(Color(0xFFFFF9C4))
                .padding(8.dp)
        )

        Text(
            text = "Footer (fixed)",
            modifier = Modifier
                .background(Color(0xFFFFCCBC))
                .padding(8.dp)
        )
    }
}

@Composable
fun WeightedCustomColumn(
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val spacingPx = spacing.roundToPx()

        val weighted = mutableListOf<Pair<Measurable, Float>>()
        val fixed = mutableListOf<Measurable>()

        measurables.forEach { measurable ->
            val pd = measurable.parentData as? CustomColumnParentData
            val w = pd?.weight
            if (w != null && w > 0f) weighted += measurable to w else fixed += measurable
        }

        // Measure fixed children first
        val fixedPlaceables = fixed.map { it.measure(constraints.copy(minHeight = 0)) }
        val usedHeight = fixedPlaceables.sumOf { it.height } +
                spacingPx * (fixedPlaceables.size - 1).coerceAtLeast(0)

        val remainingHeight = (constraints.maxHeight - usedHeight).coerceAtLeast(0)
        val totalWeight = weighted.sumOf { it.second.toDouble() }.toFloat().coerceAtLeast(0f)

        val weightedPlaceables = weighted.map { (m, w) ->
            val h: Float = if (totalWeight == 0f) 0f else (remainingHeight * (w / totalWeight))
            m.measure(
                constraints.copy(
                    minHeight = 0,
                    maxHeight = h.coerceAtLeast(0f).toInt()
                )
            )
        }

        val all = fixedPlaceables + weightedPlaceables
        val width = all.maxOfOrNull { it.width }?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        val height = (usedHeight + weightedPlaceables.sumOf { it.height } +
                spacingPx * (weightedPlaceables.size - 1).coerceAtLeast(0))
            .coerceIn(constraints.minHeight, constraints.maxHeight)

        layout(width, height) {
            var y = 0
            (fixed + weighted.map { it.first }).forEachIndexed { index, measurable ->
                val placeable = all[index]
                val pd = measurable.parentData as? CustomColumnParentData
                val x = when (pd?.align) {
                    Alignment.CenterHorizontally -> (width - placeable.width) / 2
                    Alignment.End -> width - placeable.width
                    else -> 0
                }
                placeable.placeRelative(x, y)
                y += placeable.height
                if (index != all.lastIndex) y += spacingPx
            }
        }
    }
}