package com.plcoding.run.presentation.active_run

sealed interface ActiveRunAction {
    data object OnToggleRunClick: ActiveRunAction

    data object OnFinishRunCLick: ActiveRunAction

    data object OnResumeRunClick: ActiveRunAction
    data object OnBackClick: ActiveRunAction

    data class SubmitLocationPermissionInfo(
        val acceptedLocationPermissionInfo: Boolean,
        val showLocationRationale: Boolean
    ): ActiveRunAction

    data class SubmitNotificationPermissionInfo(
        val acceptedNotificationPermissionInfo: Boolean,
        val showNotificationPermissionRationale: Boolean
    ): ActiveRunAction

    data object DismissRationaleDialog: ActiveRunAction
}