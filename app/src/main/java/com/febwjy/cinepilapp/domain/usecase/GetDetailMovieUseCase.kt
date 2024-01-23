package com.febwjy.cinepilapp.domain.usecase

import com.febwjy.cinepilapp.data.model.dto.MovieDetailResponse
import com.febwjy.cinepilapp.network.MovieService
import com.febwjy.cinepilapp.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 22/01/24.
 */
@ActivityRetainedScoped
class GetDetailMovieUseCase @Inject constructor(
    private val movieService: MovieService
){
    private suspend fun getMovieDetail(apiKey: String, movieId: Int) =
        movieService.getDetailMovie(movieId, apiKey)

    suspend fun invoke(apiKey: String, movieId: Int) :
            Flow<NetworkResult<MovieDetailResponse>> {
        return flow {
            emit(safeApiCall { getMovieDetail(apiKey, movieId)})
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}