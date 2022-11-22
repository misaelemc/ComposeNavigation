package com.rappi.featureC.api

import com.rappi.navigation.AggregateFeatureEntry

abstract class FeatureCEntry : AggregateFeatureEntry {

    override val featureRoute = "feature-c"

    fun destination() = featureRoute

    companion object {
        const val FEATURE_1_ROUTE = "feature-1"
        const val FEATURE_2_ROUTE = "feature-2"
    }
}
