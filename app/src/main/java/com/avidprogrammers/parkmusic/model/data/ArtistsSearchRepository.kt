package com.avidprogrammers.parkmusic.model.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.avidprogrammers.parkmusic.model.api.ArtistsSearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistsSearchRepository @Inject constructor(
    private val artistsSearchApi: ArtistsSearchApi
) {

    fun getArtists() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArtistsPagingSource(artistsSearchApi) }
        )

}