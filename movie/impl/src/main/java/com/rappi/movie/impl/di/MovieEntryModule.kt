package com.rappi.movie.impl.di

import com.rappi.common.AppScope
import com.rappi.movie.api.MovieEntry
import com.rappi.movie.impl.MovieEntryImpl
import com.rappi.navigation.FeatureEntry
import com.rappi.navigation.FeatureEntryKey
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
abstract class MovieEntryModule {

    @Binds
    @IntoMap
    @Singleton
    @FeatureEntryKey(MovieEntry::class)
    abstract fun bindProductViewEntry(impl: MovieEntryImpl): FeatureEntry
}