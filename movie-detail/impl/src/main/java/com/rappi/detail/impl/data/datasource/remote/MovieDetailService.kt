package com.rappi.detail.impl.data.datasource.remote

import com.rappi.detail.impl.data.model.ReviewsResponse
import com.rappi.movie.api.data.model.MovieListResponse
import com.rappi.movie.api.data.model.MovieResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {

    @GET("movie/{movieId}")
    suspend fun fetchMovie(
        @Path("movieId") movieId: Int
    ): MovieResponseItem?

    @GET("movie/{movieId}/recommendations")
    suspend fun fetchRecommendedMovies(
        @Path("movieId") movieId: Int
    ): MovieListResponse?

    @GET("movie/{movieId}/reviews")
    suspend fun fetchReviews(
        @Path("movieId") movieId: Int
    ): ReviewsResponse?
}