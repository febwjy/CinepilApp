package com.febwjy.cinepilapp.ui.movielist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.febwjy.cinepilapp.R
import com.febwjy.cinepilapp.data.model.dto.MovieListResponse
import com.febwjy.cinepilapp.databinding.FragmentMovieListBinding
import com.febwjy.cinepilapp.ui.BaseState
import com.febwjy.cinepilapp.ui.extension.gone
import com.febwjy.cinepilapp.ui.extension.showToast
import com.febwjy.cinepilapp.ui.extension.visible
import com.febwjy.cinepilapp.utils.Constant
import com.febwjy.cinepilapp.utils.Constant.Companion.ID_GENRE
import com.febwjy.cinepilapp.utils.Constant.Companion.ID_MOVIE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Febby Wijaya on 16/09/23.
 */
@AndroidEntryPoint
class MovieListFragment(): Fragment(R.layout.fragment_movie_list) {

    private var mBinding: FragmentMovieListBinding? = null
    private val binding get() = mBinding!!
    private var page: Int = 1
    private lateinit var idGenre: String

    private val viewModel: MovieListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentMovieListBinding.bind(view)
        setupRecycler()
        observeState()
        observeMovieList()

        arguments?.let {
            if (requireArguments().getString(ID_GENRE)?.isNotEmpty() == true) {
                idGenre = requireArguments().getString(ID_GENRE).toString()
            }
        }

        binding.movieListRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val canLoad = viewModel.loadMore.value
                    if (!canLoad){
                        Log.d("LoadMore", "onScrollStateChanged: ${viewModel.loadMore.value}")
                    } else {
                        viewModel.getMovieList(Constant.API_KEY, idGenre, page)
                        Log.d("LoadMore", "onScrollStateChanged: ${viewModel.loadMore.value}")
                    }
                    page++;
                }
            }
        })

        viewModel.getMovieList(Constant.API_KEY, idGenre, page)
    }

    private fun observeMovieList() {
        viewModel.mMovieList.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { movieList ->
                handleProducts(movieList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleProducts(movieList: List<MovieListResponse.MovieList>) {
        binding.movieListRecyclerview.adapter?.let {
            if (it is MovieListAdapter) {
                it.updateList(movieList)
            }
        }
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleState(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: BaseState) {
        when (state) {
            is BaseState.IsLoading -> handleLoading(state.isLoading)
            is BaseState.ShowToast -> {
                binding.loadingProgressBar.gone()
                binding.movieListRecyclerview.gone()
                binding.errorLayout.visible()
                requireActivity().showToast(state.message)
            }

            is BaseState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingProgressBar.visible()
            binding.errorLayout.gone()
            binding.movieListRecyclerview.gone()
        } else {
            binding.loadingProgressBar.gone()
            binding.errorLayout.gone()
            binding.movieListRecyclerview.visible()
        }
    }

    private fun setupRecycler() {
        val mAdapter = MovieListAdapter(mutableListOf(), MovieListAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString(ID_MOVIE, it)
            findNavController().navigate(R.id.action_goto_moviedetail, bundle)

        })
        binding.movieListRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroy() {
        super.onDestroyView()
        mBinding = null
    }

}