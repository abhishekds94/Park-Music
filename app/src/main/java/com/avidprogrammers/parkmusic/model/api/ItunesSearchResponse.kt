package com.avidprogrammers.parkmusic.model.api

import com.avidprogrammers.parkmusic.model.data.ItunesSearch

data class ItunesSearchResponse(
    val results: List<ItunesSearch>
)