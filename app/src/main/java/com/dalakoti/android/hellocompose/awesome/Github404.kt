package com.dalakoti.android.hellocompose.awesome

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.R

@Composable
fun Github404() {

    val infiniteTransition = rememberInfiniteTransition(label = "404github")

    val width = with(LocalDensity.current) {
        LocalWindow.current.screenWidthDp.toPx()
    }
    val height = with(LocalDensity.current) {
        LocalWindow.current.screenHeightDp.toPx()
    }

    val bgImageScaleFactor by infiniteTransition.animateFloat(
        initialValue = 1.3f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000, delayMillis = 500, easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ), label = "bgScale"
    )

    val githubAvatarX = remember {
        Animatable(width.div(2))
    }

    val githubAvatarY = remember {
        Animatable(height.div(2))
    }

    val yFactor by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000, delayMillis = 500, easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ), label = "yFactor"
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Box(Modifier.fillMaxSize()) {
            BackgroundImageGithub(bgImageScaleFactor)
            Looking404(githubAvatarX.value, githubAvatarY.value.plus(yFactor))
            HomeOne(githubAvatarX.value.plus(190), githubAvatarY.value.minus(180))
            SpaceShipShadow(
                githubAvatarX.value.plus(80),
                githubAvatarY.value.plus(180).minus(yFactor),
                yFactor
            )
            SpaceShip(githubAvatarX.value.plus(80), githubAvatarY.value.plus(30).minus(yFactor))
            AvatarShadow(githubAvatarX.value.minus(180), githubAvatarY.value.plus(210), yFactor)
            GithubAvatar(githubAvatarX.value.minus(180), githubAvatarY.value.minus(yFactor))
        }

    }

}

@Composable
fun BackgroundImageGithub(bgImageScaleFactor: Float) {
    Image(
        painter = painterResource(id = R.drawable.github_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .scale(bgImageScaleFactor)
    )
}

@Composable
private fun HomeTwo(
    githubAvatarX: Float,
    githubAvatarY: Float
) {
    Image(
        painter = painterResource(id = R.drawable.deserthome1),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .toInt(),
                    githubAvatarY
                        .toInt()
                )
            }
            .scale(1.5f)
    )
}

@Composable
private fun HomeOne(
    githubAvatarX: Float,
    githubAvatarY: Float
) {
    Image(
        painter = painterResource(id = R.drawable.deserthome1),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .toInt(),
                    githubAvatarY
                        .toInt()
                )
            }
            .scale(2.8f)
    )
}

@Composable
private fun SpaceShip(
    githubAvatarX: Float,
    githubAvatarY: Float
) {
    Image(
        painter = painterResource(id = R.drawable.spaceship),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .toInt(),
                    githubAvatarY
                        .toInt()
                )
            }
            .scale(1.8f)
    )
}

@Composable
private fun SpaceShipShadow(
    githubAvatarX: Float,
    githubAvatarY: Float,
    yFactor: Float
) {
    Image(
        painter = painterResource(id = R.drawable.spshipshadow),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .toInt(),
                    githubAvatarY
                        .toInt()
                )
            }
            .scale(1.8f + yFactor.times(0.02f))
    )
}

@Composable
private fun Looking404(
    githubAvatarX: Float,
    githubAvatarY: Float
) {
    Image(
        painter = painterResource(id = R.drawable.notfound),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .minus(520)
                        .toInt(),
                    githubAvatarY
                        .toInt()
                        .minus(130)
                )
            }
    )
}

@Composable
fun GithubAvatar(
    githubAvatarX: Float,
    githubAvatarY: Float
) {
    Image(
        painter = painterResource(id = R.drawable.githubavatar),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .toInt(),
                    githubAvatarY.toInt()
                )
            }
            .scale(1.8f)
    )
}

@Composable
private fun AvatarShadow(
    githubAvatarX: Float,
    githubAvatarY: Float,
    yFactor: Float
) {
    Image(
        painter = painterResource(id = R.drawable.avatarshadow),
        contentDescription = null, modifier = Modifier
            .offset {
                IntOffset(
                    githubAvatarX
                        .toInt(),
                    githubAvatarY.toInt()
                )
            }
            .scale(1.8f + yFactor.times(0.02f))
    )
}
