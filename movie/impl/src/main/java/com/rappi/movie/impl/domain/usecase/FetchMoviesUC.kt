package com.rappi.movie.impl.domain.usecase

import com.rappi.common.paging.Pageable
import com.rappi.movie.api.data.model.MovieResponseItem
import com.rappi.movie.api.data.model.toMovie
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchMoviesUC @Inject constructor(private val repository: MovieRepository) {

    private lateinit var pageable: Pageable<MovieResponseItem>

    fun fetchMovies(): Flow<List<Movie>> {
        pageable = repository.getMoviesPageable()
        return pageable.data.map {
            when (it) {
                is Pageable.State.Page -> it.data.map { items -> items.toMovie() }
                else -> emptyList()
            }
        }
    }

    suspend fun load() {
        withContext(Dispatchers.IO) { pageable.load() }
    }
}