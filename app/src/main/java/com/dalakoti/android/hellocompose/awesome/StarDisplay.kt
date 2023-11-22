package com.dalakoti.android.hellocompose.awesome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.RenderVectorGroup
import androidx.compose.ui.graphics.vector.VectorConfig
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.R

@Preview
@Composable
fun BoxScope.StarDisplay() {
//    Icon(
//        painter = painterResource(id = R.drawable.ic_star),
//        contentDescription = "",
//        modifier = Modifier
//            .size(100.dp)
//            .drawWithCache {
//                onDrawWithContent {
//                    drawContent()
//                    drawRect(
//                        Brush.horizontalGradient(
//                            listOf(
//                                Color.White,
//                                Color.Yellow,
//                            ),
//                        ),
//                        blendMode = BlendMode.SrcAtop,
//                    )
//                }
//            }
//            .align(
//                Alignment.Center,
//            ),
//    )
    PreviewGradientIcon()
}


class GradientConfig(private val brush: Brush) : VectorConfig {

    override fun <T> getOrDefault(property: VectorProperty<T>, defaultValue: T): T {
        return when (property) {
            is VectorProperty.Fill -> brush as T
            else -> super.getOrDefault(property, defaultValue)
        }
    }
}

@Composable
fun GradientIcon(image: ImageVector, gradientConfig: GradientConfig) {

    val configs = hashMapOf<String, VectorConfig>(image.root.name to gradientConfig)

    Icon(
        painter = rememberVectorPainter(image = image, configs = configs),
        contentDescription = null,
        modifier = Modifier.size(100.dp),
    )
}

@Composable
fun rememberVectorPainter(image: ImageVector, configs: Map<String, VectorConfig>): VectorPainter {

    return androidx.compose.ui.graphics.vector.rememberVectorPainter(
        defaultWidth = image.defaultWidth,
        defaultHeight = image.defaultHeight,
        viewportWidth = image.viewportWidth,
        viewportHeight = image.viewportHeight,
        name = image.name,
        tintColor = image.tintColor,
        tintBlendMode = image.tintBlendMode,
        content = { _, _ -> RenderVectorGroup(group = image.root, configs = configs) }
    )
}

@Preview(name = "GradientIcon")
@Composable
fun PreviewGradientIcon() {
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xff9F5CFF),
            Color(0xffF0A966),
        ),
        start = Offset(12f, 0f),
        end = Offset(12f, 24f),
    )

    GradientIcon(
        image = Icons.Filled.Star,
        gradientConfig = GradientConfig(gradient)
    )
}