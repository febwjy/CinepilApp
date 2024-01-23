package com.febwjy.cinepilapp.domain.repository

import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import com.febwjy.cinepilapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Febby Wijaya on 22/01/24.
 */
interface MovieRepository{

    suspend fun getGenreMovie(apiKey: String):
            Flow<NetworkListResult<List<GenreResponse.Genres>, GenreResponse>>

    suspend fun getPopular():
            Flow<NetworkListResult<List<PopularListResponse.Result>, PopularListResponse>>


    suspend fun getMovieByGenre(apiKey: String, withGenre: String, page: Int):
            Flow<NetworkListResult<List<MovieListResponse.MovieList>, MovieListResponse>>

    suspend fun getReviewMovie(apiKey: String, page: Int, movieId: Int):
            Flow<NetworkListResult<List<MovieReviewResponse.Result>, MovieReviewResponse>>

}