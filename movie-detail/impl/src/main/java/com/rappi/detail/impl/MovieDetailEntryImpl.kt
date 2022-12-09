package com.rappi.detail.impl

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.rappi.common.ComponentHolder
import com.rappi.common.daggerViewModel
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.detail.impl.di.MovieDetailComponent
import com.rappi.detail.impl.presentation.screen.MovieDetailScreen
import com.rappi.navigation.NavDestinations
import javax.inject.Inject


@OptIn(ExperimentalMaterialApi::class)
class MovieDetailEntryImpl @Inject constructor() : MovieDetailEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    ) {
        val component = ComponentHolder.component<MovieDetailComponent.ParentComponent>().build()
        MovieDetailScreen(
            movieId = backStackEntry.arguments?.getInt(ID, -1) ?: -1,
            viewModel = daggerViewModel { component.movieDetailViewModel },
            onBackPressed = { navController.popBackStack() }
        )
    }
}