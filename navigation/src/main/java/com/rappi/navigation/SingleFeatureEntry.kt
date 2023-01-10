package com.rappi.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterialApi::class)
interface SingleFeatureEntry : FeatureEntry {

    /**
     * Add the Composable to the NavGraphBuilder
     * @param navController The controller that navigated
     * @param destinations A map that contains all the destinations available to navigate to
     * @param sheetState State of the ModalBottomSheetLayout composable.
     */
    fun NavGraphBuilder.composable(
        navController: NavHostController,
        destinations: NavDestinations,
        sheetState: ModalBottomSheetState
    ) {
        composable(
            route = this@SingleFeatureEntry.getRoute(),
            arguments = arguments,
            deepLinks = deepLinks,
            content = { backStackEntry ->
                Composable(navController, destinations, backStackEntry, sheetState)
            }
        )
    }

    /**
     * Add the screen composable to the NavGraphBuilder
     * @param navController The controller that navigated
     * @param destinations A map that contains all the destinations available to navigate to
     * @param backStackEntry The representation of an entry in the back stack of a NavController
     * @param sheetState State of the ModalBottomSheetLayout composable.
     */
    @Composable
    fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    )
}
