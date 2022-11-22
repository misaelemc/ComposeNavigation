package com.rappi.featureA.impl.di

import com.rappi.common.AppScope
import com.rappi.featureA.api.FeatureAEntry
import com.rappi.featureA.impl.FeatureAEntryImpl
import com.rappi.navigation.FeatureEntry
import com.rappi.navigation.FeatureEntryKey
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
abstract class FeatureAEntryModule {

    @Binds
    @IntoMap
    @Singleton
    @FeatureEntryKey(FeatureAEntry::class)
    abstract fun bindProductViewEntry(impl: FeatureAEntryImpl): FeatureEntry
}