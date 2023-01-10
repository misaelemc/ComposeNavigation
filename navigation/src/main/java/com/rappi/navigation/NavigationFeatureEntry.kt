package com.rappi.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationFeatureEntry : FeatureEntry {

    /**
     * Add the nested NavGraph to the NavGraphBuilder
     * @param navController The controller that navigated
     * @param destinations A map that contains all the destinations available to navigate to
     * @param sheetState State of the ModalBottomSheetLayout composable.
     */
    @OptIn(ExperimentalMaterialApi::class)
    fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: NavDestinations,
        sheetState: ModalBottomSheetState
    )
}
