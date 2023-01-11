package com.rappi.movie.impl.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.usecase.FetchMoviesUC
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel constructor(
    private val fetchMoviesUC: FetchMoviesUC
) : ViewModel() {

    val state = fetchMoviesUC.fetchMovies()
        .map {
            if (it.isEmpty()) {
                State.Loading
            } else {
                State.Success(it)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            State.Loading
        )

    fun loadMore() {
        viewModelScope.launch {
            fetchMoviesUC.load()
        }
    }

    sealed class State {
        object Empty: State()
        object Loading: State()
        data class Success(val data: List<Movie>): State()
    }
}