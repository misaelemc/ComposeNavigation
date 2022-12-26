package com.rappi.detail.impl.presentation.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.rappi.common.AppScope
import com.rappi.detail.impl.domain.model.Review
import com.rappi.detail.impl.domain.usecase.FetchReviewsByIdUC
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
class ReviewsScreen(val movieId: Int) : Screen {

    sealed interface ReviewsState : CircuitUiState {
        val eventSink: (ReviewsScreen.Event) -> Unit

        data class Loading(override val eventSink: (ReviewsScreen.Event) -> Unit) :
            ReviewsState

        data class Success(
            val reviews: List<Review>,
            override val eventSink: (ReviewsScreen.Event) -> Unit
        ) : ReviewsState

        data class NoReviews(override val eventSink: (ReviewsScreen.Event) -> Unit) :
            ReviewsState
    }

    sealed class Event {
        object OnBackPressed : Event()
    }

}

class ReviewsPresenter @AssistedInject constructor(
    @Assisted private val reviewsScreen: ReviewsScreen,
    @Assisted private val navigator: Navigator,
    private val fetchReviewsByIdUC: FetchReviewsByIdUC
) : Presenter<ReviewsScreen.ReviewsState> {

    @Composable
    override fun present(): ReviewsScreen.ReviewsState {
        val eventSink: (ReviewsScreen.Event) -> Unit = { event ->
            when (event) {
                ReviewsScreen.Event.OnBackPressed -> navigator.pop()
            }
        }

        val state by produceRetainedState<ReviewsScreen.ReviewsState>(
            initialValue = ReviewsScreen.ReviewsState.Loading(
                eventSink
            )
        ) {
            val reviews = fetchReviewsByIdUC.invoke(reviewsScreen.movieId)

            value = when (reviews) {
                null -> ReviewsScreen.ReviewsState.NoReviews(eventSink)
                else -> {
                    ReviewsScreen.ReviewsState.Success(reviews, eventSink)
                }
            }
        }

        return state
    }

    @CircuitInject(ReviewsScreen::class, AppScope::class)
    @AssistedFactory
    interface Factory {
        fun create(reviewsScreen: ReviewsScreen, navigator: Navigator): ReviewsPresenter
    }
}
