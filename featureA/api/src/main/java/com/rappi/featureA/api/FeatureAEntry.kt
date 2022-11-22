package com.rappi.featureA.api

import com.rappi.navigation.ComposableFeatureEntry

abstract class FeatureAEntry : ComposableFeatureEntry {

    override val featureRoute = "feature-a"

    fun destination() = featureRoute
}
