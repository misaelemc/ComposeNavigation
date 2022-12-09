package com.rappi.movie.api.data.model

import com.google.gson.annotations.SerializedName
import com.rappi.movie.api.domain.model.Movie

data class MovieResponseItem(
    val id: Int,
    @SerializedName("poster_path") val posterUrl: String?,
    @SerializedName("backdrop_path") val backdropUrl: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("title") val title: String,
    @SerializedName("video") val hasVideo: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("imdb_id") val imdbId: String?
)

fun MovieResponseItem.toMovie(): Movie {
    return Movie(
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