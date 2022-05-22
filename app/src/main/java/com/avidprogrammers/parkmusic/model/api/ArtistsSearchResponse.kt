package com.avidprogrammers.parkmusic.model.api

import com.avidprogrammers.parkmusic.model.data.Artist

data class ArtistsSearchResponse(
    val result: List<Artist>?
)