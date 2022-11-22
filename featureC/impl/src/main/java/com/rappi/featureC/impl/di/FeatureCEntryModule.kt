package com.rappi.featureC.impl.di

import com.rappi.common.AppScope
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.featureC.impl.FeatureCEntryImpl
import com.rappi.navigation.FeatureEntry
import com.rappi.navigation.FeatureEntryKey
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
abstract class FeatureCEntryModule {

    @Binds
    @IntoMap
    @Singleton
    @FeatureEntryKey(FeatureCEntry::class)
    abstract fun bindProductViewEntry(impl: FeatureCEntryImpl): FeatureEntry
}