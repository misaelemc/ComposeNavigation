package com.rappi.composenavigation.di

import com.rappi.common.AppScope
import com.rappi.composenavigation.NavigationApp
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@MergeComponent(
    AppScope::class,
    modules = [AndroidSupportInjectionModule::class]
)
interface AppComponent: AndroidInjector<NavigationApp> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: NavigationApp): Builder
    }
}