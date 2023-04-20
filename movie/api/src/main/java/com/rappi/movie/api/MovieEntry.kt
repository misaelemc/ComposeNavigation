package com.rappi.movie.api

import com.rappi.navigation.ScreenRoutes
import com.rappi.navigation.SingleDestinationEntry
import com.rappi.navigation.SingleFeatureEntry

abstract class MovieEntry : SingleDestinationEntry<Any> {

    override val route: String = ScreenRoutes.MOVIES.value
}