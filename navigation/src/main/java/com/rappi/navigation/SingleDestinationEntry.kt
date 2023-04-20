package com.rappi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlin.reflect.KClass

interface SingleDestinationEntry<T> : DestinationEntry<T> {

    fun NavGraphBuilder.composable(
        navController: NavHostController,
        destinations: Destinations,
    ) {
        composable(
            route = this@SingleDestinationEntry.route,
            content = { backStackEntry ->
                Composable(navController, destinations, backStackEntry)
            }
        )
    }

    /**
     * Add the screen composable to the NavGraphBuilder
     * @param navController The controller that navigated
     * @param destinations A map that contains all the destinations available to navigate to
     * @param backStackEntry The representation of an entry in the back stack of a NavController
     */
    @Composable
    fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    )
}