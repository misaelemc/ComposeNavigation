package com.rappi.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

typealias NavDestinations = Map<Class<out FeatureEntry>, @JvmSuppressWildcards FeatureEntry>

interface FeatureEntry {

    val featureRoute: String

    val deepLinks: List<NavDeepLink>
        get() = emptyList()

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    fun getRoute(): String {
        var finalRoute = "$featureRoute?"
        arguments.forEach { arg ->
            finalRoute += "${arg.name}={${arg.name}}"
        }
        return finalRoute
    }

    fun destination(vararg params: NavArgument): String {
        var route = "$featureRoute?"
        arguments.forEach { arg ->
            params.forEach { param ->
                if (arg.name == param.name) {
                    route += "${arg.name}=${param.value}"
                }
            }
        }
        return route
    }
}

data class NavArgument(val name: String, val value: Any)

enum class ScreenRoutes constructor(val value: String) {
    MOVIES("movies"),
    MOVIE_DETAIL("movie-detail"),
    FEATURE_C("feature-c"),
    SERIES("series"),
}