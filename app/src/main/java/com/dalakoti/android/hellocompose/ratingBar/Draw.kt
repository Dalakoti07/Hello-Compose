package com.dalakoti.android.hellocompose.ratingBar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize

internal fun DrawScope.drawRatingPainters(
    rating: Float,
    itemCount: Int,
    painterEmpty: Painter,
    painterFilled: Painter,
    colorFilterEmpty: ColorFilter?,
    colorFilterFilled: ColorFilter?,
    space: Float
) {

    if (itemCount == 0) {
        return
    }

    // Width of rating bar with items and spaces combined
    val ratingBarWidth = size.width

    // Width of single rating item
    val itemWidth = (size.width - space * (itemCount - 1)) / (itemCount)
    // Height of single rating item
    val itemHeight = size.height.coerceAtMost(itemWidth)

    val ratingInt = rating.toInt()

    // Height of the ratingbar
    val ratingBarHeight = size.height
    // Start of empty rating items
    val endOfFilledItems = rating * itemWidth + ratingInt * space
    // Rectangle width that covers empty items
    val rectWidth = ratingBarWidth - endOfFilledItems

    drawWithLayer {

        // Draw foreground rating items
        for (i in 0 until itemCount) {
            val start = itemWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                with(painterFilled) {
                    draw(
                        size = Size(itemWidth, itemHeight),
                        colorFilter = colorFilterFilled
                    )
                }
            }
        }

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(endOfFilledItems, 0f),
            size = Size(rectWidth, height = ratingBarHeight),
            blendMode = BlendMode.SrcIn
        )

        for (i in 0 until itemCount) {

            translate(left = (itemWidth * i + space * i), top = 0f) {
                with(painterEmpty) {
                    draw(
                        size = Size(itemWidth, itemHeight),
                        colorFilter = colorFilterEmpty
                    )
                }
            }
        }
    }
}

internal fun DrawScope.drawRatingImages(
    rating: Float,
    itemCount: Int,
    imageEmpty: ImageBitmap,
    imageFilled: ImageBitmap,
    colorFilterEmpty: ColorFilter?,
    colorFilterFilled: ColorFilter?,
    space: Float,
) {

    if (itemCount == 0) {
        return
    }

    // Width of rating bar with items and spaces combined
    val ratingBarWidth = size.width

    // Width of single rating item
    val itemWidth = (size.width - space * (itemCount - 1)) / (itemCount)
    // Height of single rating item
    val itemHeight = size.height.coerceAtMost(itemWidth)

    val ratingInt = rating.toInt()

    // Height of the ratingbar
    val ratingBarHeight = size.height
    // Start of empty rating items
    val endOfFilledItems = rating * itemWidth + ratingInt * space
    // Rectangle width that covers empty items
    val rectWidth = ratingBarWidth - endOfFilledItems

    drawWithLayer {

        // Draw foreground rating items
        for (i in 0 until itemCount) {
            val start = itemWidth * i + space * i

            // Destination
            translate(left = start, top = 0f) {
                drawImage(
                    image = imageFilled,
                    dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                    colorFilter = colorFilterFilled
                )
            }
        }

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(endOfFilledItems, 0f),
            size = Size(rectWidth, height = ratingBarHeight),
            blendMode = BlendMode.SrcIn
        )

        for (i in 0 until itemCount) {

            translate(left = (itemWidth * i + space * i), top = 0f) {
                drawImage(
                    image = imageEmpty,
                    dstSize = IntSize(itemWidth.toInt(), itemHeight.toInt()),
                    colorFilter = colorFilterEmpty
                )
            }
        }
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}
