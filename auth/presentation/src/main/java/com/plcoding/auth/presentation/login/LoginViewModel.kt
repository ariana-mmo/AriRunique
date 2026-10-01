@file:OptIn(ExperimentalFoundationApi::class)

package com.plcoding.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    //'
    private val repository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()

    val events = eventChannel.receiveAsFlow()

    init {
        state.email.textAsFlow()
            .onEach { email ->
                state = state.copy(

                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                state = state.copy(

                )
            }
            .launchIn(viewModelScope)
    }


    fun onAction(action: LoginAction){
           when(action){
               LoginAction.OnLoginClick -> login()
               LoginAction.OnTogglePasswordVisibilityClick -> {
                   state = state.copy(isPasswordVisible = !state.isPasswordVisible)
               }
               else -> Unit
           }
    }


    private fun login(){
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)

        }
        /*val result = repository.login(
            email = state.email.text.toString().trim(),
            password = state.password.text.toString()
        )*/


    }
}