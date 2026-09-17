package com.plcoding.auth.presentation.intro

sealed interface IntroAction {
//Here we define all the things user could trigger in the screen
    data object OnSignInClick: IntroAction
    data object OnSignUpClick: IntroAction
}