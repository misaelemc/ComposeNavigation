package com.rappi.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

typealias NavDestinations = Map<Class<out FeatureEntry>, @JvmSuppressWildcards FeatureEntry>

private const val DEEPLINK_URL = "https://rappi.com"
private const val DEEPLINK_URI = "app://rappi.com"

interface FeatureEntry {

    val featureRoute: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val deepLinks: List<NavDeepLink>
        get() = createDeepLinksPath(featureRoute)

    private fun createDeepLinksPath(vararg path: String): List<NavDeepLink> {
        val navDeepLinks = arrayListOf<NavDeepLink>()
        path.forEach {
            navDeepLinks.add(navDeepLink {
                uriPattern = "$DEEPLINK_URL/$it"
            })
            navDeepLinks.add(navDeepLink {
                uriPattern = "$DEEPLINK_URI/$it"
            })
        }
        return navDeepLinks
    }
}
