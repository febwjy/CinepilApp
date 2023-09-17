package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 16/09/23.
 */
class MovieDetailResponse {

    @SerializedName("backdrop_path")
    val backdrop_path: String? = null

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("original_title")
    val original_title: String? = null

    @SerializedName("overview")
    val overview: String? = null

    @SerializedName("popularity")
    val popularity: Double? = null

    @SerializedName("poster_path")
    val poster_path: String? = null

    @SerializedName("release_date")
    val release_date: String? = null

    @SerializedName("runtime")
    val runtime: Int? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("video")
    val video: Boolean? = null

    @SerializedName("vote_average")
    val vote_average: Double? = null

    @SerializedName("vote_count")
    val vote_count: Int? = null

    @SerializedName("status_message")
    var status_message: String? = null

    @SerializedName("status_code")
    var status_code: Int? = null

    @SerializedName("spoken_languages")
    val spoken_languages: List<MovieDetailResponse.Language>? = emptyList()

    @SerializedName("genres")
    val genres: List<MovieDetailResponse.Genres>? = emptyList()

    data class Genres (
        val name: String? = null
    )

    data class Language(
        val english_name: String? = null
    )

}