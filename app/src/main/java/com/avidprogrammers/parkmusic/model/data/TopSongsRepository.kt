package com.avidprogrammers.parkmusic.model.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.avidprogrammers.parkmusic.model.api.ItunesSearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopSongsRepository @Inject constructor(private val searchApi: ItunesSearchApi) {

    fun getTopSongsResults() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopSongsPagingSource (searchApi) }
        )

}