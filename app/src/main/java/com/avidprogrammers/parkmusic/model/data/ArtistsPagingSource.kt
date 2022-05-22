package com.avidprogrammers.parkmusic.model.data

import androidx.paging.PagingSource
import com.avidprogrammers.parkmusic.model.api.ArtistsSearchApi
import retrofit2.HttpException
import java.io.IOException

class ArtistsPagingSource(
    private val artistsSearchApi: ArtistsSearchApi
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val position = params.key ?: STARTING_PAGE

        return try {
            val response = artistsSearchApi.searchArtists()
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