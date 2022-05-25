package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName




/**
 * Created by Febby Wijaya on 19/05/22.
 */
class MovieListResponse {

    @SerializedName("page")
    val page: Int? = null

    @SerializedName("results")
    val movieList: List<MovieList>? = emptyList()

    @SerializedName("total_pages")
    val totalPages: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null

    @SerializedName("status_message")
    val status_message: String? = null

    @SerializedName("success")
    val success: Boolean? = null

    @SerializedName("status_code")
    var status_code: Int? = null

    data class MovieList(
        val backdrop_path: String? = null,
        val id: Int? = null,
        val original_title: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        val poster_path: String? = null,
        val release_date: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        val vote_average: Double? = null,
        val vote_count: Int? = null
    )

}