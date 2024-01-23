package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName


/**
 * Created by Febby Wijaya on 22/01/2024.
 */
class PopularListResponse {

    @SerializedName("results")
    var results: List<Result>? = emptyList()

    @SerializedName("status_message")
    val statusMessage: String? = null

    @SerializedName("success")
    val success: Boolean? = null

    @SerializedName("status_code")
    var statusCode: Int? = null

    data class Result (
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
        val id: Int? = null,
        val title: String? = null,
        val overview: String? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("media_type")
        val mediaType: String? = null,
        val popularity: Double? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null,
        val name: String? = null
    )

}