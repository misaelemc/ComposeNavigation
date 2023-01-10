package com.rappi.featureC.impl

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.navigation.NavDestinations
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class)
class FeatureCEntryImpl @Inject constructor() : FeatureCEntry() {

    override fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: NavDestinations,
        sheetState: ModalBottomSheetState
    ) {
        navigation(startDestination = FEATURE_1_ROUTE, route = this@FeatureCEntryImpl.getRoute()) {
            composable(
                route = FEATURE_1_ROUTE,
                arguments = arguments,
                deepLinks = deepLinks
            ) {
                FeatureOneScreen(
                    comesFrom = it.arguments?.getString(FROM).orEmpty(),
                    onClickNavigation = {
                        navController.navigate(FEATURE_2_ROUTE)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = FEATURE_2_ROUTE,
                arguments = arguments,
                deepLinks = deepLinks
            ) {
                FeatureTwoScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}