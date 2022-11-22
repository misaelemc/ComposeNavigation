package com.rappi.common

/**
 * Utility class that holds the instance of application wide components
 * so it can be easily accessible a cross the different features.
 *
 * ### Why don't we use a Set for the components?
 *
 * We moved away from Set to List for the components for the following reason:
 *
 * - The old implementation had a **`Set<Any>`**. Since a [Set] relies on the
 * methods **equals(Object)** and **hashCode()** being overridden and the elements of the Set were
 * interfaces and did not override **equals(Object)** and **hashCode()**, the Set would
 * allow duplicates and cause an exception when **`component<T>()`** was called.
 */
object ComponentHolder {

    /**
     * List that holds the components that want be shareable a cross the application.
     * The main case is the ApplicationComponent.
     */
    @PublishedApi
    internal var components = mutableListOf<Any>()

    /**
     * Adds a [component] if it does not exist in the list.
     * If the [component] does exist, it replaces the existing component.
     */
    inline fun <reified T : Any> addOrReplace(component: T) {
        val indexOfExistingComponent = components.indexOfFirst { it is T }
        if (indexOfExistingComponent != -1) {
            components[indexOfExistingComponent] = component
        } else {
            components.add(component)
        }
    }

    /**
     * Indicates if a given component with type [T] exists in the list.
     */
    inline fun <reified T : Any> exists(): Boolean {
        return components.find { it is T } != null
    }

    /**
     * Removes all components from the list that conform to the type [T].
     */
    inline fun <reified T : Any> remove() {
        components.removeAll { it is T }
    }

    /**
     * Clears the list of components and assigns a new and clean instance to it.
     */
    fun clear() {
        components.clear()
        components = mutableListOf()
    }

    /**
     * Provides the instance of a component filtering by the requested type [T].
     * It should be noted that the application component must implement the interface
     * for the requested component in order to work.
     */
    inline fun <reified T : Any> component(): T = components
        .filterIsInstance<T>()
        .single()
}