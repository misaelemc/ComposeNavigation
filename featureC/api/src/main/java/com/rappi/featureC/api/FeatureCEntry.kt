package com.rappi.featureC.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rappi.navigation.AggregateFeatureEntry

abstract class FeatureCEntry : AggregateFeatureEntry {

    override val featureRoute = "$ROUTE?$FROM={$FROM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(FROM) {
                type = NavType.StringType
            }
        )

    fun destination(from: String) = "$ROUTE?$FROM=$from"

    companion object {
        const val FROM = "from"
        private const val ROUTE = "featureC"
        const val FEATURE_1_ROUTE = "feature-1"
        const val FEATURE_2_ROUTE = "feature-2"
    }
}
