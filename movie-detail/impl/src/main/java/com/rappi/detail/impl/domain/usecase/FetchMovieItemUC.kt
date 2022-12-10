package com.rappi.detail.impl.domain.usecase

import com.rappi.common.domain.model.UIState
import com.rappi.common.domain.model.UIStateResponse
import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import com.rappi.movie.api.data.model.MovieResponseItem
import com.rappi.movie.api.data.model.toMovie
import com.rappi.movie.api.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchMovieItemUC @Inject constructor(private val repository: MovieDetailRepository) {

    fun fetchMovieById(movieId: Int): Flow<UIStateResponse<MovieDetail>> {
        return combine(
            getMovieById(movieId),
            getRecommendationsById(movieId)
        ) { item: MovieResponseItem?, recommendations: List<MovieResponseItem> ->
            if (item != null) {
                UIStateResponse(
                    state = UIState.CONTENT,
                    data = MovieDetail(
                        movie = item.toMovie(),
                        recommended = recommendations.map { it.toMovie() }
                    )
                )
            } else {
                UIStateResponse(state = UIState.ERROR)
            }
        }.catch {
            it.printStackTrace()
            emit(UIStateResponse(state = UIState.ERROR))
        }.flowOn(Dispatchers.IO)
    }

     private fun getMovieById(movieId: Int) : Flow<MovieResponseItem?> {
        return flow {
            try {
                val response = repository.fetchMovieById(movieId)
                emit(response)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(null)
            }
        }
    }

    private fun getRecommendationsById(movieId: Int) : Flow<List<MovieResponseItem>> {
        return flow {
            try {
                val response = repository.fetchRecommendedMoviesById(movieId)
                if (!response?.results.isNullOrEmpty()) {
                    emit(response!!.results)
                } else {
                    emit(emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(emptyList())
            }
        }
    }

    data class MovieDetail(val movie: Movie, val recommended: List<Movie>)
}