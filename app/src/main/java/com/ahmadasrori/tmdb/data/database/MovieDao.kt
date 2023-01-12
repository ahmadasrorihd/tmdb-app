package com.ahmadasrori.tmdb.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmadasrori.tmdb.data.model.ResultItem

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(quote: List<ResultItem>)

    @Query("SELECT * FROM movie")
    fun getAllMovie(): PagingSource<Int, ResultItem>

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}