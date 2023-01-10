package com.rappi.detail.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rappi.navigation.ScreenRoutes
import com.rappi.navigation.SingleFeatureEntry

abstract class MovieDetailEntry : SingleFeatureEntry {

    override val featureRoute: String = ScreenRoutes.MOVIE_DETAIL.value

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ID) {
                type = NavType.IntType
            }
        )

    companion object {
        const val ID = "id"
    }
}
