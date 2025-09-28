package com.dalakoti.android.hellocompose.examples

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val TAG = "SideEffectsExample"

@Composable
fun SomeRow(text: String, icon: ImageVector, textOpacity: () -> Float) {
    Log.d(TAG, "SomeRow: ....")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text, Modifier.alpha(textOpacity()))
        Icon(icon, contentDescription = null)
    }
}

@Preview
@Composable
fun SideEffectsExample() {
    Log.d(TAG, "SideEffectsExample: ....")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Hell saurabh, this is me SDE from Microsoft",
            modifier = Modifier.baselineHeight(10.dp)
        )
        Text(
            "GDE ",
            modifier = Modifier.fillMaxWidth().baselineHeight(10.dp),
        )
    }
}