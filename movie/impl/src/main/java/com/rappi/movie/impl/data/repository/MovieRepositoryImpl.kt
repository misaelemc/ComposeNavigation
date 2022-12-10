package com.rappi.movie.impl.data.repository

import com.rappi.common.FeatureScope
import com.rappi.movie.api.data.model.MovieListResponse
import com.rappi.movie.impl.data.datasource.remote.MovieService
import com.rappi.movie.impl.domain.repository.MovieRepository
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import javax.inject.Inject

@Reusable
@ContributesBinding(scope = FeatureScope::class, boundType = MovieRepository::class)
class MovieRepositoryImpl @Inject constructor(private val api: MovieService): MovieRepository {

    override suspend fun fetchMovies(
        page: Int,
        category: String
    ): MovieListResponse {
        return api.fetchMovies(category, page)
    }
}