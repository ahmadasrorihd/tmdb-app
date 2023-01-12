package com.ahmadasrori.tmdb.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class ResultItem(

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    val overview: String,

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "original_title")
    @field:SerializedName("original_title")
    val originalTitle: String,

    @ColumnInfo(name = "video")
    @field:SerializedName("video")
    val video: Boolean,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    val title: String,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    val posterPath: String,

    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    val releaseDate: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    val adult: Boolean,

    @ColumnInfo(name = "vote_count")
    @field:SerializedName("vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "page")
    @field:SerializedName("page")
    var page: Int

)