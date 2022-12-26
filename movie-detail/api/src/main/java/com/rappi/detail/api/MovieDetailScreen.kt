package com.rappi.detail.api

import com.rappi.movie.api.domain.model.Movie
import com.slack.circuit.CircuitUiEvent
import com.slack.circuit.CircuitUiState
import com.slack.circuit.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailScreen(val movieId: Int) : Screen {
    sealed interface State : CircuitUiState {

        val eventSink: (MovieEvent) -> Unit

        data class Loading(override val eventSink: (MovieEvent) -> Unit) : State

        data class Success(
            val movieDetail: MovieDetail,
            override val eventSink: (MovieEvent) -> Unit,
            val successEventSink: (SuccessMovieEvent) -> Unit
        ) : State {
            sealed class SuccessMovieEvent() {
                data class OpenRecommended(val movieId: Int) : SuccessMovieEvent()
            }
        }

        data class EmptyMovie(override val eventSink: (MovieEvent) -> Unit) : State
    }

    sealed interface MovieEvent : CircuitUiEvent {
        data class OpenReview(val id: Int) : MovieEvent
        object OnBackPressed : MovieEvent
    }
}

data class MovieDetail(val movie: Movie, val recommended: List<Movie>)
