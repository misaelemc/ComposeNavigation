package com.rappi.detail.impl.presentation.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.rappi.common.AppScope
import com.rappi.detail.api.MovieDetailScreen
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import com.slack.circuit.Navigator
import com.slack.circuit.Presenter
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceRetainedState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.assisted.AssistedFactory

class MovieDetailPresenter @AssistedInject constructor(
    @Assisted private val movieDetailScreen: MovieDetailScreen,
    @Assisted private val navigator: Navigator,
    private val fetchMovieItemUC: FetchMovieItemUC
) : Presenter<MovieDetailScreen.State> {

    @Composable
    override fun present(): MovieDetailScreen.State {
        val eventSink: (MovieDetailScreen.MovieEvent) -> Unit = { event ->
            when (event) {
                MovieDetailScreen.MovieEvent.OnBackPressed -> navigator.pop()
                is MovieDetailScreen.MovieEvent.OpenReview -> navigator.goTo(
                    ReviewsScreen(
                        movieDetailScreen.movieId
                    )
                )
            }
        }

        val state by
        produceRetainedState<MovieDetailScreen.State>(MovieDetailScreen.State.Loading(eventSink)) {
            val detail = fetchMovieItemUC.fetchMovieById(movieDetailScreen.movieId)
            value = when (detail) {
                null -> MovieDetailScreen.State.EmptyMovie(eventSink)
                else -> {
                    MovieDetailScreen.State.Success(detail, eventSink) { event ->
                        when (event) {
                            is MovieDetailScreen.State.Success.SuccessMovieEvent.OpenRecommended -> navigator.goTo(
                                MovieDetailScreen(event.movieId)
                            )
                        }
                    }
                }
            }
        }
        return state
    }

    @CircuitInject(MovieDetailScreen::class, AppScope::class)
    @AssistedFactory
    interface Factory {
        fun create(movieDetailScreen: MovieDetailScreen, navigator: Navigator): MovieDetailPresenter
    }

}