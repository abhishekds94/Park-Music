package com.avidprogrammers.parkmusic.model.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesSearchApi {

    @GET("search?")
    suspend fun searchResults(
        @Query("term") term: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ItunesSearchResponse

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

}