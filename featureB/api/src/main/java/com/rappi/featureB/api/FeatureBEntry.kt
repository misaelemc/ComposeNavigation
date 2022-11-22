package com.rappi.featureB.api

import com.rappi.navigation.ComposableFeatureEntry

abstract class FeatureBEntry : ComposableFeatureEntry {

    override val featureRoute = "feature-b"

    fun destination() = featureRoute
}
