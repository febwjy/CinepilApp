package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 16/09/23.
 */
class MovieReviewResponse {

    @SerializedName("results")
    val results: List<Result>? = emptyList()

    @SerializedName("status_message")
    var status_message: String? = null

    @SerializedName("status_code")
    var status_code: Int? = null

    data class Result(
        val author: String? = null,
        val content: String? = null,
        val author_details: Detail? = null
    )

    data class Detail (
        val rating: Double? = null
    )


}