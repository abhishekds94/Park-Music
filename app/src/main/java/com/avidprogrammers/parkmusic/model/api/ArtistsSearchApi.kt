package com.avidprogrammers.parkmusic.model.api

import retrofit2.http.GET

interface ArtistsSearchApi {

    @GET("/personal/artists.json")
    suspend fun searchArtists(): ArtistsSearchResponse

}