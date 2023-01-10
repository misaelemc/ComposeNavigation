package com.rappi.featureC.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rappi.navigation.NavigationFeatureEntry
import com.rappi.navigation.ScreenRoutes

abstract class FeatureCEntry : NavigationFeatureEntry {

    override val featureRoute: String = ScreenRoutes.FEATURE_C.value

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(FROM) {
                type = NavType.StringType
            }
        )

    companion object {
        const val FROM = "from"
        const val FEATURE_1_ROUTE = "feature-1"
        const val FEATURE_2_ROUTE = "feature-2"
    }
}
