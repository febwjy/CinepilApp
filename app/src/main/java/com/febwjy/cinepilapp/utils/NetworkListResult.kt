package com.febwjy.cinepilapp.utils

/**
 * Created by Febby Wijaya on 16/09/23.
 */
sealed class NetworkListResult <out T : Any, out U : Any> {
    data class Success <T: Any>(val data : T) : NetworkListResult<T, Nothing>()
    data class Error <U : Any>(val rawResponse: U) : NetworkListResult<Nothing, U>()
}
