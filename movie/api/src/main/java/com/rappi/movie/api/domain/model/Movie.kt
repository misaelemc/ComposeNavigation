package com.rappi.movie.api.domain.model

import com.rappi.movie.api.data.model.MovieResponseItem

data class Movie(
    val id: Int,
    val posterUrl: String?,
    val backdropUrl: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Float,
    val voteCount: Int,
    val title: String
)

fun Movie.toMovieResponseItem(): MovieResponseItem {
    return MovieResponseItem(
        id = this.id,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        title = this.title
    )
}