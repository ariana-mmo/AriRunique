@file:OptIn(ExperimentalFoundationApi::class)

package com.plcoding.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
//compose state, flow state (reactivity)
    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegisterAction){

    }
}