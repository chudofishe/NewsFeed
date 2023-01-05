package com.chudofishe.newsfeed.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.chudofishe.newsfeed.databinding.NewsFeedFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {
    private var _binding: NewsFeedFragmentBinding? = null
    private val binding: NewsFeedFragmentBinding
        get() = _binding!!

    private val viewModel: NewsFeedViewModel by viewModels()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsFeedAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewsFeedFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            itemsList.adapter = adapter
        }

        adapter.addLoadStateListener { state ->
            with(binding) {
                itemsList.isVisible = state.refresh != LoadState.Loading
                loading.isVisible = state.refresh == LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.news.collectLatest(adapter::submitData)
            }
        }
    }
}