package com.plcoding.core.domain.util

//to model a hierarchy here
//<> -> generic argument (wraps)
sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>

    //Implements interface error with package name(2 Error)
    data class Error<out E: com.plcoding.core.domain.util.Error>(val error: E): Result<Nothing, E>
}

//a function of extension to a type Result
//to map a result
 //map function
//map: (T) -> R is a lambda function
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this){
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}


fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E>{
    return map {  }
}

typealias EmptyDataResult<E> = Result<Unit, E>