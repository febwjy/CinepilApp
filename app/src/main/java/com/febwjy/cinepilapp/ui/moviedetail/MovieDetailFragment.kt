package com.febwjy.cinepilapp.ui.moviedetail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.febwjy.cinepilapp.R
import com.febwjy.cinepilapp.data.model.dto.MovieReviewResponse
import com.febwjy.cinepilapp.databinding.FragmentMovieDetailBinding
import com.febwjy.cinepilapp.ui.BaseState
import com.febwjy.cinepilapp.ui.extension.gone
import com.febwjy.cinepilapp.ui.extension.showToast
import com.febwjy.cinepilapp.ui.extension.visible
import com.febwjy.cinepilapp.utils.Constant
import com.febwjy.cinepilapp.utils.Constant.Companion.ID_MOVIE
import com.febwjy.cinepilapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Febby Wijaya on 22/01/24.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var mBinding: FragmentMovieDetailBinding? =null
    private val binding get() = mBinding!!
    private lateinit var idMovie: String

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentMovieDetailBinding.bind(view)
        setupRecycler()
        observeState()
        observeMovieDetail()
        observeMovieReview()

        arguments?.let {
            if (requireArguments().getString(ID_MOVIE)?.isNotEmpty() == true) {
                Log.i("id", requireArguments().getString(ID_MOVIE).toString())
                idMovie = requireArguments().getString(ID_MOVIE).toString()
            }
        }

        viewModel.getMovieDetail(Constant.API_KEY, idMovie.toInt())
        viewModel.getMovieReview(Constant.API_KEY, idMovie.toInt())
    }

    @SuppressLint("SetTextI18n")
    private fun observeMovieDetail() {
        viewModel.mMovieDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data?.let {

                        val imgPath = Constant.IMG_URL + it.posterPath
                        val uri: Uri = Uri.parse(imgPath)
                        Glide.with(this).load(uri)
                            .into(binding.imgPoster)
                        (activity as AppCompatActivity).supportActionBar?.title = it.title
                        binding.txtSysnopsis.text = it.overview
                        binding.txtDuration.text = it.runtime.toString() + " Minute"
                        binding.txtLanguage.text = viewModel.getLanguages(it)
                        binding.txtGenre.text = viewModel.getGenres(it)
                    }
                }

                else -> {}
            }
        }
    }

    private fun observeMovieReview() {
        viewModel.mMoviewReview.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { movieReview ->
                handleProducts(movieReview)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleProducts(movieReview: List<MovieReviewResponse.Result>) {
        binding.movieReviewRecyclerview.adapter?.let {
            if (it is MovieReviewAdapter) {
                it.updateList(movieReview)
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
                binding.relContent.gone()
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
            binding.relContent.gone()
        } else {
            binding.loadingProgressBar.gone()
            binding.errorLayout.gone()
            binding.relContent.visible()
        }
    }

    private fun setupRecycler() {
        val mAdapter = MovieReviewAdapter(mutableListOf())
        binding.movieReviewRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}