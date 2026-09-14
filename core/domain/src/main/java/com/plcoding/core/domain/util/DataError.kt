package com.plcoding.core.domain.util

sealed interface DataError: Error{

    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        //when the user tries to reg with an email already used
        CONFLICT,
        //API quota exceeded
        TOO_MANY_REQUEST,
        NO_INTERNET,
        //if trying to upload an image to large for the server
        PAYLOAD_TOO_LARGE,
        //not the fault of the client but of the server
        SERVER_ERROR,
        //json violates the schema accepted
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        //when insert something into the database but there is no enough storage on the device
        //or no permission
        DISK_FULL
    }

}