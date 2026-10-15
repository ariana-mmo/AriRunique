@file:OptIn(ExperimentalMaterial3Api::class)

package com.plcoding.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.core.presentation.designsystem.R
import com.plcoding.core.presentation.designsystem.RuniqueTheme
import com.plcoding.core.presentation.designsystem.StartIcon
import com.plcoding.core.presentation.designsystem.StopIcon
import com.plcoding.core.presentation.designsystem.components.RuniqueDialog
import com.plcoding.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.plcoding.core.presentation.designsystem.components.RuniqueOutlinedActionButton
import com.plcoding.core.presentation.designsystem.components.RuniqueScaffold
import com.plcoding.core.presentation.designsystem.components.RuniqueToolbar
import com.plcoding.run.presentation.active_run.components.RunDataCard
import com.plcoding.run.presentation.util.hasLocationPermission
import com.plcoding.run.presentation.util.hasNotificationPermission
import com.plcoding.run.presentation.util.shouldShowLocationPermissionRationale
import com.plcoding.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        //This block of code will be called when the user did something with
            //these permission request

        //Coarse Location: General location
        val hasCourseLocationPermission = perms[
            Manifest.permission.ACCESS_COARSE_LOCATION
        ] == true

        //Fine Location: Specific Location
        val hasFineLocationPermission = perms[
            Manifest.permission.ACCESS_FINE_LOCATION
        ] == true

        val hasNotificationPermission = if(Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        //Rationale is just an explanation to the user why they should accept the permission
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermissionInfo = hasCourseLocationPermission &&
                        hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermissionInfo = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
    }

    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermissionInfo = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermissionInfo = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale
            )
        )

        if(!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRuniquePermissions(context)
        }
    }

    RuniqueScaffold(
        withGradient = false,
        topAppBar = {
            RuniqueToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBlackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = if(state.shouldTrack) {
                    StopIcon
                } else StartIcon,
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if(state.shouldTrack){
                        stringResource(R.string.pause_run)
                } else stringResource(R.string.start_run)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }

    if(state.showLocationRationale || state.showNotificationRationale) {
        RuniqueDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = {/* Normal dismissing not allowed for permissions */},
                //3 scenarios: location accepted but not notification,
                //              notification accepted but not location,
                //              location denied and notification denied
                //in all cases an explanation required
            description = when{
                //denied both
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }
                state.showLocationRationale -> {
                    stringResource(R.string.location_rationale)
                }
                else -> {
                    stringResource(R.string.notification_rationale)
                }
            },
            primaryButton = {
                RuniqueOutlinedActionButton(
                    text = stringResource(R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.DismissRationaleDialog)
                        //if it was pressed okay, then we have to re request those permission
                            //that aren't accepted.
                        permissionLauncher.requestRuniquePermissions(context)
                    }
                )
            },
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRuniquePermissions(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val notificationPermissions = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf()
    }

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermissions)
        }
        !hasLocationPermission -> {
            launch(locationPermissions)
        }

        else -> launch(notificationPermissions)
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RuniqueTheme{
       ActiveRunScreen(
           state = ActiveRunState(),
           onAction = {}
       )
   }
}