package com.rappi.detail.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.spec.Direction
import com.rappi.common.AppScope
import com.rappi.common.ComponentHolder
import com.rappi.common.viewModel.compose.InjectComposition
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.detail.impl.di.MovieDetailComponent
import com.rappi.detail.impl.presentation.screen.MovieDetailScreen
import com.rappi.detail.impl.presentation.screen.destinations.MovieDetailScreenDestination
import com.rappi.navigation.DestinationEntry
import com.rappi.navigation.DestinationEntryKey
import com.rappi.navigation.Destinations
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@DestinationEntryKey(MovieDetailEntry::class)
@ContributesMultibinding(AppScope::class, boundType = DestinationEntry::class)
class MovieDetailEntryImpl @Inject constructor() : MovieDetailEntry() {

    private val component = ComponentHolder.component<MovieDetailComponent.ParentComponent>()
        .create()

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        InjectComposition(component.getFactoryViewModelAssistedFactory()) {
            MovieDetailScreen(
                onBackPressed = { it?.popBackStack() },
                onReviewsClicked = {
//                    navController.navigate(
//                        destinations.entry<FeatureCEntry>()
//                            .destination(NavArgument(FROM, "Movies Detail"))
//                    )
                }
            )
        }
    }

    override fun direction(navArgsDelegate: NavArgs?): Direction {
        return MovieDetailScreenDestination.invoke(navArgsDelegate)
    }
}