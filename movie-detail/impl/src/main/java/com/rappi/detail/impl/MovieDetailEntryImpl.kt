package com.rappi.detail.impl

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.rappi.common.ComponentHolder
import com.rappi.common.daggerViewModel
import com.rappi.common.viewModel.compose.InjectComposition
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.detail.impl.di.MovieDetailComponent
import com.rappi.detail.impl.presentation.screen.MovieDetailScreen
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.featureC.api.FeatureCEntry.Companion.FROM
import com.rappi.navigation.NavArgument
import com.rappi.navigation.NavDestinations
import com.rappi.navigation.entry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalMaterialApi::class)
class MovieDetailEntryImpl @Inject constructor() : MovieDetailEntry() {

    private val component = ComponentHolder.component<MovieDetailComponent.ParentComponent>()
        .create()

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    ) {
        InjectComposition(component.getFactoryViewModelAssistedFactory()) {
            MovieDetailScreen(
                onBackPressed = { navController.popBackStack() },
                onReviewsClicked = {
                    navController.navigate(
                        destinations.entry<FeatureCEntry>()
                            .destination(NavArgument(FROM, "Movies Detail"))
                    )
                }
            )
        }
    }
}