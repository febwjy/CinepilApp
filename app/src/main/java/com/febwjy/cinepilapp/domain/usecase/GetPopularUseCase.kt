package com.febwjy.cinepilapp.domain.usecase

import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import com.febwjy.cinepilapp.domain.repository.MovieRepository
import com.febwjy.cinepilapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 22/01/2024.
 */
class GetPopularUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun invoke() :
            Flow<NetworkListResult<List<PopularListResponse.Result>, PopularListResponse>> {
        return movieRepository.getPopular()
    }

}