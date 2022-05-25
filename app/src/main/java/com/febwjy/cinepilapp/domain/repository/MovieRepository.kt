package com.febwjy.cinepilapp.domain.repository

import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.data.model.dto.MovieDetailResponse
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.utils.NetworkListResult
import com.febwjy.cinepilapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Febby Wijaya on 19/05/22.
 */
interface MovieRepository{

    suspend fun getGenreMovie(api_key: String):
            Flow<NetworkListResult<List<GenreResponse.Genres>, GenreResponse>>

    suspend fun getMovieByGenre(api_key: String, with_genre: String, page: Int):
            Flow<NetworkListResult<List<MovieListResponse.MovieList>, MovieListResponse>>

    suspend fun getReviewMovie(api_key: String, page: Int, movie_id: Int):
            Flow<NetworkListResult<List<MovieReviewResponse.Result>, MovieReviewResponse>>

}