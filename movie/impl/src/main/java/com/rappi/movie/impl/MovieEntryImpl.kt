package com.rappi.movie.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDirections
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.rappi.common.AppScope
import com.rappi.common.ComponentHolder
import com.rappi.common.daggerViewModel
import com.rappi.common.viewModel.compose.InjectComposition
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.movie.api.MovieEntry
import com.rappi.movie.impl.di.MovieComponent
import com.rappi.movie.impl.presentation.screen.MovieScreen
import com.rappi.movie.impl.presentation.screen.destinations.MovieScreenDestination
import com.rappi.navigation.DestinationEntry
import com.rappi.navigation.DestinationEntryKey
import com.rappi.navigation.Destinations
import com.rappi.navigation.entry
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject
import kotlin.reflect.KClass

@DestinationEntryKey(MovieEntry::class)
@ContributesMultibinding(AppScope::class, boundType = DestinationEntry::class)
class MovieEntryImpl @Inject constructor() : MovieEntry() {

    val component = ComponentHolder.component<MovieComponent.ParentComponent>().factory()

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        InjectComposition(component.getFactoryViewModelAssistedFactory()) {
            MovieScreen(
                onMovieItemClick = { id ->
                    navController.navigate(
                        destinations.entry<MovieDetailEntry>().direction(MovieDetailEntry.NavArgs(id))
                            .route
                    )
                }
            )
        }
    }

    override fun direction(navArgsDelegate: Any?): Direction {
        return MovieScreenDestination.invoke()
    }

}
