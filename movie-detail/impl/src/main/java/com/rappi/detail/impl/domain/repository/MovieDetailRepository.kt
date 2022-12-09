package com.rappi.detail.impl.domain.repository

import com.rappi.movie.api.data.model.MovieResponseItem

interface MovieDetailRepository {

    suspend fun fetchMovieById(movieId: Int): MovieResponseItem?
}