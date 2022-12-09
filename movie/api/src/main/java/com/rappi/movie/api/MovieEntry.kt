package com.rappi.movie.api

import com.rappi.navigation.ComposableFeatureEntry

abstract class MovieEntry : ComposableFeatureEntry {

    override val featureRoute = "feature-a"

    fun destination() = featureRoute
}
