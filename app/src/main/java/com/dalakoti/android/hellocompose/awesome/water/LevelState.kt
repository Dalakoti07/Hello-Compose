package com.dalakoti.android.hellocompose.awesome.water

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun createLevelAsState(
    waterLevelProvider: () -> Int,
    bufferY: Float,
    elementParams: ElementParams,
): MutableState<LevelState> {
    return remember(elementParams.position, waterLevelProvider()) {
        when {
            isAboveElement(waterLevelProvider(), bufferY, elementParams.position) -> {
                mutableStateOf(LevelState.PlainMoving)
            }

            atElementLevel(
                waterLevelProvider(),
                bufferY,
                elementParams
            ) -> {
                mutableStateOf(LevelState.FlowsAround)
            }

            isWaterFalls(
                waterLevelProvider(),
                elementParams
            ) -> {
                mutableStateOf(LevelState.WaveIsComing)
            }

            else -> {
                mutableStateOf(LevelState.WaveIsComing)
            }
        }
    }
}


sealed class LevelState {
    object PlainMoving : LevelState()
    object FlowsAround : LevelState()
    object WaveIsComing: LevelState()
}