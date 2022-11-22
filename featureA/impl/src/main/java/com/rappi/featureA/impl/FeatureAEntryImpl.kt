package com.rappi.featureA.impl

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.rappi.common.ComponentHolder
import com.rappi.common.daggerViewModel
import com.rappi.featureA.api.FeatureAEntry
import com.rappi.featureA.impl.di.FeatureAComponent
import com.rappi.featureB.api.FeatureBEntry
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.navigation.NavDestinations
import com.rappi.navigation.entry
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class)
class FeatureAEntryImpl @Inject constructor() : FeatureAEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    ) {
        val component = ComponentHolder.component<FeatureAComponent.ParentComponent>().factory()
        FeatureAScreen(
            viewModel = daggerViewModel { component.viewModel },
            featureCOnClick = {
                navController.navigate(destinations.entry<FeatureCEntry>().destination())
            },
            featureBOnClick = {
                navController.navigate(destinations.entry<FeatureBEntry>().destination())
            },
        )
    }
}
