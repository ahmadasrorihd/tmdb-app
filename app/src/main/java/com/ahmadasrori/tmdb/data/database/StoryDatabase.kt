package com.ahmadasrori.tmdb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmadasrori.tmdb.data.model.RemoteKeys
import com.ahmadasrori.tmdb.data.model.ResultItem

@Database(
    entities =[ResultItem::class, RemoteKeys::class],
    version = 14,
    exportSchema = false
)
abstract class StoryDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeysDao(): RemoteKeysDao

}