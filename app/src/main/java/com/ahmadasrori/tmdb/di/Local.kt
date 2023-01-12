package com.ahmadasrori.tmdb.di

import android.content.Context
import androidx.room.Room
import com.ahmadasrori.tmdb.data.database.MovieDao
import com.ahmadasrori.tmdb.data.database.RemoteKeysDao
import com.ahmadasrori.tmdb.data.database.StoryDatabase
import com.ahmadasrori.tmdb.helper.Constant.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Local {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StoryDatabase {
        return Room.databaseBuilder(context, StoryDatabase::class.java, DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(database: StoryDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideRemoteKeyDao(database: StoryDatabase): RemoteKeysDao = database.remoteKeysDao()

}