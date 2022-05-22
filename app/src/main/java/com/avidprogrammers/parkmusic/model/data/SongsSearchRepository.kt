package com.avidprogrammers.parkmusic.model.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.avidprogrammers.parkmusic.model.api.SongsSearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongsSearchRepository @Inject constructor(
    private val songsSearchApi: SongsSearchApi
) {

    fun getSongs() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SongsPagingSource (songsSearchApi) }
        )

}