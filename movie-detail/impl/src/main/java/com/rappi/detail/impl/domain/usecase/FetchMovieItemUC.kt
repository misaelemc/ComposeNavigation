package com.rappi.detail.impl.domain.usecase

import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import com.rappi.movie.api.data.model.toMovie
import com.rappi.movie.api.domain.model.Movie
import javax.inject.Inject

class FetchMovieItemUC @Inject constructor(private val repository: MovieDetailRepository) {

    suspend fun fetchMovieById(movieId: Int): Movie? {
        return repository.fetchMovieById(movieId)?.toMovie()
    }
}