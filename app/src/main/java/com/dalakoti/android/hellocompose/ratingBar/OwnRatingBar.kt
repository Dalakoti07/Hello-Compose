package com.dalakoti.android.hellocompose.ratingBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Rating bar that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 *
 * @param rating value to be set on this rating bar
 * @param imageVectorEmpty background for rating items. Item with borders to
 * show empty values
 * @param imageVectorFilled foreground for rating items. Filled item to show percentage of rating
 * @param tintEmpty color for background and foreground items
 * @param itemSize size of the rating item to be displayed. This is intrinsic size of image
 * or vector file by default
 * @param rateChangeStrategy whether rating change should happen instantly or with an animation
 * @param gestureStrategy drag and touch, touch only or no gesture is used to change rating
 * @param shimmerEffect shimmer effect for having a glow
 * @param itemCount maximum number of items
 * @param space space between rating items in dp
 * [RatingInterval.Half] returns multiples of 0.5, and [RatingInterval.Unconstrained] returns
 * current value without any limitation up to [itemSize]
 * @param allowZeroRating when true [RatingInterval.Full] or [RatingInterval.Half] allows
 * user to set value zero
 * @param onRatingChange callback to notify user when rating has changed. This is helpful
 * for getting change after tap or drag gesture
 *
 */
@Composable
fun OwnRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageVectorEmpty: ImageVector,
    imageVectorFilled: ImageVector,
    tintEmpty: Color? = null,
    tintFilled: Color? = null,
    itemSize: Dp = Dp.Unspecified,
    rateChangeStrategy: RateChangeStrategy = RateChangeStrategy.AnimatedChange(),
    gestureStrategy: GestureStrategy = GestureStrategy.DragAndPress,
    itemCount: Int = 5,
    space: Dp = 0.dp,
    ratingInterval: RatingInterval = RatingInterval.Unconstrained,
    allowZeroRating: Boolean = true,
    onRatingChangeFinished: ((Float) -> Unit)? = null,
    onRatingChange: (Float) -> Unit
) {

    val painterBackground = rememberVectorPainter(image = imageVectorEmpty)
    val painterForeground = rememberVectorPainter(image = imageVectorFilled)

    val painterWidth = painterBackground.intrinsicSize.width
    val painterHeight = painterBackground.intrinsicSize.height

    val colorFilterEmpty: ColorFilter? = remember(tintEmpty) {
        if (tintEmpty != null) {
            ColorFilter.tint(tintEmpty)
        } else null
    }

    val colorFilterFilled: ColorFilter? = remember(tintFilled) {
        if (tintFilled != null) {
            ColorFilter.tint(tintFilled)
        } else null
    }

    // This is for setting initial value to zero if it's set initially and
    // setting value to zero is not allowed with gestures
    var checkInitialZeroWhenNotAllowed by remember {
        mutableStateOf(allowZeroRating.not() && rating == 0f)
    }

    val ratingFraction = if (checkInitialZeroWhenNotAllowed && rating == 0f) 0f else
        rating.getRatingForInterval(
            ratingInterval = ratingInterval,
            allowZero = allowZeroRating,
        )

    LaunchedEffect(key1 = checkInitialZeroWhenNotAllowed) {
        if (checkInitialZeroWhenNotAllowed) {
            checkInitialZeroWhenNotAllowed = false
        }
    }

    RatingBarImpl(
        modifier = modifier,
        rating = ratingFraction,
        intrinsicWidth = painterWidth,
        intrinsicHeight = painterHeight,
        itemSize = itemSize,
        rateChangeStrategy = rateChangeStrategy,
        gestureStrategy = gestureStrategy,
        itemCount = itemCount,
        space = space,
        block = { _: Float, spaceBetween: Float ->
            drawRatingPainters(
                rating,
                itemCount,
                painterBackground,
                painterForeground,
                colorFilterEmpty,
                colorFilterFilled,
                spaceBetween
            )
        },
        ratingInterval = ratingInterval,
        allowZeroRating = allowZeroRating,
        onRatingChangeFinished = onRatingChangeFinished,
        onRatingChange = onRatingChange
    )
}
