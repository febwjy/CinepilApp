package com.febwjy.cinepilapp.utils

/**
 * Created by Febby Wijaya on 22/01/24.
 */
sealed class NetworkResult <T> (
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}