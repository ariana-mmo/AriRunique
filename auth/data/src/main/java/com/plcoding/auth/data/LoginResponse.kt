package com.plcoding.auth.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable

data class LoginResponse (
    val email: String,
    val password: String
    )