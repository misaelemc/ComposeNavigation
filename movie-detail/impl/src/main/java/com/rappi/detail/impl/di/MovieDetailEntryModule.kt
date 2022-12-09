package com.rappi.detail.impl.di

import com.rappi.common.AppScope
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.detail.impl.MovieDetailEntryImpl
import com.rappi.navigation.FeatureEntry
import com.rappi.navigation.FeatureEntryKey
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
abstract class MovieDetailEntryModule {

    @Binds
    @IntoMap
    @Singleton
    @FeatureEntryKey(MovieDetailEntry::class)
    abstract fun bindMovieDetailEntry(impl: MovieDetailEntryImpl): FeatureEntry
}