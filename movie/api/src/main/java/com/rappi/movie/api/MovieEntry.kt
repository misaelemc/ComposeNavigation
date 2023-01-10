package com.rappi.movie.api

import com.rappi.navigation.ScreenRoutes
import com.rappi.navigation.SingleFeatureEntry

abstract class MovieEntry : SingleFeatureEntry {

    override val featureRoute: String = ScreenRoutes.MOVIES.value
}