package com.rappi.detail.impl.domain.usecase

import com.rappi.common.domain.model.UIState
import com.rappi.detail.impl.domain.repository.MovieDetailRepository
import com.rappi.movie.api.data.model.toMovie
import com.rappi.movie.api.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchMovieItemUC @Inject constructor(private val repository: MovieDetailRepository) {

    fun fetchMovieById(movieId: Int): Flow<MovieDetailViewState> {
        return flow {
            try {
                val movieResult = repository.fetchMovieById(movieId)
                if (movieResult != null) {
                    emit(
                        MovieDetailViewState(
                            uiState = UIState.CONTENT,
                            movie = movieResult.toMovie()
                        )
                    )
                } else {
                    emit(MovieDetailViewState(uiState = UIState.ERROR))
                }
            } catch (e: Exception) {
                emit(MovieDetailViewState(uiState = UIState.ERROR))
            }
        }.flowOn(Dispatchers.IO)
    }

    data class MovieDetailViewState(
        val uiState: UIState = UIState.IDLE,
        val movie: Movie? = null
    )
}