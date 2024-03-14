package com.dalakoti.android.hellocompose.examples

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun ListItem(item: EachItem) {
    Row(
        modifier = Modifier
            .padding(
                vertical = 10.dp,
                horizontal = 10.dp,
            )
            .fillMaxWidth()
            .background(
                color = Color(0xFF76c679),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                8.dp,
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(imageVector = item.icon, contentDescription = null)
        Text(
            text = item.title,
            modifier = Modifier.padding(
                start = 10.dp,
            ),
        )
    }
}

data class EachItem(
    val icon: ImageVector,
    val title: String,
)

val allIcons = listOf(
    Icons.Outlined.Add,
    Icons.Outlined.Build,
    Icons.Outlined.AddCircle,
    Icons.Outlined.Check,
    Icons.Outlined.LocationOn,
)


fun getItemAtIndex(idx: Int): EachItem {
    return EachItem(
        icon = allIcons[Random(3).nextInt(allIcons.size - 1)],
        title = "Content #$idx"
    )
}

val Contents = (1..20).map {
    getItemAtIndex(it)
}

private class CollapsingAppBarNestedScrollConnection(
    val appBarMaxHeight: Int
) : NestedScrollConnection {

    var appBarOffset: Int by mutableIntStateOf(0)
        private set

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y.toInt()
        val newOffset = appBarOffset + delta
        val previousOffset = appBarOffset
        appBarOffset = newOffset.coerceIn(-appBarMaxHeight, 0)
        val consumed = appBarOffset - previousOffset
        return Offset(0f, consumed.toFloat())
    }
}

@Preview
@Composable
fun ScrollInternals() {
    val AppBarHeight = 56.dp
    val Purple40 = Color(0xFF6650a4)

    val appBarMaxHeightPx = with(LocalDensity.current) { AppBarHeight.roundToPx() }
    val connection = remember(appBarMaxHeightPx) {
        CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
    }
    val density = LocalDensity.current
    val spaceHeight by remember(density) {
        derivedStateOf {
            with(density) {
                (appBarMaxHeightPx + connection.appBarOffset).toDp()
            }
        }
    }

    Box(Modifier.nestedScroll(connection)) {
        Column {
            Spacer(
                Modifier
                    .padding(4.dp)
                    .height(spaceHeight)
            )
            LazyColumn {
                items(Contents.size) {
                    ListItem(item = Contents[it])
                }
            }
        }

        TopAppBar(
            modifier = Modifier.offset { IntOffset(0, connection.appBarOffset) },
            title = { Text(text = "Jetpack Compose") },
            contentColor = Color.White,
            backgroundColor = Purple40,
        )
    }
}
