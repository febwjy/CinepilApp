package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 22/01/24.
 */
class GenreResponse {

    @SerializedName("genres")
    val genres: List<Genres> = emptyList()

    @SerializedName("status_code")
    var statusCode: Int? = null

    @SerializedName("status_message")
    val statusMessage: String? = null

    data class Genres(
        val id: Int? = null,
        val name: String? = null
    )

}