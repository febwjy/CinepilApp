package com.febwjy.cinepilapp.ui.popular

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
import com.febwjy.cinepilapp.data.model.dto.PopularListResponse
import com.febwjy.cinepilapp.databinding.FragmentPopularListBinding
import com.febwjy.cinepilapp.ui.BaseState
import com.febwjy.cinepilapp.ui.extension.gone
import com.febwjy.cinepilapp.ui.extension.showToast
import com.febwjy.cinepilapp.ui.extension.visible
import com.febwjy.cinepilapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Febby Wijaya on 22/01/2024.
 */
@AndroidEntryPoint
class PopularListFragment : Fragment(R.layout.fragment_popular_list) {

    private var mBinding: FragmentPopularListBinding? = null
    private val binding get() = mBinding!!

    private val viewModel: PopularListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPopularListBinding.bind(view)
        setupRecycler()
        observeState()
        observePopularList()
        viewModel.getPopularList()
    }

    private fun observePopularList() {
        viewModel.mPopularList.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { popularList ->
                handlePopular(popularList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handlePopular(popularList: List<PopularListResponse.Result>) {
        binding.movieListRecyclerview.adapter?.let {
            if (it is PopularListAdapter) {
                it.updateList(popularList)
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
        val mAdapter = PopularListAdapter(mutableListOf(), PopularListAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putString(Constant.ID_MOVIE, it)
            findNavController().navigate(R.id.action_goto_moviedetail, bundle)

        })
        binding.movieListRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        super.onDestroyView()
        mBinding = null
    }

}