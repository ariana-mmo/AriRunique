package com.plcoding.run.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn


//AndroidLocationObserver = sabe obtener GPS
//RunningTracker = sabe cuándo activarlo y exponer su estado
//ViewModel = sabe qué hacer con la ubicación para la pantalla/carrera
class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope
) {
    private val isObservingLocation = MutableStateFlow(false)

    //We listen to every single change in the location
    val currentLocation = isObservingLocation
        //block triggers when isObservingLocation has a new value.
        //map the outcome (isObservingLocation) of a specific flow to a different flow
        //============== EMITE UBICACIÓN EN FLOWS =============
        .flatMapLatest { isObservingLocation ->
            if (isObservingLocation) {
                //flow from the location observer
                locationObserver.observeLocation(1000L)
            } else flowOf()
        }

        //convert from Flow to StateFlow
        .stateIn(
            //coroutine that lives while the app lives
            applicationScope,
            //it only starts the gps if somebody observes currentLocation
            SharingStarted.Lazily,
            //before gps there is no location
            null
        )

    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    fun stopObservingLocation(){
        isObservingLocation.value = false
    }
}