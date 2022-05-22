package com.avidprogrammers.parkmusic.model.api

import retrofit2.http.GET

interface SongsSearchApi {

    @GET("/personal/songs.json")
    suspend fun searchSongs(): SongsSearchResponse

}