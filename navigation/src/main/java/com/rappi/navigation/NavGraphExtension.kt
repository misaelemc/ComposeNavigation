package com.rappi.navigation

inline fun <reified T : FeatureEntry> NavDestinations.entry(): T =
    findOrNull() ?: error("Unable to find '${T::class.java} destination.")

inline fun <reified T : FeatureEntry> NavDestinations.findOrNull(): T? = this[T::class.java] as? T
