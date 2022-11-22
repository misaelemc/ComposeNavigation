package com.rappi.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel


/**
 * Using this extension, you have to create a Dagger Component
 * (if needed, according to the ViewModel owner) and provide a ViewModel instance.
 *
 * @param viewModelInstanceCreator the viewModel instance
 */
@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    crossinline viewModelInstanceCreator: () -> T
): T = viewModel(
    modelClass = T::class.java,
    factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelInstanceCreator() as T
        }
    }
)