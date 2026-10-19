package com.plcoding.run.location

import android.location.Location
import com.plcoding.core.domain.location.LocationWithAltitude

//this Location is from Android
fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.plcoding.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
        )
}