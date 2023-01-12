package com.ahmadasrori.tmdb.helper

import com.ahmadasrori.tmdb.data.model.*

object DataDummy {

    fun generateDummyMovieResponse(): List<ResultItem> {
        val items = arrayListOf<ResultItem>()
        for (i in 0 until 10) {
            val story = ResultItem(
                id = "1212332",
                overview = "some of desc",
                originalLanguage = "en",
                originalTitle = "M3GAN",
                video = false,
                title = "M3GAN",
                posterPath = "/image1.jpg",
                releaseDate = "2023-01-20",
                adult = false,
                voteCount = 1,
                page = 1
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyTrailersResponse(): TrailerVideoResponse {
        val items = arrayListOf<ResultsItemTrailer>()
        for (i in 0 until 10) {
            val story = ResultsItemTrailer(
                id = "1212332",
                site = "some of desc",
                size = 20,
                iso6391 = "M3GAN",
                iso31661 = "M3GAN",
                official = false,
                name = "M3GAN",
                type = "/image1.jpg",
                publishedAt = "2023-01-20",
                key = "samplekey"
            )
            items.add(story)
        }
        return TrailerVideoResponse(1, items)
    }

    fun generateDummyReviewsResponse(): ReviewResponse {
        val items = arrayListOf<ResultsItemReview>()
        for (i in 0 until 10) {
            val story = ResultsItemReview(
                id = "12123",
                author = "person",
                content = "sample content review",
                createdAt = "2020-01-01"
            )
            items.add(story)
        }
        return ReviewResponse(1, 1, 2, items, 2)
    }

    fun generateDummyMovieDetailResponse(): MovieDetailResponse {
        return MovieDetailResponse(
            title = "sample title",
            id = 2333,
            overview = "sample overview movie",
            posterPath = "/image1.png",
            releaseDate = "2020-01-10",
            voteAverage = "7.5",
        )
    }

    fun generatedDummyMovieId(): Int {
        return 34555
    }

}