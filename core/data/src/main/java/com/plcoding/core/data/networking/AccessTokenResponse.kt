package com.plcoding.core.data.networking

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class AccessTokenResponse(
    val accessToken: String,
    //It helps us to see if the token already expired before making the request
    val expirationTimestamp: String
)
