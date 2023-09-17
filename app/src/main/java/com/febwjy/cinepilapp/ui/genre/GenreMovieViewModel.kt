package com.febwjy.cinepilapp.ui.genre

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.domain.usecase.GetGenreMovieUseCase
import com.febwjy.cinepilapp.ui.MainViewModel
import com.febwjy.cinepilapp.utils.NetworkListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 16/09/23.
 */
@HiltViewModel
class GenreMovieViewModel @Inject constructor(
    private val getGenreMovieUseCase: GetGenreMovieUseCase, application: Application
) : MainViewModel(application) {

    private val genre = MutableStateFlow<List<GenreResponse.Genres>>(mutableListOf())
    val mGenre: StateFlow<List<GenreResponse.Genres>> get() = genre

    fun getGenre(api_key: String){
        viewModelScope.launch {
            getGenreMovieUseCase.invoke(api_key).onStart {
                showLoading()
            }.catch { exception ->
                dismissLoading()
                Log.e("Error", exception.message.toString())
            }.collect { result ->
                when (result) {
                    is NetworkListResult.Success -> {
                        dismissLoading()
                        genre.value = result.data
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