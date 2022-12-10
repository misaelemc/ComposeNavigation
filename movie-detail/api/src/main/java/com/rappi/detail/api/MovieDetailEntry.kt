package com.rappi.detail.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rappi.navigation.ComposableFeatureEntry

abstract class MovieDetailEntry : ComposableFeatureEntry {

    override val featureRoute = "$ROUTE?$ID={$ID}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(ID) {
                type = NavType.IntType
            }
        )

    fun destination(id: Int) = "$ROUTE?$ID=$id"

    companion object {
        const val ID = "id"
        const val DETAIL = "detail"
        const val REVIEW = "review"
        private const val ROUTE = "movie-detail"
    }
}
