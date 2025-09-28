// This file demonstrates a custom column layout that supports per-child
// alignment and weight distribution using Compose's parent data pattern.
package com.dalakoti.android.hellocompose.customLayout

// Visual helpers for the demo content
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
// Core Compose primitives
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// Low-level layout APIs
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
// Units and density conversions
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Parent data carried by each child to influence how the parent measures/places it.
// - weight: child should take a proportional share of remaining height
// - align: child’s horizontal alignment inside the column’s width
private data class CustomColumnParentData(
    val weight: Float? = null,
    val align: Alignment.Horizontal? = null
)

// Exposed modifier to assign weight to a child inside WeightedCustomColumn
fun Modifier.customColumnWeight(weight: Float) = this.then(
    object : ParentDataModifier {
        // Called by the parent during measurement to read/merge parent data
        override fun Density.modifyParentData(parentData: Any?): Any {
            return when (parentData) {
                is CustomColumnParentData -> parentData.copy(weight = weight)
                else -> CustomColumnParentData(weight = weight)
            }
        }
    }
)

// Exposed modifier to set per-child horizontal alignment
fun Modifier.customColumnAlign(align: Alignment.Horizontal) = this.then(
    object : ParentDataModifier {
        // Merge alignment into existing parent data if present
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
    // The container we will lay out. Height is fixed so weighted children can expand.
    WeightedCustomColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF0F0F0))
            .height(240.dp),
        spacing = 8.dp
    ) {
        // A fixed-height child centered horizontally
        Text(
            text = "Header (fixed)",
            modifier = Modifier
                .background(Color(0xFFBBDEFB))
                .padding(8.dp)
                .customColumnAlign(Alignment.CenterHorizontally)
        )

        // Weighted child that takes 1 share of the remaining height
        Text(
            text = "Weighted 1x",
            modifier = Modifier
                .customColumnWeight(1f)
                .background(Color(0xFFC8E6C9))
                .padding(8.dp)
        )

        // Weighted child that takes 2 shares, aligned to the end
        Text(
            text = "Weighted 2x, end-aligned",
            modifier = Modifier
                .customColumnWeight(2f)
                .customColumnAlign(Alignment.End)
                .background(Color(0xFFFFF9C4))
                .padding(8.dp)
        )

        // Another fixed-height child at the bottom
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
    // Standard modifier chain for the whole layout
    modifier: Modifier = Modifier,
    // Vertical space between children
    spacing: Dp = 8.dp,
    // Slot for children; each child can provide parent data via modifiers
    content: @Composable () -> Unit
) {
    // Core low-level layout entry point. We get 'measurables' (children to measure)
    // and 'constraints' (min/max width/height available from parent).
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        // Convert spacing to pixels using current density
        val spacingPx = spacing.roundToPx()

        // Partition children into weighted and fixed based on their parent data
        val weighted = mutableListOf<Pair<Measurable, Float>>()
        val fixed = mutableListOf<Measurable>()

        // Read each child's parent data and classify it
        measurables.forEach { measurable ->
            val pd = measurable.parentData as? CustomColumnParentData
            val w = pd?.weight
            if (w != null && w > 0f) weighted += measurable to w else fixed += measurable
        }

        // Measure fixed children first with flexible height (minHeight = 0)
        val fixedPlaceables = fixed.map { it.measure(constraints.copy(minHeight = 0)) }
        // Total height consumed by fixed children plus spacing between them
        val usedHeight = fixedPlaceables.sumOf { it.height } +
                spacingPx * (fixedPlaceables.size - 1).coerceAtLeast(0)

        // Remaining height we can distribute to weighted children
        val remainingHeight = (constraints.maxHeight - usedHeight).coerceAtLeast(0)
        // Sum of all child weights (guarded to be >= 0)
        val totalWeight = weighted.sumOf { it.second.toDouble() }.toFloat().coerceAtLeast(0f)

        // Measure weighted children, capping each by its proportional share
        val weightedPlaceables = weighted.map { (m, w) ->
            val h: Float = if (totalWeight == 0f) 0f else (remainingHeight * (w / totalWeight))
            m.measure(
                constraints.copy(
                    minHeight = 0,
                    maxHeight = h.coerceAtLeast(0f).toInt()
                )
            )
        }

        // Combine all placeables in the same order we'll place them (fixed first, then weighted)
        val all = fixedPlaceables + weightedPlaceables
        // Column width is the widest child, constrained by incoming min/max
        val width = all.maxOfOrNull { it.width }?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        // Column height is fixed children + weighted children + spacing between weighted ones
        val height = (usedHeight + weightedPlaceables.sumOf { it.height } +
                spacingPx * (weightedPlaceables.size - 1).coerceAtLeast(0))
            .coerceIn(constraints.minHeight, constraints.maxHeight)

        // Placement phase: walk through children top-to-bottom
        layout(width, height) {
            // Running y-offset where we place the next child
            var y = 0
            // Iterate measurables in the same order as 'all' so indices align
            (fixed + weighted.map { it.first }).forEachIndexed { index, measurable ->
                val placeable = all[index]
                // Read per-child horizontal alignment from parent data
                val pd = measurable.parentData as? CustomColumnParentData
                val x = when (pd?.align) {
                    Alignment.CenterHorizontally -> (width - placeable.width) / 2
                    Alignment.End -> width - placeable.width
                    else -> 0
                }
                // Place the child and advance y by its height and spacing (except after last)
                placeable.placeRelative(x, y)
                y += placeable.height
                if (index != all.lastIndex) y += spacingPx
            }
        }
    }
}