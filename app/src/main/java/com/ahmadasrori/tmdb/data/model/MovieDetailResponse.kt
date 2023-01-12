package com.ahmadasrori.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: String? = null,
)
