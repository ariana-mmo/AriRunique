package com.plcoding.auth.domain

import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult

//THIS IS WHERE WE SAY WE NEED A REPOSITORY

interface AuthRepository {
    // What does registering users actually need in our app?

    //Why do we return an EmptyResult? Because RegisterRequest, LoginRequest
    // and LoginResponse are from data, no domain. That's why domain should
    // not know anything about this
    suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network>


    suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network>
}