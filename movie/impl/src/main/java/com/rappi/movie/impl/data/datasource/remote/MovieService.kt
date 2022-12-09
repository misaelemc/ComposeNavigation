package com.rappi.movie.impl.data.datasource.remote

import com.rappi.movie.impl.data.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{category}")
    suspend fun fetchMovies(
        @Path("category") category: String,
        @Query("page") page: Int
    ): MovieListResponse
}