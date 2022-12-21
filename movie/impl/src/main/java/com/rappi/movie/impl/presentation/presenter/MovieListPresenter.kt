package com.rappi.movie.impl.presentation.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.rappi.common.AppScope
import com.rappi.detail.api.MovieDetailScreen
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.usecase.FetchMoviesUC
import com.slack.circuit.CircuitUiEvent
import com.slack.circuit.CircuitUiState
import com.slack.circuit.Navigator
import com.slack.circuit.Presenter
import com.slack.circuit.Screen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceRetainedState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize


@Parcelize
object MovieListScreen : Screen {
    sealed interface State : CircuitUiState {
        val isRefreshing: Boolean

        object Loading : State {
            override val isRefreshing: Boolean = false
        }

        data class Success(
            val page: Int,
            val movies: List<Movie>,
            override val isRefreshing: Boolean,
            val eventSink: (MovieEvent) -> Unit
        ) : State

        data class EmptyMovies(override val isRefreshing: Boolean) : State
    }

    sealed interface MovieEvent : CircuitUiEvent {
        data class OpenMovie(val id: Int) : MovieEvent
    }
}


class MovieListPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val fetchMoviesUC: FetchMoviesUC
) : Presenter<MovieListScreen.State> {

    @Composable
    override fun present(): MovieListScreen.State {
        var isRefreshing by remember { mutableStateOf(false) }
        var page by rememberSaveable { mutableStateOf(1) }
        val moviesState by
        produceRetainedState<List<Movie>?>(
            null,
            isRefreshing
        ) {
            val movieList = fetchMoviesUC.fetchMovies(page)
            isRefreshing = false
            value = movieList
        }

        val movies = moviesState
        return when {
            movies == null -> MovieListScreen.State.Loading
            movies.isEmpty() -> MovieListScreen.State.EmptyMovies(isRefreshing = false)
            else ->
                MovieListScreen.State.Success(
                    page = page,
                    movies = movies,
                    isRefreshing = isRefreshing
                ) { movieEvent ->
                    when (movieEvent) {
                        is MovieListScreen.MovieEvent.OpenMovie -> navigator.goTo(
                            MovieDetailScreen(
                                movieEvent.id
                            )
                        )
                    }
                }
        }
    }

    @CircuitInject(MovieListScreen::class, AppScope::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): MovieListPresenter
    }
}