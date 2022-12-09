package com.rappi.movie.impl.data.model

import com.rappi.movie.api.data.model.MovieResponseItem
import com.rappi.movie.api.data.model.toMovie
import com.rappi.movie.api.domain.model.Movie

data class MovieListResponse(val results: List<MovieResponseItem>)

fun MovieListResponse.toMovieList(): List<Movie> {
    return this.results.map(MovieResponseItem::toMovie)
}
