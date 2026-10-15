package com.plcoding.run.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

//should request the LOCATION permission
fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}

//should request the NOTIFICATION permission
fun ComponentActivity.shouldShowNotificationPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= 33 &&
        shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

//this function returns a true if it has permission (location or notification) and
//returns a false if it doesn't
private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

//this function helps to ask if it SPECIFICALLY has LOCATION permission
fun Context.hasLocationPermission(): Boolean {
    return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}

//this function helps to ask if it SPECIFICALLY has NOTIFICATION permission
fun Context.hasNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= 33) {
        hasPermission(Manifest.permission.POST_NOTIFICATIONS)
    } else true
}
