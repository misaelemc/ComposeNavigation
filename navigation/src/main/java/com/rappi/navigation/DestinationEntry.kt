package com.rappi.navigation

import com.ramcosta.composedestinations.spec.Direction
import dagger.MapKey
import kotlin.reflect.KClass

typealias Destinations = Map<Class<out DestinationEntry<*>>, @JvmSuppressWildcards DestinationEntry<*>>

interface DestinationEntry<T> {

    val route: String

    fun direction(navArgsDelegate: T? = null): Direction
}

@MapKey
annotation class DestinationEntryKey(val value: KClass<out DestinationEntry<*>>)

inline fun <reified T : DestinationEntry<*>> Destinations.entry(): T =
    findOrNull() ?: error("Unable to find '${T::class.java} destination.")

inline fun <reified T : DestinationEntry<*>> Destinations.findOrNull(): T? = this[T::class.java] as? T