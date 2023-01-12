package com.ahmadasrori.tmdb.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahmadasrori.tmdb.data.MovieMediator
import com.ahmadasrori.tmdb.data.database.StoryDatabase
import com.ahmadasrori.tmdb.data.model.MovieDetailResponse
import com.ahmadasrori.tmdb.data.model.ResultItem
import com.ahmadasrori.tmdb.data.model.ReviewResponse
import com.ahmadasrori.tmdb.data.model.TrailerVideoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class DataSource @Inject constructor(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
    ) {

    fun getMovie(): Flow<PagingData<ResultItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            remoteMediator = MovieMediator(storyDatabase, apiService),
            pagingSourceFactory = { storyDatabase.movieDao().getAllMovie() }
        ).flow
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getMovieDetail(movieId)
                if (response.id != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.toString()))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun getReviews(movieId: Int): Flow<ApiResponse<ReviewResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getReviews(movieId)
                if (response.id != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.toString()))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun getTrailers(movieId: Int): Flow<ApiResponse<TrailerVideoResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getTrailers(movieId)
                if (response.id != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.toString()))
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.message.toString()))
            }
        }
    }

}