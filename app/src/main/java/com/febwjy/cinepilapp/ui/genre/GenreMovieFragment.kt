package com.febwjy.cinepilapp.ui.genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.febwjy.cinepilapp.R
import com.febwjy.cinepilapp.data.model.dto.GenreResponse
import com.febwjy.cinepilapp.databinding.FragmentMovieGenreBinding
import com.febwjy.cinepilapp.ui.BaseState
import com.febwjy.cinepilapp.ui.extension.gone
import com.febwjy.cinepilapp.ui.extension.showToast
import com.febwjy.cinepilapp.ui.extension.visible
import com.febwjy.cinepilapp.utils.Constant
import com.febwjy.cinepilapp.utils.Constant.Companion.ID_GENRE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Febby Wijaya on 22/01/24.
 */
@AndroidEntryPoint
class GenreMovieFragment: Fragment(R.layout.fragment_movie_genre) {

    private var mBinding: FragmentMovieGenreBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: GenreMovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentMovieGenreBinding.bind(view)
        setupRecycler()
        observeState()
        observeGenre()

        viewModel.getGenre(Constant.API_KEY)
    }

    private fun observeGenre() {
        viewModel.mGenre.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { genre ->
                handleProducts(genre)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleProducts(genre: List<GenreResponse.Genres>) {
        binding.genreRecyclerView.adapter?.let {
            if (it is GenreMovieAdapter) {
                it.updateList(genre)
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
                binding.genreRecyclerView.gone()
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
            binding.genreRecyclerView.gone()
        } else {
            binding.loadingProgressBar.gone()
            binding.errorLayout.gone()
            binding.genreRecyclerView.visible()
        }
    }

    private fun setupRecycler() {
        val mAdapter = GenreMovieAdapter(mutableListOf(), GenreMovieAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString(ID_GENRE, it)
            findNavController().navigate(R.id.action_goto_movielist, bundle)
        })
        binding.genreRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}