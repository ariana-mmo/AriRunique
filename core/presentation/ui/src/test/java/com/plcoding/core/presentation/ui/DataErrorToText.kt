package com.plcoding.core.presentation.ui

import com.plcoding.core.domain.util.DataError

//asUiText -> generic extended function of DataError
fun DataError.asUiText(): UiText{
    return when(this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(
            //R (@StringRes)
            R.string.error_disk_full
            )
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )
        //We dont handle it here, unauthorized could mean multiple things
        //DataError.Network.UNAUTHORIZED -> TODO()
        //Handle it for registration
        //DataError.Network.CONFLICT -> TODO()
        DataError.Network.TOO_MANY_REQUEST -> UiText.StringResource(
            R.string.error_too_many_requests
        )
        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_sever_error
        )
        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )
        else -> UiText.StringResource(R.string.error_unknown)
    }
}