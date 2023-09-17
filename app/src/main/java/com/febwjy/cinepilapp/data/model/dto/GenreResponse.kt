package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 16/09/23.
 */
class GenreResponse {

    @SerializedName("genres")
    val genres: List<Genres>? = emptyList()


    @SerializedName("status_code")
    var status_code: Int? = null

    @SerializedName("status_message")
    val status_message: String? = null

    data class Genres(
        val id: Int? = null,
        val name: String? = null
    )

}