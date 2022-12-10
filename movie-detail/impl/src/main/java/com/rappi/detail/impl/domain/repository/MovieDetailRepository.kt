package com.rappi.detail.impl.domain.repository

import com.rappi.detail.impl.data.model.ReviewsResponse
import com.rappi.movie.api.data.model.MovieListResponse
import com.rappi.movie.api.data.model.MovieResponseItem

interface MovieDetailRepository {

    suspend fun fetchMovieById(movieId: Int): MovieResponseItem?

    suspend fun fetchReviewsById(movieId: Int): ReviewsResponse?

    suspend fun fetchRecommendedMoviesById(movieId: Int): MovieListResponse?
}