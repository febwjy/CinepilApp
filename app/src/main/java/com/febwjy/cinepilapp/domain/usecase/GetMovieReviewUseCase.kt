package com.febwjy.cinepilapp.domain.usecase

import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 22/01/24.
 */
class GetMovieReviewUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend fun invoke(apiKey: String, movieId: Int, page: Int) :
            Flow<NetworkListResult<List<MovieReviewResponse.Result>, MovieReviewResponse>> {
        return movieRepository.getReviewMovie(apiKey, page, movieId)
    }
}