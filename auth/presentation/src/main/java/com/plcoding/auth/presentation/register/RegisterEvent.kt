package com.plcoding.auth.presentation.register

import com.plcoding.core.presentation.ui.UiText

sealed interface RegisterEvent {

    data object RegistrationSuccess: RegisterEvent

    data class Error(val error: UiText): RegisterEvent

    //also we use the new basic text field to api
}
