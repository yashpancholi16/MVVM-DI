package com.yash.myproject.networking

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

suspend fun <T : Any> Deferred<Response<T>>.performSafeApiCallResult():
        NetworkRequestResult<T> {
    return try {
        val result = this.await()
        if (result.isSuccessful) {
            if (result.body() != null) {
                NetworkRequestResult.Success(result.body()!!)
            } else {
                NetworkRequestResult.SuccessWithoutBody
            }
        } else {
            return if (result.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                NetworkRequestResult.Unauthorized
            } else {
                handleErrorBody(result)
            }
        }
    } catch (exception: Exception) {
        // TODO make some default message
        // TODO add logging all exceptions
        val code = if (exception::class.java == ConnectException::class.java ||
            exception::class.java == SocketTimeoutException::class.java
        )
            HttpURLConnection.HTTP_UNAVAILABLE
        else null
        NetworkRequestResult.Error(code, "Something went wrong")
    }
}

private fun <T : Any> handleErrorBody(response: Response<T>): NetworkRequestResult.Error {
    val code = response.code()
    val errorMessage = response.message()
    try {
        if (response.errorBody() != null) {
            Unit
        }
    } finally {
        return NetworkRequestResult.Error(code, errorMessage)
    }
}
