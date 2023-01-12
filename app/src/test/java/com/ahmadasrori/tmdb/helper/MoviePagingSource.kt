package com.ahmadasrori.tmdb.helper

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahmadasrori.tmdb.data.model.ResultItem

class MoviePagingSource :
    PagingSource<Int, LiveData<List<ResultItem>>>() {

    companion object {
        fun snapshot(items: List<ResultItem>): PagingData<ResultItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ResultItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ResultItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

}