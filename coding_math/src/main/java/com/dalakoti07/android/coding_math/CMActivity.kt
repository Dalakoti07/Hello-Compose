package com.dalakoti07.android.coding_math

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dalakoti07.android.coding_math.examples.BasicLineDraws
import com.dalakoti07.android.coding_math.examples.BouncingBall
import com.dalakoti07.android.coding_math.examples.CircularPathIllustration
import com.dalakoti07.android.coding_math.examples.DrawCirclesInCircularPath
import com.dalakoti07.android.coding_math.examples.EllipticalPath
import com.dalakoti07.android.coding_math.examples.SimpleSinWave
import com.dalakoti07.android.coding_math.examples.motion.FireWorks
import com.dalakoti07.android.coding_math.examples.motion.ParticleSystem
import com.dalakoti07.android.coding_math.examples.motion.UnderForceDemonstration
import com.dalakoti07.android.coding_math.examples.touch.CircularPathNDragIllustration
import com.dalakoti07.android.coding_math.examples.touch.DragGestureExample

class CMActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentAnimation by remember {
                mutableStateOf(0)
            }

            MaterialTheme {
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
        UnderForceDemonstration(
            modifier = Modifier.fillMaxSize(),
        )
    }
    add {
        FireWorks(
            modifier = Modifier.fillMaxSize(),
        )
    }
    add {
        ParticleSystem(
            modifier = Modifier.fillMaxSize(),
        )
    }
    add {
        CircularPathNDragIllustration()
    }
    add {
        DragGestureExample()
    }
    add{
        EllipticalPath()
    }
    add{
        DrawCirclesInCircularPath()
    }
    add {
        CircularPathIllustration()
    }
    add {
        BouncingBall()
    }
    add {
        SimpleSinWave()
    }
    add {
        BasicLineDraws()
    }
}