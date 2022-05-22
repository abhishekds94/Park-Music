package com.avidprogrammers.parkmusic.model.api

import com.avidprogrammers.parkmusic.model.data.Songs

data class SongsSearchResponse(
    val result: List<Songs>?
)