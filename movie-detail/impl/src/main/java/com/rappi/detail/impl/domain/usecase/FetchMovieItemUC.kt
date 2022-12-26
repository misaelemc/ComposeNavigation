package com.rappi.detail.impl.domain.usecase

import com.rappi.detail.api.MovieDetail
import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import com.rappi.movie.api.data.model.MovieResponseItem
import com.rappi.movie.api.data.model.toMovie
import javax.inject.Inject

class FetchMovieItemUC @Inject constructor(private val repository: MovieDetailRepository) {

    suspend fun fetchMovieById(movieId: Int): MovieDetail? {
        val movieResponseItem = getMovieById(movieId)
        val recommendationsById = getRecommendationsById(movieId)
        return if (movieResponseItem != null) {
            MovieDetail(
                movieResponseItem.toMovie(),
                recommendationsById.map { it.toMovie() }
            )
        } else null
    }

    private suspend fun getMovieById(movieId: Int): MovieResponseItem? {
        return try {
            repository.fetchMovieById(movieId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    private suspend fun getRecommendationsById(movieId: Int): List<MovieResponseItem> {
        return try {
            val response = repository.fetchRecommendedMoviesById(movieId)
            if (!response?.results.isNullOrEmpty()) {
                response!!.results
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}