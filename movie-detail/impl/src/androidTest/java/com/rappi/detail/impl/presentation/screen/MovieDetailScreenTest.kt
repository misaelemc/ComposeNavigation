package com.rappi.detail.impl.presentation.screen

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.rappi.common.domain.model.UIState
import com.rappi.common.domain.model.UIStateResponse
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.detail.impl.common.BaseAndroidComposeTest
import com.rappi.detail.impl.common.BaseRobot
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import com.rappi.detail.impl.domain.usecase.MovieDetail
import com.rappi.detail.impl.presentation.viewModel.MovieDetailViewModel
import com.rappi.movie.api.domain.model.Movie
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Test

class MovieDetailScreenTest : BaseAndroidComposeTest<UIStateResponse<MovieDetail>>() {

    @MockK
    lateinit var fetchMovieItemUC: FetchMovieItemUC

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    @MockK(relaxed = true)
    lateinit var onBackPressed: () -> Unit

    @MockK(relaxed = true)
    lateinit var onReviewsClicked: () -> Unit

    private lateinit var viewModel: MovieDetailViewModel

    override val config: Config<UIStateResponse<MovieDetail>> =
        object : Config<UIStateResponse<MovieDetail>> {

            lateinit var mutableStateFlow: MutableStateFlow<UIStateResponse<MovieDetail>>

            override val screenState: StateFlow<UIStateResponse<MovieDetail>>
                get() = mutableStateFlow

            override fun init() {
                mutableStateFlow = MutableStateFlow(UIStateResponse(state = UIState.IDLE))
                every { savedStateHandle.get<Int>(MovieDetailEntry.ID) } returns 1
                every { fetchMovieItemUC.invoke(any()) } returns screenState
                viewModel = MovieDetailViewModel(savedStateHandle, fetchMovieItemUC)
                composeTestRule.setContent {
                    MaterialTheme {
                        MovieDetailScreen(viewModel, onBackPressed, onReviewsClicked)
                    }
                }
            }

            override fun setState(state: UIStateResponse<MovieDetail>) {
                if (mutableStateFlow.value != state) {
                    mutableStateFlow.value = state
                }
            }
        }

    @Test
    fun loadingAndErrorState() {
        robot {
            assertLoadingViewIsDisplayed()
            assertErrorViewIsDisplayed()
        }
    }

    @Test
    fun successfulState() {
        robot {
            assertLoadingViewIsDisplayed()
            assertMovieContent()
            assertBackActionIsCalled()
            assertReviewActionIsCalled()
        }
    }
}

internal fun MovieDetailScreenTest.robot(
    func: MovieDetailScreenRobot.() -> Unit
) = MovieDetailScreenRobot(this).apply(func)

internal class MovieDetailScreenRobot constructor(
    private val test: MovieDetailScreenTest
) : BaseRobot(test.composeTestRule) {

    fun assertLoadingViewIsDisplayed() = test.apply {
        config.setState(UIStateResponse(state = UIState.LOADING))
        composeTestRule.onNodeWithTag(LOADER_TAG).assertExists()
    }

    fun assertErrorViewIsDisplayed() = test.apply {
        config.setState(UIStateResponse(state = UIState.ERROR))
        composeTestRule.onNodeWithTag(ERROR_TAG).assertExists()
    }

    fun assertBackActionIsCalled() = test.apply {
        composeTestRule.onNodeWithTag(MOVIE_BACK_BUTTON_TAG).performClick()
        verify(exactly = 1) { onBackPressed.invoke() }
    }

    fun assertReviewActionIsCalled() = test.apply {
        composeTestRule.onNodeWithTag(MOVIE_REVIEW_BUTTON_TAG).performClick()
        verify(exactly = 1) { onReviewsClicked.invoke() }
    }

    fun assertMovieContent() = test.apply {
        config.setState(UIStateResponse(data = MovieObjectMother.movieDetail, state = UIState.CONTENT))
        with(composeTestRule) {
            onNodeWithTag(MOVIE_IMAGE_TAG).assertExists()
            if (MovieObjectMother.movie.overview != null) {
                onNodeWithTag(MOVIE_OVERVIEW_TAG).assert(hasText(MovieObjectMother.movie.overview!!))
            }
            if (MovieObjectMother.movieDetail.recommended.isNotEmpty()) {
                onNodeWithTag(MOVIE_ROW_RECOMMENDATIONS_TAG)
                    .assertExists()
                    .onChildren()
                    .assertCountEquals(MovieObjectMother.movieDetail.recommended.size - 1)
            }
        }
    }
}

object MovieObjectMother {

    val movie = Movie(
        id = 1,
        title = "Summering",
        overview = "Four best friends, on the brink of starting middle school.",
        posterUrl = "/2UH9yHIqW8IQiDTPHEBTo7RknEL.jpg",
        voteCount = 12,
        backdropUrl = "/8xGomirtP23Fh89b46iGBUQxaWh.jpg",
        releaseDate = "2022-08-12",
        voteAverage = 4F,
    )

    val movieDetail = MovieDetail(
        movie = movie,
        recommended = listOf(
            movie.copy(id = 2, title = "Abandoned"),
            movie.copy(id = 3, title = "The Boss Baby"),
            movie.copy(id = 4, title = "Night at the Museum"),
            movie.copy(id = 5, title = "Hex"),
        )
    )
}