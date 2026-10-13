package com.plcoding.run.presentation.active_run

import com.plcoding.core.location.Location
import com.plcoding.run.domain.RunData
import kotlin.time.Duration

data class ActiveRunState(
    //We initialize the time at 0.
        //The total time from the start to the end
    val elapsedTime: Duration = Duration.ZERO,
    val runData: RunData = RunData(),
    val shouldTrack: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val currentLocation: Location? = null,
    val isRunFinished: Boolean = false,
    //Progress indicator on the finish. button when we click it
    val isSavingRun: Boolean = false
)
