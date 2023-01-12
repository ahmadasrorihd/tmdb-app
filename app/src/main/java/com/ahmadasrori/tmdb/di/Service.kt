package com.ahmadasrori.tmdb.di

import com.ahmadasrori.tmdb.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class Service {

    @Provides
    fun provideAuthService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}