package com.febwjy.cinepilapp.data.model.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 22/01/24.
 */
class MovieReviewResponse {

    @SerializedName("results")
    val results: List<Result> = emptyList()

    @SerializedName("status_message")
    var statusMessage: String? = null

    @SerializedName("status_code")
    var statusCode: Int? = null

    data class Result(
        val author: String? = null,
        val content: String? = null,
        @SerializedName("author_details")
        val authorDetails: Detail? = null
    )

    data class Detail (
        val rating: Double? = null
    )

}