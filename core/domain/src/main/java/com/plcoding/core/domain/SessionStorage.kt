package com.plcoding.core.domain

//what a session storage should be able to do
interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}