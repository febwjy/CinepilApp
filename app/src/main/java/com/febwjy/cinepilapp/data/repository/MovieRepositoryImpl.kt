package com.febwjy.cinepilapp.data.repository

import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.network.MovieService
import com.febwjy.cinepilapp.utils.Constant
import com.febwjy.cinepilapp.utils.NetworkListResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 22/01/24.
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {

    override suspend fun getGenreMovie(apiKey: String): Flow<NetworkListResult<List<GenreResponse.Genres>, GenreResponse>> {
        return flow {
            val response = movieService.getGenre(
                apiKey = Constant.API_KEY
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val genre = mutableListOf<GenreResponse.Genres>()

                body.genres.forEach {
                    genre.add(
                        GenreResponse.Genres(
                            id = it.id,
                            name = it.name
                        )
                    )
                }
                emit(NetworkListResult.Success(genre))
            } else {
                val type = object : TypeToken<GenreResponse>() {}.type
                val error = Gson().fromJson<GenreResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.statusCode = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }

    override suspend fun getPopular(): Flow<NetworkListResult<List<PopularListResponse.Result>, PopularListResponse>> {
        return flow {
            val response = movieService.getPopular(
                apiKey = Constant.API_KEY
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val popular = mutableListOf<PopularListResponse.Result>()

                body.results?.forEach{
                    popular.add(
                        PopularListResponse.Result(
                            backdropPath = it.backdropPath,
                            id = it.id,
                            title = it.title,
                            overview = it.overview,
                            posterPath = it.posterPath,
                            mediaType = it.mediaType,
                            popularity = it.popularity,
                            releaseDate = it.releaseDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount,
                            name = it.name,
                        )
                    )
                }
                emit(NetworkListResult.Success(popular))
            } else {
                val type = object : TypeToken<PopularListResponse>() {}.type
                val error = Gson().fromJson<PopularListResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.statusCode = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }

    override suspend fun getMovieByGenre(
        apiKey: String,
        withGenre: String,
        page: Int
    ): Flow<NetworkListResult<List<MovieListResponse.MovieList>, MovieListResponse>> {
        return flow {
            val response = movieService.getMovieByGenre(
                apiKey = Constant.API_KEY,
                withGenre = withGenre,
                page = page
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val movieByGenre = mutableListOf<MovieListResponse.MovieList>()

                body.movieList.forEach {
                    movieByGenre.add(
                        MovieListResponse.MovieList(
                            backdropPath = it.backdropPath,
                            id = it.id,
                            originalTitle = it.originalTitle,
                            overview = it.overview,
                            popularity = it.popularity,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            title = it.title,
                            video = it.video,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                        )
                    )
                }
                emit(NetworkListResult.Success(movieByGenre))
            } else {
                val type = object : TypeToken<MovieListResponse>() {}.type
                val error = Gson().fromJson<MovieListResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.statusCode = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }

    override suspend fun getReviewMovie(
        apiKey: String,
        page: Int,
        movieId: Int
    ): Flow<NetworkListResult<List<MovieReviewResponse.Result>, MovieReviewResponse>> {
        return flow {
            val response = movieService.getMovieReview(
                apiKey = Constant.API_KEY,
                page = page,
                movieId = movieId
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val movieReview = mutableListOf<MovieReviewResponse.Result>()

                body.results.forEach {
                    movieReview.add(
                        MovieReviewResponse.Result(
                            author = it.author,
                            content = it.content,
                            authorDetails = it.authorDetails
                        )
                    )
                }
                emit(NetworkListResult.Success(movieReview))
            } else {
                val type = object : TypeToken<MovieReviewResponse>() {}.type
                val error = Gson().fromJson<MovieReviewResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.statusCode = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }
}