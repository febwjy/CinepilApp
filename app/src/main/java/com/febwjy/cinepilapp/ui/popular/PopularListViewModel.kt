package com.febwjy.cinepilapp.ui.popular

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import com.febwjy.cinepilapp.domain.usecase.GetPopularUseCase
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
 * Created by Febby Wijaya on 22/01/2024.
 */

@HiltViewModel
class PopularListViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase, application: Application
) : MainViewModel(application){

    private val popularList = MutableStateFlow<List<PopularListResponse.Result>>(mutableListOf())
    val mPopularList: StateFlow<List<PopularListResponse.Result>> get() = popularList

    fun getPopularList() {
        viewModelScope.launch {
            getPopularUseCase.invoke().onStart {
                showLoading()
            }.catch {exception ->
                dismissLoading()
                Log.e("Error", exception.message.toString())
            }.collect { result ->
                when (result) {
                    is NetworkListResult.Success -> {
                        dismissLoading()
                        popularList.value = result.data
                    }
                    is NetworkListResult.Error -> {
                        dismissLoading()
                        Log.e("result", result.rawResponse.statusMessage!!)
                    }
                }
            }
        }
    }

}