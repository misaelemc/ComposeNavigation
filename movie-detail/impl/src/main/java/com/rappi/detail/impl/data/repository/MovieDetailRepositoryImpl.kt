package com.rappi.detail.impl.data.repository

import com.rappi.common.FeatureScope
import com.rappi.detail.impl.data.datasource.remote.MovieDetailService
import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import com.rappi.movie.api.data.model.MovieResponseItem
import com.squareup.anvil.annotations.ContributesBinding
import dagger.Reusable
import javax.inject.Inject

@Reusable
@ContributesBinding(scope = FeatureScope::class, boundType = MovieDetailRepository::class)
class MovieDetailRepositoryImpl @Inject constructor(
    private val api: MovieDetailService
) : MovieDetailRepository {

    override suspend fun fetchMovieById(movieId: Int): MovieResponseItem? {
        return api.fetchMovie(movieId)
    }
}