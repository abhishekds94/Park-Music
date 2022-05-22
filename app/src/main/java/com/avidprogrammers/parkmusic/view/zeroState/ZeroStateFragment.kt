package com.avidprogrammers.parkmusic.view.zeroState

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.avidprogrammers.parkmusic.MainActivity
import com.avidprogrammers.parkmusic.R
import com.avidprogrammers.parkmusic.databinding.FragmentZeroStateBinding
import com.avidprogrammers.parkmusic.model.data.Artist
import com.avidprogrammers.parkmusic.model.data.Songs
import com.avidprogrammers.parkmusic.view.adapter.ArtistsAdapter
import com.avidprogrammers.parkmusic.view.adapter.SongsAdapter
import com.avidprogrammers.parkmusic.view.search.ItunesSearchLoadStateAdapter
import com.avidprogrammers.parkmusic.viewmodel.ZeroStateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZeroStateFragment : Fragment(R.layout.fragment_zero_state),
    ArtistsAdapter.OnItemClickListener, SongsAdapter.OnItemClickListener {

    private val viewModel by viewModels<ZeroStateViewModel>()
    private val viewModelSongs by viewModels<ZeroStateViewModel>()

    private var _binding: FragmentZeroStateBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.title = "Park Music"

        _binding = FragmentZeroStateBinding.bind(view)

        val adapterArtist = ArtistsAdapter(this)
        val adapterSongs = SongsAdapter(this)

        binding.apply {
            recyclerViewArtists.setHasFixedSize(true)
            recyclerViewArtists.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewArtists.itemAnimator = null
            recyclerViewArtists.adapter = adapterArtist.withLoadStateHeaderAndFooter(
                header = ItunesSearchLoadStateAdapter { adapterArtist.retry() },
                footer = ItunesSearchLoadStateAdapter { adapterArtist.retry() },
            )
            buttonRetry.setOnClickListener {
                adapterArtist.retry()
            }
        }

        binding.apply {
            recyclerViewSongs.setHasFixedSize(true)
            recyclerViewSongs.itemAnimator = null
            recyclerViewSongs.adapter = adapterSongs.withLoadStateHeaderAndFooter(
                header = ItunesSearchLoadStateAdapter { adapterSongs.retry() },
                footer = ItunesSearchLoadStateAdapter { adapterSongs.retry() },
            )
            buttonRetry.setOnClickListener {
                adapterSongs.retry()
            }
        }

        viewModel.artists.observe(viewLifecycleOwner) {
            adapterArtist.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.songs.observe(viewLifecycleOwner) {
            adapterSongs.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapterArtist.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewArtists.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapterArtist.itemCount < 1
                ) {
                    recyclerViewArtists.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        adapterSongs.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewSongs.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapterSongs.itemCount < 1
                ) {
                    recyclerViewSongs.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onItemClick(item: Artist) {
        toSearchFragment(item.artistName)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerViewArtists.scrollToPosition(0)
                    searchView.clearFocus()
                    toSearchFragment(query)
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

    private fun toSearchFragment(query: String) {
        findNavController().navigate(ZeroStateFragmentDirections.toSearchFragment(query))
    }

    override fun onItemClick(item: Songs) {
        toSearchFragment(item.trackName)
    }
}