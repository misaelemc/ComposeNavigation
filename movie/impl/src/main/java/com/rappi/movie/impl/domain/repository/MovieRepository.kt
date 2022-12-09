package com.rappi.movie.impl.domain.repository

import com.rappi.movie.impl.data.model.MovieListResponse

interface MovieRepository {

    suspend fun fetchMovies(
        page: Int,
        category: String = CATEGORY_POPULAR
    ): MovieListResponse

    companion object {
        const val CATEGORY_POPULAR = "popular"
    }
}