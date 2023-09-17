package com.febwjy.cinepilapp.domain.usecase

import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 16/09/23.
 */
class GetMovieReviewUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend fun invoke(api_key: String, movie_id: Int, page: Int) :
            Flow<NetworkListResult<List<MovieReviewResponse.Result>, MovieReviewResponse>> {
        return movieRepository.getReviewMovie(api_key, page, movie_id)
    }
}