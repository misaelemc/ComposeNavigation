package com.rappi.detail.impl.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.common.domain.model.UIState
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import com.rappi.movie.api.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel constructor(
    private val fetchMovieItemUC: FetchMovieItemUC
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(MovieDetailViewState())
    val viewStateFlow: StateFlow<MovieDetailViewState> = _viewStateFlow

    fun fetchMovie(movieId: Int) {
        viewModelScope.launch {
            _viewStateFlow.value = viewStateFlow.value.copy(uiState = UIState.LOADING)
            val movieResult = fetchMovieItemUC.fetchMovieById(movieId)
            if (movieResult != null) {
                _viewStateFlow.value = MovieDetailViewState(
                    uiState = UIState.CONTENT,
                    movie = movieResult
                )
            } else {
                _viewStateFlow.value = MovieDetailViewState(uiState = UIState.ERROR)
            }
        }
    }

    data class MovieDetailViewState(
        val uiState: UIState = UIState.IDLE,
        val movie: Movie? = null
    )
}