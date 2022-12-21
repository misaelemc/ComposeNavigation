package com.rappi.composenavigation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rappi.common.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import javax.inject.Provider

@ContributesBinding(AppScope::class)
class ViewModelProviderFactory @Inject constructor(
    private val vmProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val modelProvider =
            vmProviders[modelClass]
                ?: throw  IllegalArgumentException("Unknown ViewModel class: $modelClass")
        return modelProvider.get() as T
    }

}