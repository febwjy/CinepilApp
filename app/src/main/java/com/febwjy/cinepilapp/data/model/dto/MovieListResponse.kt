package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName


/**
 * Created by Febby Wijaya on 22/01/24.
 */
class MovieListResponse {
    @SerializedName("results")
    val movieList: List<MovieList> = emptyList()

    @SerializedName("status_message")
    val statusMessage: String? = null

    @SerializedName("success")
    val success: Boolean? = null

    @SerializedName("status_code")
    var statusCode: Int? = null

    data class MovieList(
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
        val id: Int? = null,
        @SerializedName("original_title")
        val originalTitle: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    )

}