package com.rappi.featureB.impl.di

import com.rappi.common.AppScope
import com.rappi.featureB.api.FeatureBEntry
import com.rappi.featureB.impl.FeatureBEntryImpl
import com.rappi.navigation.FeatureEntry
import com.rappi.navigation.FeatureEntryKey
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
abstract class FeatureBEntryModule {

    @Binds
    @IntoMap
    @Singleton
    @FeatureEntryKey(FeatureBEntry::class)
    abstract fun bindProductViewEntry(impl: FeatureBEntryImpl): FeatureEntry
}