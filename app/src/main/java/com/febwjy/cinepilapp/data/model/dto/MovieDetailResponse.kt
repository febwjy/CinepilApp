package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 22/01/24.
 */
class MovieDetailResponse {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("overview")
    val overview: String? = null

    @SerializedName("poster_path")
    val posterPath: String? = null

    @SerializedName("runtime")
    val runtime: Int? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language> = emptyList()

    @SerializedName("genres")
    val genres: List<Genres> = emptyList()

    data class Genres (
        val name: String? = null
    )

    data class Language(
        @SerializedName("english_name")
        val englishName: String? = null
    )

}