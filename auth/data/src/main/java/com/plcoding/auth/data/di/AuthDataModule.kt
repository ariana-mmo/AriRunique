package com.plcoding.auth.data.di

import com.plcoding.auth.data.AuthRepositoryImpl
import com.plcoding.auth.data.EmailPatternValidator
import com.plcoding.auth.domain.AuthRepository
import com.plcoding.auth.domain.PatternValidator
import com.plcoding.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    //help koin to understand how certain dependencies in our project are created
    single<PatternValidator> {
        EmailPatternValidator
    }
    //it will figure out the dependencies
    singleOf(::UserDataValidator)
    //So when we try to implement AuthRepository then we need to use the AuthRepositoryImplement
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}