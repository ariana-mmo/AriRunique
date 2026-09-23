package com.plcoding.auth.domain

import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult

//THIS IS WHERE WE SAY WE NEED A REPOSITORY

interface AuthRepository {
    // What does registering users actually need in our app?
    suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network>
}