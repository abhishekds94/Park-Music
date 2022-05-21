package com.avidprogrammers.parkmusic.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.avidprogrammers.parkmusic.model.data.ItunesSearchRepository

class ItunesSearchViewModel @ViewModelInject constructor(
    private val repository: ItunesSearchRepository
): ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val search = currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchResults(query: String){
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }

}