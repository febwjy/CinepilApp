package com.febwjy.cinepilapp.network

import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.data.model.dto.MovieDetailResponse
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Febby Wijaya on 16/09/23.
 */
interface MovieService {

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api_key: String,
    ): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") api_key: String,
        @Query("with_genres") with_genre: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(
        @Path("movie_id")movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<MovieReviewResponse>

}