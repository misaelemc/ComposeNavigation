package com.rappi.movie.impl.domain.usecase

import com.rappi.movie.api.data.model.toMovie
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.repository.MovieRepository
import javax.inject.Inject

class FetchMoviesUC @Inject constructor(private val repository: MovieRepository) {

    suspend fun fetchMovies(page: Int): List<Movie> {
        return try {
            repository.fetchMovies(page).results.map { it.toMovie() }
        } catch (e: Exception) {
            listOf()
        }
    }
}