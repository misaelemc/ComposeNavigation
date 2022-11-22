package com.rappi.composenavigation

import com.rappi.common.ComponentHolder
import com.rappi.composenavigation.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class NavigationApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .apply { inject(this@NavigationApp) }
        ComponentHolder.addOrReplace(component)
        return component
    }
}