package com.febwjy.cinepilapp.ui.moviedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.febwjy.cinepilapp.data.model.dto.MovieDetailResponse
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.domain.usecase.GetDetailMovieUseCase
import com.febwjy.cinepilapp.domain.usecase.GetMovieReviewUseCase
import com.febwjy.cinepilapp.ui.MainViewModel
import com.febwjy.cinepilapp.utils.NetworkListResult
import com.febwjy.cinepilapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 22/01/24.
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetDetailMovieUseCase,
    private val getMovieReviewUseCase: GetMovieReviewUseCase,
    application: Application
) : MainViewModel(application) {

    private val movieDetail: MutableLiveData<NetworkResult<MovieDetailResponse>> = MutableLiveData()
    val mMovieDetail: LiveData<NetworkResult<MovieDetailResponse>> = movieDetail

    private val movieReview = MutableStateFlow<List<MovieReviewResponse.Result>>(mutableListOf())
    val mMoviewReview: StateFlow<List<MovieReviewResponse.Result>> get() = movieReview

    fun getMovieDetail(apiKey: String, idMovie: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(apiKey, idMovie).onStart {
                showLoading()
            }.catch { exception ->
                dismissLoading()
                Log.e("Error", exception.message.toString())
            }.collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        movieDetail.value = result
                    }
                    is NetworkResult.Error -> {
                        dismissLoading()
                        Log.e("result", result.message.toString())
                    }

                    else -> {}
                }
            }
        }
    }

    fun getMovieReview(apiKey: String, idMovie: Int) {
        viewModelScope.launch {
            getMovieReviewUseCase.invoke(apiKey, idMovie, 1).collect { result ->
                when (result) {
                    is NetworkListResult.Success -> {
                        dismissLoading()
                        movieReview.value = result.data
                    }
                    is NetworkListResult.Error -> {
                        dismissLoading()
                        Log.e("result", result.rawResponse.statusMessage!!)
                    }
                }
            }
        }
    }

    fun getGenres(movieDetail: MovieDetailResponse): String {
        var genres = ""
        for (i in 0 until movieDetail.genres.size) {
            val genre: MovieDetailResponse.Genres = movieDetail.genres[i]
            genres += genre.name.toString() + ", "
        }
        genres = removeTrailingComma(genres)
        return genres.ifEmpty { "-" }
    }

    fun getLanguages(movieDetail: MovieDetailResponse): String {
        var languages = ""
        for (i in 0 until movieDetail.spokenLanguages.size) {
            val language: MovieDetailResponse.Language = movieDetail.spokenLanguages[i]
            languages += language.englishName.toString()+ ", "
        }
        languages = removeTrailingComma(languages)
        return languages.ifEmpty { "-" }
    }

    private fun removeTrailingComma(text: String): String {
        var text = text
        text = text.trim { it <= ' ' }
        if (text.endsWith(",")) {
            text = text.substring(0, text.length - 1)
        }
        return text
    }

}