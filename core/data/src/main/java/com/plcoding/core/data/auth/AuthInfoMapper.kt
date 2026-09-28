package com.plcoding.core.data.auth

import com.plcoding.core.domain.AuthInfo
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable{
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}

@OptIn(InternalSerializationApi::class)

fun AuthInfoSerializable.toAuthInfo(): AuthInfo{
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}
