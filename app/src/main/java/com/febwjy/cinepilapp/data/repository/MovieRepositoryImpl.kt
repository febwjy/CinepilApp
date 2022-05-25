package com.febwjy.cinepilapp.data.repository

import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.data.model.dto.MovieDetailResponse
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.network.MovieService
import com.febwjy.cinepilapp.utils.Constant
import com.febwjy.cinepilapp.utils.NetworkListResult
import com.febwjy.cinepilapp.utils.NetworkResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 19/05/22.
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {

    override suspend fun getGenreMovie(api_key: String): Flow<NetworkListResult<List<GenreResponse.Genres>, GenreResponse>> {
        return flow {
            val response = movieService.getGenre(
                api_key = Constant.API_KEY
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val genre = mutableListOf<GenreResponse.Genres>()

                body.genres?.forEach {
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
                error.status_code = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }

    override suspend fun getMovieByGenre(
        api_key: String,
        with_genre: String,
        page: Int
    ): Flow<NetworkListResult<List<MovieListResponse.MovieList>, MovieListResponse>> {
        return flow {
            val response = movieService.getMovieByGenre(
                api_key = Constant.API_KEY,
                with_genre = with_genre,
                page = page
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val movieByGenre = mutableListOf<MovieListResponse.MovieList>()

                body.movieList?.forEach {
                    movieByGenre.add(
                        MovieListResponse.MovieList(
                            backdrop_path = it.backdrop_path,
                            id = it.id,
                            original_title = it.original_title,
                            overview = it.overview,
                            popularity = it.popularity,
                            poster_path = it.poster_path,
                            release_date = it.release_date,
                            title = it.title,
                            video = it.video,
                            vote_average = it.vote_average,
                            vote_count = it.vote_count
                        )
                    )
                }
                emit(NetworkListResult.Success(movieByGenre))
            } else {
                val type = object : TypeToken<MovieListResponse>() {}.type
                val error = Gson().fromJson<MovieListResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.status_code = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }

    override suspend fun getReviewMovie(
        api_key: String,
        page: Int,
        movie_id: Int
    ): Flow<NetworkListResult<List<MovieReviewResponse.Result>, MovieReviewResponse>> {
        return flow {
            val response = movieService.getMovieReview(
                api_key = Constant.API_KEY,
                page = page,
                movie_id = movie_id
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val movieReview = mutableListOf<MovieReviewResponse.Result>()

                body.results?.forEach {
                    movieReview.add(
                        MovieReviewResponse.Result(
                            author = it.author,
                            content = it.content,
                            author_details = it.author_details
                        )
                    )
                }
                emit(NetworkListResult.Success(movieReview))
            } else {
                val type = object : TypeToken<MovieReviewResponse>() {}.type
                val error = Gson().fromJson<MovieReviewResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.status_code = response.code()
                emit(NetworkListResult.Error(error))
            }
        }
    }
}