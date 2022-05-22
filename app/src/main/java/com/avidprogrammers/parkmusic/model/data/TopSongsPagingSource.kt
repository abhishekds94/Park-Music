package com.avidprogrammers.parkmusic.model.data

import androidx.paging.PagingSource
import com.avidprogrammers.parkmusic.model.api.ItunesSearchApi
import dagger.Provides
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopSongsPagingSource @Inject constructor(
    private val itunesSearchApi: ItunesSearchApi
): PagingSource<Int, ItunesSearch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItunesSearch> {

        return try {
            val response = itunesSearchApi?.topSongsResults()
            val search = response?.results!!

            LoadResult.Page(
                data = search,
                prevKey = -1,
                nextKey = 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

}