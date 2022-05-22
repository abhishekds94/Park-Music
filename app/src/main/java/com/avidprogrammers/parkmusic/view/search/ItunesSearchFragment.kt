package com.avidprogrammers.parkmusic.view.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.avidprogrammers.parkmusic.MainActivity
import com.avidprogrammers.parkmusic.R
import com.avidprogrammers.parkmusic.databinding.FragmentSearchBinding
import com.avidprogrammers.parkmusic.model.data.ItunesSearch
import com.avidprogrammers.parkmusic.view.adapter.ItunesSearchAdapter
import com.avidprogrammers.parkmusic.viewmodel.ItunesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItunesSearchFragment: Fragment(R.layout.fragment_search), ItunesSearchAdapter.OnItemClickListener {

    private val viewModel by viewModels<ItunesSearchViewModel>()
    private val args = navArgs<ItunesSearchFragmentArgs>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (requireActivity() as MainActivity).supportActionBar?.title = "Park Music"

        _binding = FragmentSearchBinding.bind(view)

        val adapter = ItunesSearchAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ItunesSearchLoadStateAdapter { adapter.retry() },
                footer = ItunesSearchLoadStateAdapter { adapter.retry() },
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.search.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)

        viewModel.searchResults(args.value.query)
    }

    override fun onItemClick(search: ItunesSearch) {
        val action = ItunesSearchFragmentDirections.actionSearchFragmentToDetailsFragment(search)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchResults(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}