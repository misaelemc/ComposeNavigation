package com.rappi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface SingleDestinationEntry : DestinationEntry {

    /**
     * Add the screen composable to the NavGraphBuilder
     * @param navController The controller that navigated
     * @param destinations A map that contains all the destinations available to navigate to
     * @param backStackEntry The representation of an entry in the back stack of a NavController
     */
    @Composable
    fun NavGraphBuilder.ComposableView(
        navController: NavHostController,
        destinations: Destinations,
    )
}