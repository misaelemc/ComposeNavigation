package com.rappi.movie.api.domain.model

import com.rappi.movie.api.data.model.MovieResponseItem

data class Movie(
    val id: Int,
    val posterUrl: String?,
    val backdropUrl: String?,
    val overview: String?,
    val releaseDate: String?,
    val hasVideo: Boolean,
    val voteAverage: Float,
    val voteCount: Int,
    val imdbId: String?,
    val title: String
)

fun Movie.toMovieResponseItem(): MovieResponseItem {
    return MovieResponseItem(
        id = this.id,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl,
        overview = this.overview,
        releaseDate = this.releaseDate,
        hasVideo = this.hasVideo,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        imdbId = this.imdbId,
        title = this.title
    )
}