package com.avidprogrammers.parkmusic.model.data

import androidx.paging.PagingSource
import com.avidprogrammers.parkmusic.model.api.ItunesSearchApi
import retrofit2.HttpException
import java.io.IOException

private const val ITUNES_STARTING_PAGE_INDEX = 1

class ItunesPagingSource(
    private val itunesSearchApi: ItunesSearchApi,
    private val query: String
): PagingSource<Int, ItunesSearch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItunesSearch> {
        val position = params.key ?: ITUNES_STARTING_PAGE_INDEX

        return try {
            val response = itunesSearchApi.searchResults(query, position, params.loadSize)
            val search = response.results

            LoadResult.Page(
                data = search,
                prevKey = if (position == ITUNES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (search.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

}