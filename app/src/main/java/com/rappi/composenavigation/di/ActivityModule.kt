package com.rappi.composenavigation.di

import com.rappi.common.AppScope
import com.rappi.composenavigation.MainActivity
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@ContributesTo(AppScope::class)
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}