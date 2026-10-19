package com.plcoding.run.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.plcoding.core.domain.location.LocationWithAltitude
import com.plcoding.run.domain.LocationObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.time.Duration.Companion.seconds

class AndroidLocationObserver(
    private val context: Context
): LocationObserver {

    private val client = LocationServices.getFusedLocationProviderClient(context)


    override fun observeLocation(interval: Long): Flow<LocationWithAltitude> {

        return callbackFlow {
            val locationManager = context.getSystemService<LocationManager>()!!

            //To be able to have the location, we need the gps or the network. The fastest and the ideal case
                //is both
            var isGpsEnabled = false
            var isNetworkEnabled = false

            //If we don't have it enable, then we wait.
            while (!isGpsEnabled && !isNetworkEnabled){
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                //If it is still not enabled, then we wait a little.
                if (!isGpsEnabled && !isNetworkEnabled){
                    delay(3.seconds)
                }

            }

            //catch location value (almost immediate access), android catch location from other apps.
            //I have my android location, I try it to be locationWithAltitude
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                close()
            } else {
                client.lastLocation.addOnSuccessListener {
                    it?.let { location ->
                        trySend(location.toLocationWithAltitude())
                    }
                }

                //I build my request
                //Location accurate
                val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                    .build()

                //A callBack triggers everytime there is a new location (once every interval)
                val locationCallback = object: LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let { location ->
                            trySend(location.toLocationWithAltitude())
                        }
                    }
                }

                client.requestLocationUpdates(request,locationCallback, Looper.getMainLooper())

                //triggered when the flow is closed.
                //When is it closed? When Viewmodelscope is cleared
                awaitClose {
                    client.removeLocationUpdates(locationCallback)
                }
            }
        }

    }
}