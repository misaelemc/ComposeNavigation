package com.rappi.common.viewModel.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rappi.common.viewModel.ViewModelFactory

@Composable
fun InjectComposition(
    viewModelFactory: ViewModelFactory,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        content = content
    )
}

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(): VM {
    val factory = getViewModelFactory()
    return viewModel {
        val savedStateHandle = createSavedStateHandle()
        factory.create(VM::class.java, savedStateHandle)
    }
}

@Composable
@PublishedApi
internal fun getViewModelFactory(): ViewModelFactory {
    return checkNotNull(LocalViewModelFactory.current) {
        "No ViewModelFactory was provided via LocalViewModelFactory"
    }
}

object LocalViewModelFactory {
    private val LocalViewModelCompositionFactory = compositionLocalOf<ViewModelFactory?> { null }

    val current: ViewModelFactory?
        @Composable
        get() = LocalViewModelCompositionFactory.current

    infix fun provides(viewModelFactory: ViewModelFactory):
            ProvidedValue<ViewModelFactory?> {
        return LocalViewModelCompositionFactory.provides(viewModelFactory)
    }
}