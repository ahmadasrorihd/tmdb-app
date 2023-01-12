package com.ahmadasrori.tmdb.viewmodel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ahmadasrori.tmdb.data.model.MovieDetailResponse
import com.ahmadasrori.tmdb.data.model.ResultItem
import com.ahmadasrori.tmdb.data.model.ReviewResponse
import com.ahmadasrori.tmdb.data.model.TrailerVideoResponse
import com.ahmadasrori.tmdb.data.remote.ApiResponse
import com.ahmadasrori.tmdb.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    fun getTrailers(movieId: Int): LiveData<ApiResponse<TrailerVideoResponse>> {
        val result = MutableLiveData<ApiResponse<TrailerVideoResponse>>()
        viewModelScope.launch {
            mainRepository.getTrailers(movieId).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        val result = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        viewModelScope.launch {
            mainRepository.getMovieDetail(movieId).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getReviews(movieId: Int): LiveData<ApiResponse<ReviewResponse>> {
        val result = MutableLiveData<ApiResponse<ReviewResponse>>()
        viewModelScope.launch {
            mainRepository.getReviews(movieId).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getMovie(): LiveData<PagingData<ResultItem>> =
        mainRepository.getMovie().cachedIn(viewModelScope).asLiveData()

}