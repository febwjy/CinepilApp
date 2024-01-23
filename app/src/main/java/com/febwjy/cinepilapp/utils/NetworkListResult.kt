package com.febwjy.cinepilapp.utils

/**
 * Created by Febby Wijaya on 22/01/24.
 */
sealed class NetworkListResult <out T : Any, out U : Any> {
    data class Success <T: Any>(val data : T) : NetworkListResult<T, Nothing>()
    data class Error <U : Any>(val rawResponse: U) : NetworkListResult<Nothing, U>()
}
