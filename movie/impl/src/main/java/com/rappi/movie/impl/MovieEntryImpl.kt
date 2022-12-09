package com.rappi.movie.impl

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.rappi.common.ComponentHolder
import com.rappi.common.daggerViewModel
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.movie.api.MovieEntry
import com.rappi.movie.impl.di.MovieComponent
import com.rappi.movie.impl.presentation.screen.MovieScreen
import com.rappi.navigation.NavDestinations
import com.rappi.navigation.entry
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class)
class MovieEntryImpl @Inject constructor() : MovieEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    ) {
        val component = ComponentHolder.component<MovieComponent.ParentComponent>().factory()
        MovieScreen(
            viewModel = daggerViewModel { component.viewModel },
            onMovieItemClick = {
                navController.navigate(
                    destinations.entry<MovieDetailEntry>().destination(it)
                )
            }
        )
    }
}
