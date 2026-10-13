package com.plcoding.core.presentation.ui

import android.annotation.SuppressLint
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

@SuppressLint("DefaultLocale")
//Duration
fun Duration.formatted(): String {
    val totalSeconds = inWholeSeconds
    //if it is only one digit, it will have a 0 in front.
        //Ex: if it is 1 -> "01" ... if it is 10 -> "10"
        // "%02d"
            //% : replace by.
            // 0 : 0 in front.
            // 2d : 2 digits.
    val hours = String.format("%02d", totalSeconds / 3600)
    val minutes = String.format("%02d", (totalSeconds % 3600) / 60)
    val seconds = String.format("%02d", (totalSeconds % 60))

    return "$hours:$minutes:$seconds"
}

//Distance
fun Double.toFormattedKm():String {
    return "${this.roundToDecimals(1)} km"
}

//Pace
@SuppressLint("DefaultLocale")
fun Duration.toFormattedPace(distanceKm: Double): String {
    if(this == Duration.ZERO || distanceKm <= 0.0) {
        return "-"
    }

    val secondsPerKm = (this.inWholeSeconds / distanceKm).roundToInt()
    val avgPaceMinutes = secondsPerKm / 60
    val avgPaceSeconds = String.format("%02d", secondsPerKm % 60)

    return "$avgPaceMinutes:$avgPaceSeconds / km"
}



//This function is just for round to decimals

//5.678
//factor = 10^1 = 10
//round(5.678 * factor) = 56.78
//round(56.78) / 10. == 57/10 = 5.7
private fun Double.roundToDecimals(decimalCount: Int): Double {
    val factor = 10f.pow(decimalCount)
    return round(this * factor ) / factor
}