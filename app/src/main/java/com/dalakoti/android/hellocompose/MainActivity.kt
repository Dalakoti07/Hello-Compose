package com.dalakoti.android.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dalakoti.android.hellocompose.effects.bounceClick
import com.dalakoti.android.hellocompose.effects.pressClickEffect
import com.dalakoti.android.hellocompose.effects.rippleEffect
import com.dalakoti.android.hellocompose.effects.shakeClickEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = {}, modifier = Modifier.bounceClick()) {
                    Text(text = "Bouncy click")
                }
                Button(
                    onClick = {}, modifier = Modifier
                        .padding(
                            top = 20.dp,
                        )
                        .pressClickEffect()
                ) {
                    Text(text = "Press Effect click")
                }
                Button(
                    onClick = {}, modifier = Modifier
                        .shakeClickEffect()
                        .padding(
                            top = 20.dp,
                        )
                ) {
                    Text(text = "Shake Effect click")
                }
                Button(onClick = {

                }) {
                    Text(text = "Simple btn")
                }
                Surface(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .clickable {

                        }
                ) {
                    Text(text = "Simple Surface")
                }
            }
        }
    }
}

