package com.avidprogrammers.parkmusic.model.data

import androidx.paging.PagingSource
import com.avidprogrammers.parkmusic.model.api.SongsSearchApi
import retrofit2.HttpException
import java.io.IOException

class SongsPagingSource(
    private val songsSearchApi: SongsSearchApi
) : PagingSource<Int, Songs>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Songs> {
        val position = params.key ?: STARTING_PAGE

        return try {
            val response = songsSearchApi.searchSongs()
            val search = response.result

            search ?: return LoadResult.Page(
                emptyList(),
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = null
            )
            LoadResult.Page(
                data = search,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private companion object {
        private const val STARTING_PAGE = 1
    }

}