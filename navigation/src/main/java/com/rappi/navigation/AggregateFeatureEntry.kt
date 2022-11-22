package com.rappi.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface AggregateFeatureEntry : FeatureEntry {

    @OptIn(ExperimentalMaterialApi::class)
    fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: NavDestinations,
        sheetState: ModalBottomSheetState
    )
}
