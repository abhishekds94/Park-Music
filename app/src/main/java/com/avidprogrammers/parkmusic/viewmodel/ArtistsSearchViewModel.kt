package com.avidprogrammers.parkmusic.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.avidprogrammers.parkmusic.model.data.Artist
import com.avidprogrammers.parkmusic.model.data.ArtistsSearchRepository

class ArtistsSearchViewModel @ViewModelInject constructor(
    private val repository: ArtistsSearchRepository
): ViewModel() {

    val artists: LiveData<PagingData<Artist>> by lazy {
        repository.getArtists().liveData.cachedIn(viewModelScope)
    }

}