package com.febwjy.cinepilapp.domain.usecase

import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 19/05/22.
 */
class GetMovieByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend fun invoke(api_key: String, with_genre: String, page: Int) :
            Flow<NetworkListResult<List<MovieListResponse.MovieList>, MovieListResponse>> {
        return movieRepository.getMovieByGenre(api_key, with_genre, page)
    }
}