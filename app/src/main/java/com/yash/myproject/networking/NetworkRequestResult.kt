package com.yash.myproject.networking

sealed class NetworkRequestResult<out T : Any>
{
    data class Success<out T : Any>(val data: T) : NetworkRequestResult<T>()

    data class Error(val errorCode: Int?, val errorMessage: String) : NetworkRequestResult<Nothing>()

    object SuccessWithoutBody : NetworkRequestResult<Nothing>()

    object Unauthorized : NetworkRequestResult<Nothing>()
}