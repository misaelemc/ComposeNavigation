package com.rappi.detail.impl.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.viewmodel.annotations.ContributesViewModel
import com.rappi.common.FeatureScope
import com.rappi.common.domain.model.UIState
import com.rappi.common.domain.model.UIStateResponse
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@ContributesViewModel(FeatureScope::class)
class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    fetchMovieItemUC: FetchMovieItemUC,
) : ViewModel() {

    val state = fetchMovieItemUC.invoke(handle.get<Int>(MovieDetailEntry.ID) ?: -1)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            UIStateResponse(state = UIState.IDLE)
        )
}