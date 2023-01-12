package com.ahmadasrori.tmdb.data.remote

import com.ahmadasrori.tmdb.BuildConfig.APIKEY
import com.ahmadasrori.tmdb.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("movie/popular?language=en-US&api_key=${APIKEY}")
    suspend fun getMovie(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}?language=en-US&api_key=${APIKEY}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie: Int
    ): MovieDetailResponse

    @GET("movie/{movie_id}/reviews?language=en-US&api_key=${APIKEY}")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int
    ): ReviewResponse

    @GET("movie/{movie_id}/videos?language=en-US&api_key=${APIKEY}")
    suspend fun getTrailers(
        @Path("movie_id") movieId: Int?
    ): TrailerVideoResponse
}