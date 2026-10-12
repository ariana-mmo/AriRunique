package com.plcoding.run.presentation.run_overview

sealed interface RunOverViewAction {
    data object OnStartClick: RunOverViewAction
    data object OnLogoutClick: RunOverViewAction
    data object OnAnalyticsClick: RunOverViewAction
}