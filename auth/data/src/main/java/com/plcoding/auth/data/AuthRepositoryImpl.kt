@file:OptIn(InternalSerializationApi::class)

package com.plcoding.auth.data

import com.plcoding.auth.domain.AuthRepository
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult
import io.ktor.client.HttpClient
import kotlinx.serialization.InternalSerializationApi

//THIS IS HOW TO CREATE THE CLIENT FOR THE REPOSITORY
class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
                        //what goes to the api and what we spect to get from return
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }

    /*override suspend fun login(email: String, password: String): Result{

        val result =  httpClient.post<LoginRequest, LoginResponse> (
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )


        return
    }*/
}
