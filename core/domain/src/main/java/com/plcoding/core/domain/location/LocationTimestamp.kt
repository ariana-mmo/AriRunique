package com.plcoding.core.domain.location

import kotlin.time.Duration

data class LocationTimestamp(
    val Location: LocationWithAltitude,
    val durationTimestamp: Duration
)
