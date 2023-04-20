package com.rappi.movie.impl.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.viewmodel.annotations.ContributesViewModel
import com.rappi.common.FeatureScope
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.usecase.FetchMoviesUC
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesViewModel(FeatureScope::class)
class MovieViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
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