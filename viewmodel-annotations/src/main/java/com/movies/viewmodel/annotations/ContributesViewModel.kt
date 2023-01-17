package com.movies.viewmodel.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class ContributesViewModel(
    val scope: KClass<*>
)