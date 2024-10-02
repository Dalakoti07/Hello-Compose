package com.dalakoti07.android.awsm_animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dalakoti07.android.awsm_animation.awsm.HueRotationExample
import com.dalakoti07.android.awsm_animation.awsm.IosCallAnimation
import com.dalakoti07.android.awsm_animation.awsm.LikeReaction
import com.dalakoti07.android.awsm_animation.paths.MoveAlongPathExample
import com.dalakoti07.android.awsm_animation.awsm.OneWordFlipAnimation
import com.dalakoti07.android.awsm_animation.awsm.TextViewFlipAnimation
import com.dalakoti07.android.awsm_animation.effects.AllEasingEffects
import com.dalakoti07.android.awsm_animation.effects.ConcentricProgressBar
import com.dalakoti07.android.awsm_animation.effects.EmojiBounce
import com.dalakoti07.android.awsm_animation.ui.theme.HelloComposeTheme

class AwsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentAnimation by remember {
                mutableStateOf(0)
            }

            HelloComposeTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    allAnimations[currentAnimation].invoke()
                    IconButton(
                        {
                            if (currentAnimation > 0) {
                                currentAnimation = currentAnimation.dec()
                            }
                        }, modifier = Modifier
                            .align(Alignment.CenterStart)
                            .background(Color.White.copy(alpha = 0.4f))
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }

                    IconButton(
                        {
                            if (currentAnimation < allAnimations.size.minus(1)) {
                                currentAnimation = currentAnimation.inc()
                            }
                        }, modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .background(Color.White.copy(alpha = 0.4f))
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    }
}

private val allAnimations = mutableListOf<@Composable () -> Unit>().apply {
    add {
        ConcentricProgressBar()
    }
    add {
        EmojiBounce()
    }
    add {
        AllEasingEffects()
    }
    add {
        MoveAlongPathExample()
    }
    add {
        HueRotationExample()
    }
    add {
        IosCallAnimation()
    }
    add {
        LikeReaction()
    }
    add {
        OneWordFlipAnimation()
    }
    add {
        TextViewFlipAnimation()
    }
}