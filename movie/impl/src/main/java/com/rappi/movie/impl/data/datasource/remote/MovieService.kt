package com.rappi.movie.impl.data.datasource.remote

import com.rappi.movie.api.data.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun fetchMovies(
        @Query("page") page: Int
    ): MovieListResponse
}