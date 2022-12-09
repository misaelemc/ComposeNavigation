package com.rappi.detail.impl.data.datasource.remote

import com.rappi.movie.api.data.model.MovieResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {

    @GET("movie/{movieId}")
    suspend fun fetchMovie(
        @Path("movieId") movieId: Int
    ): MovieResponseItem?
}