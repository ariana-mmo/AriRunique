package com.plcoding.core.location

import kotlin.time.Duration

data class LocationTimestamp(
    val Location: LocationWithAltitude,
    val durationTimestamp: Duration
)
