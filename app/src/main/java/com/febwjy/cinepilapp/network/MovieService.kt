package com.febwjy.cinepilapp.network

import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.data.model.dto.MovieDetailResponse
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Febby Wijaya on 22/01/24.
 */
interface MovieService {

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") apiKey: String,
    ): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") withGenre: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("trending/movie/day")
    suspend fun getPopular(
        @Query("api_key") apiKey: String
    ): Response<PopularListResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(
        @Path("movie_id")movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieReviewResponse>

}