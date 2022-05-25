package com.febwjy.cinepilapp.ui.movielist

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.domain.usecase.GetMovieByGenreUseCase
import com.febwjy.cinepilapp.ui.MainViewModel
import com.febwjy.cinepilapp.utils.NetworkListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 24/05/22.
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase, application: Application
) : MainViewModel(application) {

    private val movieList = MutableStateFlow<List<MovieListResponse.MovieList>>(mutableListOf())
    val mMovieList: StateFlow<List<MovieListResponse.MovieList>> get() = movieList

    fun getMovieList(api_key: String, id_genre: String, page: Int) {
        viewModelScope.launch {
            getMovieByGenreUseCase.invoke(api_key, id_genre, page).onStart {
                showLoading()
            }.catch { exception ->
                dismissLoading()
                Log.e("Error", exception.message.toString())
            }.collect { result ->
                when (result) {
                    is NetworkListResult.Success -> {
                        dismissLoading()
                        movieList.value = result.data
                        _loadMore.value = result.data.size > 10
                    }
                    is NetworkListResult.Error -> {
                        dismissLoading()
                        Log.e("result", result.rawResponse.status_message!!)
                    }
                }
            }
        }
    }

}