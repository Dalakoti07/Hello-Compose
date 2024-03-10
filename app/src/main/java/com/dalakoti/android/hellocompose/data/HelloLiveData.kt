package com.dalakoti.android.hellocompose.data

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers

@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(
        onClick ={ count += 1 }
    ){
        Text(
            text="Count: $count",
        )
    }
}

@Composable
fun HelloLiveData(msg: LiveData<String>) {

    var count by remember {
        mutableStateOf("saurabh")
    }

    val state by msg.observeAsState()

    val scope = rememberCoroutineScope()

    val scopeIO = rememberCoroutineScope{
        Dispatchers.IO
    }

    DisposableEffect(key1 = Unit, effect = {
        onDispose {

        }
    })

    LaunchedEffect(key1 = Unit, block = {

    })

    val stringVal = state
}
