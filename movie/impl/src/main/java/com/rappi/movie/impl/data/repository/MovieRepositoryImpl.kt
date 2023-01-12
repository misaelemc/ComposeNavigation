package com.rappi.movie.impl.data.repository

import com.rappi.common.FeatureScope
import com.rappi.common.paging.Pageable
import com.rappi.movie.api.data.model.MovieResponseItem
import com.rappi.movie.impl.data.datasource.remote.MovieService
import com.rappi.movie.impl.domain.repository.MovieRepository
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import javax.inject.Inject

@Reusable
@ContributesBinding(scope = FeatureScope::class, boundType = MovieRepository::class)
class MovieRepositoryImpl @Inject constructor(private val api: MovieService) : MovieRepository {

    override fun getMoviesPageable(): Pageable<MovieResponseItem> {
        return object : Pageable<MovieResponseItem>() {

            override suspend fun load() {
                if (hasMorePages) {
                    val response = api.fetchMovies(page)
                    if (response.results.isEmpty()) {
                        emit(State.Page(emptyList(), page))
                    } else {
                        val nextPage = if (page < response.totalPages) page++ else null
                        emit(State.Page(response.results, page, nextPage))
                    }
                } else {
                    emit(State.Page(emptyList(), page))
                }
            }
        }
    }
}