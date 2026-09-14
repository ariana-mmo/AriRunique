package com.plcoding.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
//T is for all data types than may come with the flow and it will be sent to onEvent
fun <T>ObserveAsEvents(
    //Flow
    flow: Flow<T>,
    //We pass some keys
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
    ){
        val lifecycleOwner = LocalLifecycleOwner.current
        //when the attributes changes, it will re-execute inside the block code
        LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
            //We want to repeate the block once started

            //this is used to collect flows when the ui life cycle started (not when the app is
            // backgrounded but when is in the front)
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                //Here we actually take the events
                withContext(Dispatchers.Main.immediate){
                    flow.collect(onEvent)
                }
            }
        }
}