package com.rappi.featureB.impl

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.rappi.featureB.api.FeatureBEntry
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.navigation.NavDestinations
import com.rappi.navigation.entry
import javax.inject.Inject


@OptIn(ExperimentalMaterialApi::class)
class FeatureBEntryImpl @Inject constructor() : FeatureBEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    ) {
        FeatureBScreen(
            onNavigateClick = {
                navController.navigate(destinations.entry<FeatureCEntry>().destination())
            },
            onBackClick = { navController.popBackStack() }
        )
    }
}