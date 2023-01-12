package com.ahmadasrori.tmdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.ahmadasrori.tmdb.data.model.*
import com.ahmadasrori.tmdb.data.remote.ApiResponse
import com.ahmadasrori.tmdb.data.remote.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class MainRepository @Inject constructor(private val dataSource: DataSource) {

    suspend fun getTrailers(movieId: Int): Flow<ApiResponse<TrailerVideoResponse>> {
        return dataSource.getTrailers(movieId)
    }

    suspend fun getReviews(movieId: Int): Flow<ApiResponse<ReviewResponse>> {
        return dataSource.getReviews(movieId)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return dataSource.getMovieDetail(movieId)
    }

    fun getMovie(): Flow<PagingData<ResultItem>> = dataSource.getMovie().flowOn(
        Dispatchers.IO)

}