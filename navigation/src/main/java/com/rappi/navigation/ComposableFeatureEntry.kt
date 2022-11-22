package com.rappi.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterialApi::class)
interface ComposableFeatureEntry : FeatureEntry {

    fun NavGraphBuilder.composable(
        navController: NavHostController,
        destinations: NavDestinations,
        sheetState: ModalBottomSheetState
    ) {
        composable(
            route = featureRoute,
            arguments = arguments,
            deepLinks = deepLinks,
            content = { backStackEntry ->
                Composable(navController, destinations, backStackEntry, sheetState)
            }
        )
    }

    @Composable
    fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    )
}
