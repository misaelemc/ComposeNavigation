package com.rappi.detail.api

import com.rappi.navigation.ScreenRoutes
import com.rappi.navigation.SingleDestinationEntry

abstract class MovieDetailEntry : SingleDestinationEntry<MovieDetailEntry.NavArgs> {

    override val route: String = ScreenRoutes.MOVIE_DETAIL.value

    data class NavArgs(val id : Int = -1)
}
