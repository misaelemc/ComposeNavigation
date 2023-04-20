package com.rappi.movie.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.common.viewModel.ViewModelFactory
import com.rappi.movie.impl.data.datasource.remote.MovieService
import com.rappi.navigation.Destinations
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@SingleIn(FeatureScope::class)
@ContributesSubcomponent(
    scope = FeatureScope::class,
    parentScope = AppScope::class
)
interface MovieComponent {

    val destinations: Destinations
    fun getFactoryViewModelAssistedFactory(): ViewModelFactory

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun factory(): MovieComponent
    }
}

@Module
@ContributesTo(FeatureScope::class)
object FeatureModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}